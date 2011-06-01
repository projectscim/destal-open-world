package general.net.client;

import general.Client;
import general.net.MSGType;
import general.net.Packet;
import general.world.Chunk;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class NetworkClient implements Runnable
{
	private Socket _socket;
	private ObjectInputStream _input;
	private ObjectOutputStream _output;
	
	private Client _client;
	State _state;
	
	private enum State { NONE, CONNECTING, CHECKED, READY };
	
	public NetworkClient(Client client)
	{
		_client = client;
		_state = State.NONE;
	}

	@Override
	public void run()
	{
		System.out.println("netclient started... listening...");
		try
		{
			boolean error = false;
			while(!error)
			{
				Packet r = recv();
				byte type = r.getType();
				System.out.println("received packet (type: " + type + ")");
				
				if(type == MSGType.MSG_SV_INIT && _state == State.CONNECTING)
				{
					if(!(Boolean)r.get())
					{
						System.out.println("wrong version");
						error = true;
					}
					else
					{
						System.out.println("[server] MOTD: " + (String)r.get());
						send(new Packet(MSGType.MSG_CL_REQUEST_ENTER));
						_state = State.CHECKED;
					}
				}
				if (type == MSGType.MSG_SV_RESPONSE_ENTER && _state == State.CHECKED)
				{
					_client.getLocalCharacter().setLocation((Double)r.get(), (Double)r.get());
					Chunk[] buffer = (Chunk[])r.get();
					for (Chunk c : buffer)
					{
						c.initImages();
					}
					_client.setChunkBuffer(buffer);
					_state = State.READY;
					_client.connected();
					System.out.println("received chunk buffer from server");
				}
				if(_state == State.READY)
				{
					if (type == MSGType.MSG_SV_RESPONSE_CHUNK)
					{
						Chunk c = (Chunk)r.get();
						c.initImages();
						for (int i = 0; i < _client.getChunkBuffer().length; i++)
						{
							if(_client.getChunkBuffer()[i] == null)
							{
								_client.getChunkBuffer()[i] = c;
								break;
							}
						}
						System.out.println("received chunk from server");
					}
				}
			}
			_socket.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public void connect(String address, String username)
	{
		System.out.println("connecting to '" + address + "'");
		try
		{
			_state = State.CONNECTING;
			_socket = new Socket(address, 4185);
			_output = new ObjectOutputStream(_socket.getOutputStream());
			_input = new ObjectInputStream(_socket.getInputStream());
			System.out.println("connected to server");
			
			Packet p = new Packet(MSGType.MSG_CL_INIT);
			p.set(MSGType.PROTOCOL_VERSION);
			p.set(username);
			send(p);
			
			(new Thread(this)).start();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public Packet recv() throws Exception
	{
		return (Packet)_input.readObject();
	}
	
	public void send(Packet data)
	{
		try
        {
			_output.writeObject(data);
			_output.flush();
		}
		catch(IOException e)
		{
			System.out.println("exception occured: couldn't send the packet");
		}
	}
}

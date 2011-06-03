package destal.general.net.server;


import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import destal.general.net.MSGType;
import destal.general.net.Packet;

public class ClientConnection implements Runnable
{
	private Socket _socket;
	private ObjectInputStream _input;
	private ObjectOutputStream _output;
	private NetworkServer _networkManager;
	
	private String _clientName;
	
	public ClientConnection(Socket s, NetworkServer network) throws Exception
	{
		_socket = s;
		_input = new ObjectInputStream(_socket.getInputStream());
		_output = new ObjectOutputStream(_socket.getOutputStream());
		_networkManager = network;
		_clientName = "< connecting >";
	}
	
	@Override
	public void run()
	{
		System.out.println("thread started");
		try
        {
			boolean error = false;
			while(!error)
			{
				Packet p = recv();
				byte type = p.getType();
				System.out.println("received packet from client: '" + this + "' (type: " + type + ")");
				
				if(type == MSGType.MSG_CL_INIT)
				{
					if((Integer)p.get() != MSGType.PROTOCOL_VERSION)
					{
						System.out.println("error: wrong version... dropping client");
						Packet r = new Packet(MSGType.MSG_SV_INIT);
						r.set(false);
						send(r);
						error = true;
					}
					else
					{
						_clientName = (String)p.get();
						_networkManager.clientConnected(this);
					}
				}
				if(type == MSGType.MSG_CL_REQUEST_ENTER)
				{
					_networkManager.clientRequestEnter(this);
				}
				if(type == MSGType.MSG_CL_REQUEST_CHUNK)
				{
					int x = (Integer)p.get();
					int y = (Integer)p.get();
					_networkManager.clientRequestChunk(this, x, y);
				}
				if (type == MSGType.MSG_CL_PLAYER_POSITION)
				{
					double x = (Double)p.get();
					double y = (Double)p.get();
					_networkManager.clientPlayerPosition(this, x, y);
				}
			}
			_socket.close();
        }
		catch(Exception e)
		{
			System.out.println("exception occured");
			//e.printStackTrace();
		}
		_networkManager.clientDisconnected(this);
	}
	
	public void drop()
	{
		System.out.println("dropping client: '" + this + "'");
		try
        {
			_socket.close();
        }
		catch(Exception e)
		{
			System.out.println("error: cant't drop the client: '" + this + "'");
		}
	}
	
	public String getName()
	{
		return _clientName;
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
	
	public String toString()
	{
		return getName();
	}
}

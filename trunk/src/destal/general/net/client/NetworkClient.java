package destal.general.net.client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

import destal.general.event.events.PacketReceivedClientEvent;
import destal.general.event.listener.PacketRecievedClientListener;
import destal.general.net.MSGType;
import destal.general.net.Packet;
import destal.general.world.Chunk;

public class NetworkClient implements Runnable
{
	private Socket _socket;
	private ObjectInputStream _input;
	private ObjectOutputStream _output;
	private ArrayList<PacketRecievedClientListener> _packetReceivedClientListener;
	
	private State _state;
	
	private enum State { NONE, CONNECTING, CHECKED, READY };
	
	public NetworkClient()
	{
		_packetReceivedClientListener = new ArrayList<PacketRecievedClientListener>();
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
				//System.out.println("received packet (type: " + type + ")");
				
				if(type == MSGType.MSG_SV_INIT && _state == State.CONNECTING)
				{
					if(!(Boolean)r.get())
					{
						System.out.println("wrong version");
						error = true;
					}
					else
					{
						send(new Packet(MSGType.MSG_CL_REQUEST_ENTER));
						_state = State.CHECKED;
						
						PacketReceivedClientEvent e = new PacketReceivedClientEvent(this);
						e.setMOTD((String)r.get());
						e.setClientID((Integer)r.get());
						System.out.println("Received client id: " + e.getClientID());
						for (PacketRecievedClientListener l : _packetReceivedClientListener)
						{
							l.serverConnected(e);
						}
					}
				}
				if (type == MSGType.MSG_SV_RESPONSE_ENTER && _state == State.CHECKED)
				{
					_state = State.READY;
					
					PacketReceivedClientEvent e = new PacketReceivedClientEvent(this);
					e.setPoint((Double)r.get(), (Double)r.get());
					e.setChunkBuffer((Chunk[])r.get());
					for (PacketRecievedClientListener l : _packetReceivedClientListener)
					{
						l.serverResponseEnter(e);
					}
				}
				if(_state == State.READY)
				{
					if (type == MSGType.MSG_SV_RESPONSE_CHUNK)
					{
						PacketReceivedClientEvent e = new PacketReceivedClientEvent(this);
						e.setChunk((Chunk)r.get());
						for (PacketRecievedClientListener l : _packetReceivedClientListener)
						{
							l.serverResponseChunk(e);
						}
					}
					else if (type == MSGType.MSG_SV_RESPONSE_PLAYER_POSITIONS)
					{
						PacketReceivedClientEvent e = new PacketReceivedClientEvent(this);
						int id = (Integer)r.get();
						double x = (Double)r.get();
						double y = (Double)r.get();
						e.setClientID(id);
						e.setPoint(x, y);
						for (PacketRecievedClientListener l : _packetReceivedClientListener)
						{
							l.serverResponsePlayerPositions(e);
						}
					}
					else if (type == MSGType.MSG_SV_NEW_CLIENT_CONNECTED)
					{
						PacketReceivedClientEvent e = new PacketReceivedClientEvent(this);
						int id = (Integer)r.get();
						double x = (Double)r.get();
						double y = (Double)r.get();
						e.setClientID(id);
						e.setPoint(x, y);
						for (PacketRecievedClientListener l : _packetReceivedClientListener)
						{
							l.serverNewClientConnected(e);
						}
					}
				}
			}
			_socket.close();
		}
		catch(Exception e)
		{
			System.out.println("lost connection");
			//e.printStackTrace();
		}
		for (PacketRecievedClientListener l : _packetReceivedClientListener)
		{
			l.serverDisconnected(new PacketReceivedClientEvent(this));
		}
	}
	
	public void disconnect()
	{
		System.out.println("disconnecting from server");
		try
		{
			_socket.close();
		}
		catch (IOException e)
		{
			System.out.println("exception occured (disconnect)");
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
			System.out.println("can't connect to server");
			//e.printStackTrace();
		}
	}
	
	private Packet recv() throws Exception
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
	
	public void addPacketReceivedClientListener(PacketRecievedClientListener listener)
	{
		_packetReceivedClientListener.add(listener);
	}
}

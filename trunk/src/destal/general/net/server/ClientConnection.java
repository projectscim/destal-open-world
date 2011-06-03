package destal.general.net.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

import destal.general.event.events.PacketReceivedServerEvent;
import destal.general.event.listener.PacketRecievedServerListener;
import destal.general.event.listener.PlayerMovementListener;
import destal.general.net.MSGType;
import destal.general.net.Packet;

public class ClientConnection implements Runnable
{
	private Socket _socket;
	private ObjectInputStream _input;
	private ObjectOutputStream _output;
	private ArrayList<PacketRecievedServerListener> _packetReceivedServerListener;
	
	private String _clientName;
	
	public ClientConnection(Socket s) throws Exception
	{
		_socket = s;
		_input = new ObjectInputStream(_socket.getInputStream());
		_output = new ObjectOutputStream(_socket.getOutputStream());
		_packetReceivedServerListener = new ArrayList<PacketRecievedServerListener>();
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
						error = true;
						System.out.println("error: wrong version... dropping client");
						Packet r = new Packet(MSGType.MSG_SV_INIT);
						r.set(false);
						send(r);
					}
					else
					{
						_clientName = (String)p.get();
						for (PacketRecievedServerListener l : _packetReceivedServerListener)
						{
							l.clientConnected(new PacketReceivedServerEvent(this));
						}
					}
				}
				if(type == MSGType.MSG_CL_REQUEST_ENTER)
				{
					for (PacketRecievedServerListener l : _packetReceivedServerListener)
					{
						l.clientRequestEnter(new PacketReceivedServerEvent(this));
					}
				}
				if(type == MSGType.MSG_CL_REQUEST_CHUNK)
				{
					PacketReceivedServerEvent e = new PacketReceivedServerEvent(this);
					e.setPoint((Integer)p.get(),(Integer)p.get());
					for (PacketRecievedServerListener l : _packetReceivedServerListener)
					{
						l.clientRequestChunk(e);
					}
				}
				if (type == MSGType.MSG_CL_PLAYER_POSITION)
				{
					PacketReceivedServerEvent e = new PacketReceivedServerEvent(this);
					e.setPoint((Double)p.get(),(Double)p.get());
					for (PacketRecievedServerListener l : _packetReceivedServerListener)
					{
						l.clientPlayerPosition(e);
					}
				}
			}
			_socket.close();
        }
		catch(Exception e)
		{
			System.out.println("exception occured");
			//e.printStackTrace();
		}
		for (PacketRecievedServerListener l : _packetReceivedServerListener)
		{
			l.clientDisconnected(new PacketReceivedServerEvent(this));
		}
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
	
	public String getName()
	{
		return _clientName;
	}
	
	public String toString()
	{
		return getName();
	}
	
	public void addPacketReceivedServerListener(PacketRecievedServerListener listener)
	{
		_packetReceivedServerListener.add(listener);
	}
}

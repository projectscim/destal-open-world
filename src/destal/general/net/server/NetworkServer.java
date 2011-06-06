package destal.general.net.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Vector;

import destal.general.Server;
import destal.general.event.events.PacketReceivedServerEvent;
import destal.general.event.listener.PacketRecievedServerListener;
import destal.general.net.MSGType;
import destal.general.net.Packet;
import destal.general.world.WorldPoint;

public class NetworkServer implements Runnable, PacketRecievedServerListener
{
	private Server _server;
	private ServerSocket _serverSocket;
	private Vector<ClientConnection> _clientConnections;
	
	public NetworkServer(Server server)
	{
		_server = server;
		_clientConnections = new Vector<ClientConnection>();
	}
	
	@Override
	public void run()
	{
		try 
		{
			_serverSocket = new ServerSocket(4185);
		}
		catch (IOException e)
		{
			//e.printStackTrace();
			System.out.println("couldn't create socket");
			return;
		}
		while(true)
        {
			try
			{
				Socket s = _serverSocket.accept();
				ClientConnection clCon = new ClientConnection(s, getFreeId());
				clCon.addPacketReceivedServerListener(this);
				clCon.addPacketReceivedServerListener(_server.getController());
				_clientConnections.add(clCon);
	            new Thread(clCon).start();
	            System.out.println("new client: " + (_clientConnections.size()-1));
			}
			catch (Exception e)
			{
				System.out.println("exception occured (client listener)");
				//e.printStackTrace();
				break;
			}
        }
	}
	
	private int getFreeId()
	{
		int id = 0;
		int prevId;
		do
		{
			prevId = id;
			for(ClientConnection clCon : _clientConnections)
			{
				if(clCon.getID() == id)
				{
					id++;
					break;
				}
			}
		} while(prevId != id);
		return id;
	}
	
	public void send(int ID, Packet data)
	{
		// TODO: use real ID or something else to identify the client
		if(ID >= 0 && ID < _clientConnections.size())
		{
			_clientConnections.get(ID).send(data);
		}
		else if(ID == -1) // broadcast
		{
			for(ClientConnection clientCon : _clientConnections)
				clientCon.send(data);
		}
	}

	@Override
	public void clientConnected(PacketReceivedServerEvent e)
	{
		System.out.println("client connected: '" + e.getClient() + "'");
		Packet p = new Packet(MSGType.MSG_SV_INIT);
		p.set(true);
		p.set("Welcome :)");
		p.set(e.getClient().getID());
		// Pack other clients ids in array
		
		int[] a = new int[_clientConnections.size()];
		int i = 0;
		for (ClientConnection c : _clientConnections)
		{
			a[i++] = c.getID();
		}
		p.set(a);
		e.getClient().send(p);
		
		if(_server.getServerGUI() != null)
		{
			_server.getServerGUI().setClientList(_clientConnections);
		}
	}

	@Override
	public void clientDisconnected(PacketReceivedServerEvent e)
	{
		System.out.println("client left: '" + e.getClient() + "'");
		_clientConnections.remove(e.getClient());
		
		if(_server.getServerGUI() != null)
		{
			_server.getServerGUI().setClientList(_clientConnections);
		}
	}
	
	@Override
	public void clientPlayerInput(PacketReceivedServerEvent e)
	{
		Packet p = new Packet(MSGType.MSG_SV_RESPONSE_PLAYER_POSITIONS);
		p.set(e.getClient().getID());
		p.set(e.getPoint().getX());
		p.set(e.getPoint().getY());
		send(-1, p);
		//System.out.println("Player " + e.getClient() + " changed location to: " + e.getPoint());
	}

	@Override
	public void clientRequestEnter(PacketReceivedServerEvent e)
	{
		// TODO: get position somewhere else
		WorldPoint pos = new WorldPoint(40, 40);
		
		// Send packet to all other clients
		Packet p = new Packet(MSGType.MSG_SV_NEW_CLIENT_CONNECTED);
		p.set(e.getClient().getID());
		p.set(pos.getX());
		p.set(pos.getY());
		send(-1, p);
	}
	
	@Override
	public void clientRequestChunk(PacketReceivedServerEvent e) { }
}

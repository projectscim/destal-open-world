package destal.general.net.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Vector;

import destal.general.Server;
import destal.general.event.events.ClientConnectedEvent;
import destal.general.event.events.PacketReceivedServerEvent;
import destal.general.event.events.PlayerMovementEvent;
import destal.general.event.listener.ClientConnectedListener;
import destal.general.event.listener.PacketRecievedServerListener;
import destal.general.event.listener.PlayerMovementListener;
import destal.general.net.MSGType;
import destal.general.net.Packet;

public class NetworkServer implements Runnable, PacketRecievedServerListener, PlayerMovementListener
{
	private Server _server;
	private ServerSocket _serverSocket;
	private Vector<ClientConnection> _clientConnections;
	private ArrayList<ClientConnectedListener> _clientConnectedListener;
	private int _currentID = 0;
	
	public NetworkServer(Server server)
	{
		_server = server;
		_clientConnections = new Vector<ClientConnection>();
		_clientConnectedListener = new ArrayList<ClientConnectedListener>();
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
				ClientConnection clCon = new ClientConnection(s, _currentID++);
				clCon.addPacketReceivedServerListener(this);
				clCon.addPacketReceivedServerListener(_server.getController());
				_clientConnections.add(clCon);
	            new Thread(clCon).start();
	            System.out.println("new client: " + (_clientConnections.size()-1));
	            this.invokeClientConnected(new ClientConnectedEvent(this));
			}
			catch (Exception e)
			{
				System.out.println("exception occured (client listener)");
				//e.printStackTrace();
				break;
			}
        }
	}
	
	public Vector<ClientConnection> getClientList()
	{
		return _clientConnections;
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
		p.set(((ClientConnection)e.getSource()).getID());
		
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
	
	public void addClientConnectedListener(ClientConnectedListener l)
	{
		_clientConnectedListener.add(l);
	}
	
	public void invokeClientConnected(ClientConnectedEvent e)
	{
		for (ClientConnectedListener l : _clientConnectedListener)
		{
			l.clientConnected(e);
		}
	}

	@Override
	public void clientRequestEnter(PacketReceivedServerEvent e) { }
	@Override
	public void clientRequestChunk(PacketReceivedServerEvent e) { }
	@Override
	public void clientPlayerPosition(PacketReceivedServerEvent e)
	{
		for (ClientConnection c : _clientConnections)
		{
			Packet p = new Packet(MSGType.MSG_SV_PLAYER_POSITIONS);
			p.set(e.getClientID());
			p.set(e.getPoint().getX());
			p.set(e.getPoint().getY());
			c.send(p);
			System.out.println("Player " + e.getClientID() + " changed location to: " + e.getPoint().toString());
		}
	}

	@Override
	public void playerMoved(PlayerMovementEvent e)
	{/*
		for (ClientConnection c : _clientConnections)
		{
			Packet p = new Packet(MSGType.MSG_SV_PLAYER_POSITIONS);
			p.set(e.getLocation().getX());
			p.set(e.getLocation().getY());
			p.set(e.getSource());
			c.send(p);
		}*/
	}
}

package general.net.server;

import general.Controller;
import general.Server;
import general.net.MSGType;
import general.net.Packet;
import general.world.Chunk;
import general.world.WorldPoint;

import java.awt.Point;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class NetworkServer implements Runnable
{
	private Server _server;
	private Controller _controller;
	private ServerSocket _serverSocket;
	private ArrayList<ClientConnection> _clientConnections;
	
	public NetworkServer(Server server, Controller controller)
	{
		_server = server;
		_controller = controller;
		_clientConnections = new ArrayList<ClientConnection>();
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		while(true)
        {
			try
			{
				Socket s = _serverSocket.accept();
				_clientConnections.add(new ClientConnection(s, this));
				System.out.println("new client: " + (_clientConnections.size()-1));
	            new Thread(_clientConnections.get(_clientConnections.size()-1)).start();
			}
			catch (Exception e)
			{
				e.printStackTrace();
				break;
			}
        }
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
	
	public void clientDisconnected(ClientConnection c)
	{
		System.out.println("client left: '" + c.getName() + "'");
		_clientConnections.remove(c);
	}
	
	public void clientConnected(ClientConnection c)
	{
		System.out.println("client connected: '" + c.getName() + "'");
		Packet p = new Packet(MSGType.MSG_SV_INIT);
		p.set(true);
		p.set("Welcome :)");
		c.send(p);
	}
	
	public void clientRequestEnter(ClientConnection c)
	{
		System.out.println("sending chunk buffer to client: '" + c.getName() + "'");
		// TODO: change default position
		WorldPoint pos = new WorldPoint(40, 40);
		Point chunkPos = pos.getChunkLocation();
		
		Chunk[] buffer = new Chunk[9];
		int i = 0;
		for (int x = chunkPos.x-1; x <= chunkPos.x+1; x++)
		{
			for (int y = chunkPos.y-1; y <= chunkPos.y+1; y++)
			{
				buffer[i++] = _controller.world().getLevels()[0].getChunk(x, y);
			}
		}
		
		Packet p = new Packet(MSGType.MSG_SV_RESPONSE_ENTER);
		p.set(pos.getX());
		p.set(pos.getY());
		p.set(buffer);
		c.send(p);
	}
	
	public void clientRequestChunk(ClientConnection c, int x, int y)
	{
		System.out.println("sending chunk to client: '" + c.getName() + "'");
		
		Packet p = new Packet(MSGType.MSG_SV_RESPONSE_CHUNK);
		p.set(_controller.world().getLevels()[0].getChunk(x, y));
		c.send(p);
	}
	
	public void clientPlayerPosition(ClientConnection c, double x, double y)
	{
		System.out.println("received player position from client: " + c.getName() + ", Position: " + x + "|" + y);
	}
}

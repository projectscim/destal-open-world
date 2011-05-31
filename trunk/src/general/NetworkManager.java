package general;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class NetworkManager implements Runnable
{
	private Server _server;
	private Controller _controller;
	private ServerSocket _serverSocket;
	private ArrayList<ClientConnection> _clientConnections;
	
	public NetworkManager(Server server, Controller controller)
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
	
	public void clientRequestChunkbuffer(ClientConnection c)
	{
		System.out.println("sending chunk buffer to client: '" + c.getName() + "'");
		
		Chunk[] buffer = new Chunk[9];
		int i = 0;
		for (int x = 0; x < 3; x++)
		{
			for (int y = 0; y < 3; y++)
			{
				buffer[i++] = _controller.world().getLevels()[0].getChunk(x, y);
			}
		}
		
		Packet p = new Packet(MSGType.MSG_SV_RESPONSE_CHUNKBUFFER);
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
}

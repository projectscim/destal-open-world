package general;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class NetworkManager implements Runnable
{
	private Server _server;
	private ServerSocket _serverSocket;
	private ArrayList<ClientConnection> _clientConnections;
	
	public NetworkManager(Server server)
	{
		_server = server;
		_clientConnections = new ArrayList<ClientConnection>();
	}
	
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
				break;
			}
        }
	}
	
	public void send(int ID, Packet data)
	{
		try
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
		catch (Exception e)
		{
			System.out.println("exception occured");
		}
	}
	
	public void clientDisconnected(ClientConnection c)
	{
		System.out.println("client left");
		_clientConnections.remove(c);
	}
}

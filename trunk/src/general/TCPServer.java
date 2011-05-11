package general;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.io.IOException;

public class TCPServer
{

	private Controller _controller;
	
	// TODO: move this to Network
	private ServerSocket _serverSocket;
	private ArrayList<ClientConnection> _clientConnections;

	public static void main(String[] args) throws Exception
	{
		new TCPServer();
	}
	
	public TCPServer()
	{
		_clientConnections = new ArrayList<ClientConnection>();
		Thread listenerThread = new Thread(new ClientListener(this));
		listenerThread.start();
	}
	
	public void clientDisconnected(ClientConnection c)
	{
		_clientConnections.remove(c);
		_clientConnections.trimToSize();
	}
	
	protected class ClientListener implements Runnable
	{
		private TCPServer _server;
		
		public ClientListener(TCPServer server)
		{
			_server = server;
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
					_clientConnections.add(new ClientConnection(s, _server));
					System.out.println("new client: " + (_clientConnections.size()-1));
		            new Thread(_clientConnections.get(_clientConnections.size()-1)).start();
				}
				catch (Exception e)
				{
					break;
				}
				
	        }
		}
	}
}
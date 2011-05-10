package general;

import java.net.ServerSocket;
import java.net.Socket;

public class TCPServer
{

	private Controller _controller;
	
	// TODO: move this to Network
	private ServerSocket _serverSocket;
	private ClientConnection[] _clients;

	public static void main(String[] args) throws Exception
	{
		(new TCPServer()).run();
	}
	
	public TCPServer()
	{
		_clients = new ClientConnection[32];
	}

	public void run() throws Exception
	{
			_serverSocket = new ServerSocket(4185);
			
			int NumClients = 0;
			while(true)
	        {
				if(NumClients >= _clients.length)
					continue;
				
				Socket s = _serverSocket.accept();
				System.out.println("new client: " + NumClients);
				_clients[NumClients] = new ClientConnection(s, NumClients);
	            new Thread(_clients[NumClients]).start();
	            NumClients++;
	        }
	}
}

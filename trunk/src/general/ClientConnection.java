package general;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ClientConnection implements Runnable
{
	private Socket _socket;
	private ObjectInputStream _input;
	private ObjectOutputStream _output;
	private TCPServer _server;
	
	public ClientConnection(Socket s, TCPServer server) throws Exception
	{
		_socket = s;
		_input = new ObjectInputStream(_socket.getInputStream());
		_output = new ObjectOutputStream(_socket.getOutputStream());
		_server = server;
	}
	
	public void run()
	{
		System.out.println("thread started");
		try
        {
			while(true) // TODO stop function
			{
				System.out.println(recv());
				
				send("Hi");
			}
        }
		catch(Exception e)
		{
			System.out.println("exception occured");
		}
		System.out.println("thread stopped");
		_server.clientDisconnected(this);
	}
	
	public Object recv() throws Exception
	{
		return _input.readObject();
	}
	
	public void send(Object packet) throws Exception
	{
		_output.writeObject(packet);
		_output.flush();
	}
}

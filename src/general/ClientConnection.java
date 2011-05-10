package general;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ClientConnection implements Runnable
{
	private int _ID;
	
	private Socket _socket;
	private ObjectInputStream _input;
	private ObjectOutputStream _output;
	
	public ClientConnection(Socket s, int ID) throws Exception
	{
		_ID = ID;
		_socket = s;
		_input = new ObjectInputStream(_socket.getInputStream());
		_output = new ObjectOutputStream(_socket.getOutputStream());
	}
	
	public void run()
	{
		System.out.println("thread started (" + _ID + ")");
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
		}
		System.out.println("thread stopped (" + _ID + ")");
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

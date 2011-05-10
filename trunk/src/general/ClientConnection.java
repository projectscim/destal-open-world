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
				System.out.println(_input.readObject());
				
				_output.writeObject("Hi");
				_output.flush();
			}
        }
		catch(Exception e)
		{
		}
	}
}

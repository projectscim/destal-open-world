package general;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ClientConnection implements Runnable
{
	private Socket _socket;
	private ObjectInputStream _input;
	private ObjectOutputStream _output;
	private NetworkManager _networkManager;
	
	private String _clientName;
	
	public ClientConnection(Socket s, NetworkManager network) throws Exception
	{
		_socket = s;
		_input = new ObjectInputStream(_socket.getInputStream());
		_output = new ObjectOutputStream(_socket.getOutputStream());
		_networkManager = network;
	}
	
	@Override
	public void run()
	{
		System.out.println("thread started");
		try
        {
			while(true) // TODO stop function
			{
				Packet p = recv();
				if(p.getType() == MSGType.MSG_CL_TEST)
				{
					_clientName = (String)p.get();
					_networkManager.controller().onClientEnter(this);
				}
			}
        }
		catch(Exception e)
		{
			e.printStackTrace();
		}
		System.out.println("thread stopped");
		_networkManager.clientDisconnected(this);
	}
	
	public String getName()
	{
		return _clientName;
	}
	
	public Packet recv() throws Exception
	{
		return (Packet)_input.readObject();
	}
	
	public void send(Packet data)
	{
		try
        {
			_output.writeObject(data);
			_output.flush();
		}
		catch(IOException e)
		{
			System.out.println("exception occured: couldn't send the packet");
		}
	}
}

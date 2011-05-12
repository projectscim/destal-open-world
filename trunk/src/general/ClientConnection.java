package general;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ClientConnection implements Runnable
{
	private Socket _socket;
	private ObjectInputStream _input;
	private ObjectOutputStream _output;
	private NetworkManager _networkManager;
	
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
					System.out.println("Client: " + (String)p.get());
				}
				
				Packet r = new Packet(MSGType.MSG_SV_TEST);
				r.set("Hi");
				r.set(10);
				send(r);
			}
        }
		catch(Exception e)
		{
			System.out.println("exception occured");
		}
		System.out.println("thread stopped");
		_networkManager.clientDisconnected(this);
	}
	
	public Packet recv() throws Exception
	{
		return (Packet)_input.readObject();
	}
	
	public void send(Packet data) throws Exception
	{
		_output.writeObject(data);
		_output.flush();
	}
}

package general;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class NetworkClient implements Runnable
{
	private Socket _socket;
	private ObjectInputStream _input;
	private ObjectOutputStream _output;
	
	private Client _client;
	
	public NetworkClient(Client client)
	{
		_client = client;
	}

	@Override
	public void run()
	{
		System.out.println("started netclient");
		try
		{
			Socket _socket = new Socket("localhost", 4185);
			_output = new ObjectOutputStream(_socket.getOutputStream());
			_input = new ObjectInputStream(_socket.getInputStream());
			System.out.println("connected to server");
			
			Packet p = new Packet(MSGType.MSG_CL_TEST);
			p.set("John Doe");
			send(p);
			
			Packet r = recv();
			if(r.getType() == MSGType.MSG_SV_TEST)
			{
				Chunk c = (Chunk)r.get();
				for (int x = 0; x < World.CHUNK_SIZE; x++)
				{
					for (int y = 0; y < World.CHUNK_SIZE; y++)
					{
						c.getBlocks()[x][y].initImage();
					}
				}
				_client.setCurrentChunk(c);
				System.out.println("received chunk from server");
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
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

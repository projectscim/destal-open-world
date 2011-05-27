package general;

public class Controller
{
	private World _world;
	
	public Controller()
	{
		//...
	}
	
	public void loadWorld(String name)
	{
		_world = new World(name);
	}
	
	public void onClientEnter(ClientConnection client)
	{
		System.out.println("sending chunk to client: '" + client.getName() + "'");
		
		Chunk[] buffer = new Chunk[9];
		int i = 0;
		for (int x = 0; x < 3; x++)
		{
			for (int y = 0; y < 3; y++)
			{
				buffer[i++] = _world.getLevels()[0].getChunk(x, y);
			}
		}
		//Packet p = new Packet(MSGType.MSG_SV_TEST);
		Packet p = new Packet(MSGType.MSG_SV_SEND_CHUNKBUFFER);
		//p.set(_world.getLevels()[0].getChunk(0, 0));
		p.set(buffer);
		client.send(p);
	}
}

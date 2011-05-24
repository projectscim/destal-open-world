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
		Packet p = new Packet(MSGType.MSG_SV_TEST);
		p.set(_world.getLevels()[0].getChunk(0, 0));
		client.send(p);
	}
}

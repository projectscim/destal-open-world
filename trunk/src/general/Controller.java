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
	
	public World world()
	{
		return _world;
	}
}

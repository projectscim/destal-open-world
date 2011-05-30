package general;

import java.util.EventObject;

public class PlayerMovementEvent extends EventObject
{
	private WorldPoint _location;
	
	public PlayerMovementEvent(Object source)
	{
		super(source);
	}

	public WorldPoint getLocation()
	{
		return _location;
	}
	public void setLocation(WorldPoint location)
	{
		_location = location;
	}
}

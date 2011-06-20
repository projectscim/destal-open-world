package destal.event.events.player;

import java.util.EventObject;

import destal.general.world.WorldPoint;

public class PlayerActionEvent extends EventObject
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -1730614988225309241L;
	
	public enum EventType {BUILD_HOUSE}
	private WorldPoint _location;
	/**
	 * Creates a new PlayerActionEvent
	 * @param source The object in which the event is invoked
	 */
	public PlayerActionEvent(Object source)
	{
		super(source);
	}
	public void setLocation(WorldPoint location)
	{
		_location = location;
	}
	public WorldPoint getLocation()
	{
		return _location;
	}
}

package destal.event.events;


import java.util.EventObject;

import destal.general.world.WorldPoint;

/**
 * An event which indicates that the player has moved
 *
 */
public class PlayerMovementEvent extends EventObject
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 5696388540638966219L;
	private WorldPoint _location;
	
	public PlayerMovementEvent(Object source)
	{
		super(source);
	}
	public PlayerMovementEvent(Object source, WorldPoint newLocation)
	{
		this(source);
		setLocation(newLocation);
	}
	/**
	 * Returns the new location of the player
	 * @return The new location of the player as a WorldPoint
	 */
	public WorldPoint getLocation()
	{
		return _location;
	}
	public void setLocation(WorldPoint location)
	{
		_location = location;
	}
}

package destal.client.event;

import java.util.EventObject;

import destal.shared.world.WorldPoint;

public class PlayerActionEvent extends EventObject
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -1730614988225309241L;
	
	public enum EventType {BUILD_HOUSE, BLOCK_CLICKED, REQUEST_MOVE}
	private WorldPoint _location;
	private WorldPoint _direction;
	private int _buildingType;
	private EventType _eventType;
	
	/**
	 * Creates a new PlayerActionEvent
	 * @param source The object in which the event is invoked
	 */
	public PlayerActionEvent(Object source, EventType eventType)
	{
		super(source);
		_eventType = eventType;
	}
	public void setLocation(WorldPoint location)
	{
		_location = location;
	}
	public WorldPoint getLocation()
	{
		return _location;
	}
	public void setDirection(WorldPoint direction)
	{
		_direction = direction;
	}
	public WorldPoint getDirection()
	{
		return _direction;
	}
	public void setBuildingType(int buildingType)
	{
		_buildingType = buildingType;
	}
	public int getBuildingType()
	{
		return _buildingType;
	}
	public EventType getEventType()
	{
		return _eventType;
	}
}

package destal.event.events;

import java.awt.Point;
import java.util.EventObject;

import destal.general.net.server.ClientConnection;
import destal.general.world.WorldPoint;

public class PacketReceivedServerEvent extends EventObject
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -7823769737397870673L;
	private WorldPoint _point;
	private Point[] _points;
	
	public PacketReceivedServerEvent(ClientConnection client)
	{
		super(client);
		_point = new WorldPoint(0,0);
	}
	
	public ClientConnection getClient()
	{
		return (ClientConnection)getSource();
	}
	
	public void setPoint(WorldPoint point)
	{
		_point = point;
	}
	
	public void setPoint(double x, double y)
	{
		_point.setLocation(x, y);
	}
	
	public void setPoint(int x, int y)
	{
		_point.setLocation(x, y);
	}
	
	public WorldPoint getPoint()
	{
		return _point;
	}
	
	public void setPoints(Point[] points)
	{
		_points = points;
	}
	
	public Point[] getPoints()
	{
		return _points;
	}
}

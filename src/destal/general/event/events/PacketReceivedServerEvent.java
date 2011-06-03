package destal.general.event.events;

import java.util.EventObject;

import destal.general.net.server.ClientConnection;
import destal.general.world.WorldPoint;

public class PacketReceivedServerEvent extends EventObject
{
	private WorldPoint _point;
	private int _clientID;
	
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
	
	public void setClientID(int id)
	{
		_clientID = id;
	}
	
	public int getClientID()
	{
		return _clientID;
	}
}

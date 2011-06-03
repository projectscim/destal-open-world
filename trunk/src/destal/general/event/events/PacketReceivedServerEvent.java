package destal.general.event.events;

import java.awt.Point;
import java.util.EventObject;

import destal.general.net.server.ClientConnection;
import destal.general.world.Chunk;

public class PacketReceivedServerEvent extends EventObject
{
	//private Chunk _chunk[];
	private Point _point;
	
	public PacketReceivedServerEvent(ClientConnection client)
	{
		super(client);
		//_chunk = new Chunk[9];
		_point = new Point(0,0);
	}
	
	public ClientConnection getClient()
	{
		return (ClientConnection)getSource();
	}
	
	/*public void setChunk(Chunk chunk)
	{
		_chunk[0] = chunk;
	}
	
	public Chunk getChunk()
	{
		return _chunk[0];
	}
	
	public void setChunkBuffer(Chunk[] chunk)
	{
		_chunk = chunk;
	}
	
	public Chunk[] getChunkBuffer()
	{
		return _chunk;
	}*/
	
	public void setPoint(Point point)
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
	
	public Point getPoint()
	{
		return _point;
	}
}

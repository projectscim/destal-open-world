package destal.general.event.events;

import java.util.EventObject;

import destal.general.net.client.NetworkClient;
import destal.general.world.Chunk;
import destal.general.world.WorldPoint;

public class PacketReceivedClientEvent extends EventObject
{
	private Chunk _chunk[];
	private WorldPoint _point;
	private String _MOTD;
	private int _clientID;
	
	public PacketReceivedClientEvent(NetworkClient source)
	{
		super(source);
		_chunk = new Chunk[9];
		_point = new WorldPoint(0,0);
		_MOTD = "";
	}
	
	public NetworkClient getNetworkClient()
	{
		return (NetworkClient)getSource();
	}
	
	public void setChunk(Chunk chunk)
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
	
	public void setMOTD(String MOTD)
	{
		_MOTD = MOTD;
	}
	
	public String getMOTD()
	{
		return _MOTD;
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

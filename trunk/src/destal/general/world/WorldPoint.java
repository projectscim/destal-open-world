package destal.general.world;

import java.awt.Point;

public class WorldPoint extends Point.Double
{
	public WorldPoint()
	{
		super();
	}
	public WorldPoint(int x, int y)
	{
		super(x, y);
	}
	public WorldPoint(double x, double y)
	{
		super(x,y);
	}
	public WorldPoint(int xLevel, int yLevel, int xChunk, int yChunk)
	{
		this(xLevel*World.CHUNK_SIZE+xChunk, yLevel*World.CHUNK_SIZE+yChunk);
	}
	
	public WorldPoint getRelativePoint(WorldPoint p, int x, int y)
	{
		return new WorldPoint((int)p.getX()-x, (int)p.getY()-y);
	}
	
	// TODO: does not work for negative values
	public Point getChunkLocation()
	{
		return new Point((int)this.x/World.CHUNK_SIZE, (int)this.y/World.CHUNK_SIZE);
	}
	// TODO: does not work for negative values
	public Point getLocationInChunk()
	{
		return new Point((int)(this.getX()%World.CHUNK_SIZE),
						 (int)(this.getY()%World.CHUNK_SIZE));
	}
	
	public Point getLocationOnPanel(double x, double y)
	{
		return new Point((int)((this.getX()- x)*World.BLOCK_PAINTSIZE), (int)((this.getY()- y)*World.BLOCK_PAINTSIZE));
	}
}

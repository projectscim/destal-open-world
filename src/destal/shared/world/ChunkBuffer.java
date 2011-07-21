package destal.shared.world;

import java.io.IOException;
import java.util.ArrayList;

import destal.shared.entity.block.Block;
/**
 * Enables the user class to store the last used chunks temporarily instead
 * of loading them again from the HDD
 * @author Alex Belke, Dennis Sternberg, Steffen Schneider
 */
public class ChunkBuffer
{
	private ArrayList<Chunk> _chunks;
	private World _world;
	private int _maxSize;
	/**
	 * Creates a new ChunkBuffer instance
	 * @param world the world from which chunks should be loaded
	 * @param maxSize the buffer's maximum size
	 */
	public ChunkBuffer(World world, int maxSize)
	{
		_chunks = new ArrayList<Chunk>();
		_maxSize = maxSize;
		_world = world;
	}
	/**
	 * Returns the chunk buffer's maximum size
	 * @return The maximum buffer size
	 */
	public int getMaximumSize()
	{
		return _maxSize;
	}
	/**
	 * Adds a new chunk to the buffer
	 * If this operation causes a buffer overflow, another chunk will be removed
	 * first
	 * @param c The chunk which should be added
	 */
	public void addChunk(Chunk c)
	{
		if (_chunks.size() > getMaximumSize())
		{
			_chunks.remove(0);
		}
		_chunks.add(c);
	}
	/**
	 * Returns the chunk at the specified location.
	 * If the requested chunk is not within the buffer, it will be loaded and
	 * added to the buffer automatically.
	 * @param x The chunk's horizontal position in the world
	 * @param y The chunk's vertical position in the world
	 * @return The chunk
	 */
	public Chunk getChunk(int x, int y)
	{
		for (Chunk c : _chunks)
		{
			if (c != null && c.getLocation().x == x && c.getLocation().y == y)
			{
				return c;
			}
		}
		try
		{
			addChunk(_world.getChunk(x, y, 0));
			return getChunk(x, y);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		throw new IllegalArgumentException("Cannot load chunk");
	}
	/**
	 * Returns the chunk at the specified location.
	 * If the requested chunk is not within the buffer, it will be loaded and
	 * added to the buffer automatically.
	 * @param x The chunk's horizontal position in the world
	 * @param y The chunk's vertical position in the world
	 * @param reload If true, the chunk will be loaded again before returning
	 * @return The chunk
	 */
	public Chunk getChunk(int x, int y, boolean reload)
	{
		for (Chunk c : _chunks)
		{
			if (c != null && c.getLocation().x == x && c.getLocation().y == y)
			{
				if (reload)
				{
					try
					{
						_chunks.set(_chunks.indexOf(c), _world.getChunk(x, y, 0));
					}
					catch (IOException e)
					{
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				return c;
			}
		}
		try
		{
			addChunk(_world.getChunk(x, y, 0));
			return getChunk(x, y);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		throw new IllegalArgumentException("Cannot load chunk");
	}
	/**
	 * Updates the chunk at the specified location
	 * @param x The chunk's horizontal position in the world
	 * @param y The chunk's vertical position in the world
	 */
	public void updateChunk(int x, int y)
	{
		getChunk(x,y,true);
	}
	/**
	 * Returns the block at the specified location
	 * @param x The blocks's horizontal position in the world
	 * @param y The blocks's vertical position in the world
	 * @return The block
	 */
	public Block getBlock(int x, int y)
	{
		WorldPoint location = new WorldPoint(x, y);
		return getBlock(location);
	}
	/**
	 * Returns the block at the specified location
	 * @param location The block's location in the world
	 * @return The block
	 */
	public Block getBlock(WorldPoint location)
	{
		return getChunk(location.getChunkLocation().x, location.getChunkLocation().y)
				.getBlock(location.x, location.y);
	}
}

/*******************************************************************************
 * destal open world, an open source java multiplayer game
 * Copyright (C) 2011 Alexander Belke, Dennis Sternberg, Steffen Schneider
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/
package destal.general.world;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.io.File;
import java.io.IOException;

public class World
{
	public final static int CHUNK_SIZE = 32;
	public final static int BLOCK_PAINTSIZE = 32;
	public final static int LEVEL_SIZE = 16;
	public final static int LEVEL_QUANTITY = 3;
	
	public final static String WORLD_PATH = "data/world/";

	private Level[] _levels;
	private String _name;
	
	public Level[] getLevels()
	{
		return _levels;
	}

	public void setLevels(Level[] levels)
	{
		this._levels = levels;
	}
	
	public String getPath()
	{
		return WORLD_PATH + _name + "/";
	}

	public World(String name)
	{
		_name = name;
		_levels = new Level[LEVEL_QUANTITY];
		
		try
		{
			if(!(new File(getPath() + ".world")).exists())
			{
				createWorld();
				loadLevels();
				System.out.println("created world: '" + getName() + "'");
			}
			else
			{
				loadLevels();
				System.out.println("loaded world: '" + getName() + "'");
			}
		}
		catch (IOException e)
		{
			System.out.println("couldn't load world: '" + getName() + "'");
		}
	}
	
	protected void createWorld() throws IOException
	{
		// Create directory for the worlds
		(new File(WORLD_PATH)).mkdir();
		// Create directory for the new world
		(new File(getPath())).mkdir();
		// Create new World
		(new File(getPath() + ".world")).createNewFile();
	}
	
	protected String getName()
	{
		return _name;
	}
	
	protected void loadLevels() throws IOException
	{
		for (int i = 0; i < LEVEL_QUANTITY; i++)
		{
			_levels[i] = new Level(getPath() + "lvl_" + i + "/");
		}
	}
	
	public Chunk getChunk(int x, int y, int level) throws IOException
	{
		return getLevels()[level].getChunk(x, y);
	}
	
	public void paint(Graphics g, int level, int xChunk, int yChunk, Point location) throws IOException
	{
		Rectangle r = g.getClipBounds();
		int blockXQuantity = (int) (r.getWidth() / BLOCK_PAINTSIZE);
		int blockYQuantity = (int) (r.getHeight() / BLOCK_PAINTSIZE);
		
		for (int x = 0; x < blockXQuantity; x++)
		{
			for (int y = 0; y < blockYQuantity; y++)
			{
				getChunk(xChunk, yChunk, level).getBlocks()[x][y].paint(g);
			}
		}
	}
	
	public Point getAbsoluteLocation(int ChunkX, int ChunkY, Point location)
	{
		return new Point(ChunkX * CHUNK_SIZE + (int)location.getX(),
				ChunkY * CHUNK_SIZE + (int) location.getY());
	}
}

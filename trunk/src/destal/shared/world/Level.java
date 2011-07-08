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
package destal.shared.world;

import java.awt.Point;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Level
{
	@SuppressWarnings("unused")
	private ArrayList<Character> _characters;
	private String _dir;

	public Level(String dir) throws IOException
	{
		// Create data file
		_dir = dir;
		_characters = new ArrayList<Character>();
		
		if (!new File(_dir + ".level").exists())
		{
			// Create folder for the current Level
			(new File(_dir)).mkdir();
			// Create the current Level
			(new File(_dir + ".level")).createNewFile();

			System.out.println("created new level");
		}
	}
	
	public void createLevel(int width, int height) throws IOException
	{
		for (int x = 0; x < width; x++)
		{
			for (int y = 0; y < height; y++)
			{
				getChunk(x, y);
			}
		}
	}
	
	public void createCompleteLevel() throws IOException
	{
		createLevel(World.LEVEL_SIZE, World.LEVEL_SIZE);
	}
	
	public Chunk getChunk(int x, int y)
	{
		// load chunk or create new one
		try 
		{
			if(getChunkFile(x,y).exists())
			{
				return new Chunk(getChunkFile(x,y), new Point(x,y));
			}
			else
			{
				System.out.println("generating chunk (" + x + "/" + y + ")");
				Chunk c = new Chunk(new Point(x,y));
				c.create();
				c.saveChunk(getChunkFile(x,y));
				return c;
			}
		}
		catch (IOException e)
		{
			System.out.println("couldn't load chunk (" + x + "|" + y + ")");
			//e.printStackTrace();
			return null;
		}
	}
	
	protected File getChunkFile(int x, int y) throws IOException
	{
		return new File(_dir + "chunk" + "_" + x + "_" + y);
	}
}

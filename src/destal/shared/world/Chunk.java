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

import java.awt.Graphics;
import java.awt.Point;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;
import java.util.Vector;

import destal.shared.entity.block.Block;
import destal.shared.entity.building.Building;
import destal.shared.entity.data.Values;
import destal.shared.entity.item.Item;

public class Chunk implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -6178629598857083217L;
	private Point _location;
	private Block[][] _blocks;
	private Vector<Item> _items;
	private Vector<Building> _buildings;

	private Chunk()
	{
		setBlocks(new Block[World.CHUNK_SIZE][World.CHUNK_SIZE]);
		setItems(new Vector<Item>());
		_buildings = new Vector<Building>();
	}
	
	public Chunk(Point location)
	{
		this();
		_location = location;
	}
	
	public Chunk(File file, Point location) throws IOException
	{
		this(location);
		this.loadFile(file);
	}
	
	public void buildHouse(Building building)
	{
		_buildings.add(building);
	}
	
	public Vector<Building> getHouses()
	{
		return _buildings;
	}
	
	public Point getLocation()
	{
		return _location;
	}
	
	public void setBlocks(Block[][] blocks)
	{
		this._blocks = blocks;
	}

	public Block[][] getBlocks()
	{
		return _blocks;
	}
	/**
	 * Returns the block with the requested location
	 * @param x the block's x position (WorldPoint)
	 * @param y the block's y position (WorldPoint)
	 * @return the block, if the specified point is inside the chunk, else: null
	 */
	public Block getBlock(double x, double y)
	{
		for (Block[] bl : _blocks)
		{
			for (Block b : bl)
			{
				if (b.getLocation().equals(new WorldPoint(x, y)))
					return b;
				
			}
		}
		return null;
	}

	public void setItems(Vector<Item> items)
	{
		_items = items;
	}

	public Vector<Item> getItems()
	{
		return _items;
	}

	private void loadFile(File file) throws IOException
	{
		FileInputStream fs = new FileInputStream(file);
		
		for (int x = 0; x < World.CHUNK_SIZE; x++)
		{
			for (int y = 0; y < World.CHUNK_SIZE; y++)
			{
				getBlocks()[x][y] = Block.create(fs.read());
				getBlocks()[x][y].setLocation(new WorldPoint((int)_location.getX(), (int)_location.getY(), x, y));
			}
		}
		// TODO optimize
		int size = fs.read();
		for (int i = 0; i < size; i++)
		{
			int v = fs.read();
			int x = fs.read();
			int y = fs.read();
			Building b = Building.create(v);
			b.setLocation(new WorldPoint(_location.x, _location.y, x, y));
			_buildings.add(b);
		}
		fs.close();
	}

	/**
	 * Saves the current Chunk instance in the specified binary file
	 * Note: If the file not exists, it is automatically generated
	 * If the file exists, the existing file will be overwritten
	 * @param file The binary file
	 * @throws IOException If an I/O error occurs
	 * @throws NullPointerException If a block is missing in the current Chunk
	 */
	public void saveChunk(File file) throws IOException, NullPointerException
	{
		file.createNewFile();
		FileOutputStream fs = new FileOutputStream(file);
		
		for (int x = 0; x < World.CHUNK_SIZE; x++)
		{
			for (int y = 0; y < World.CHUNK_SIZE; y++)
			{
				if (this.getBlocks()[x][y] != null)
				{
					fs.write(this.getBlocks()[x][y].getDataValue());
				}
				else
				{
					throw new NullPointerException();
				}
			}
		}
		// TODO optimize
		_buildings.trimToSize();
		fs.write(_buildings.size());
		for (Building h : _buildings)
		{
			int v = h.getDataValue();
			int x = h.getLocation().getLocationInChunk().x;
			int y = h.getLocation().getLocationInChunk().y;
			fs.write(v);
			fs.write(x);
			fs.write(y);
		}
		fs.close();
	}
	/**
	 * Returns a new Chunk with random blocks
	 * @return The generated Chunk
	 */
	public void create()
	{
		for (int x = 0; x < World.CHUNK_SIZE; x++)
		{
			for (int y = 0; y < World.CHUNK_SIZE; y++)
			{
				// fill chunk with dirt
				this.getBlocks()[x][y] = Block.create(Values.BLOCK_DIRT);
				this.getBlocks()[x][y].setLocation(new WorldPoint((int)_location.getX(), (int)_location.getY(), x, y));
			}
		}
		Random r = new Random();
		while (r.nextDouble() < 0.99)
		{
			int x = r.nextInt(getBlocks().length);
			int y = r.nextInt(getBlocks().length);
			ArrayList<Block> blocks = getNeighbourBlocks(x, y);
			for (int i = 0; i < blocks.size(); i++)
			{
				int xP = blocks.get(i).getLocation().getLocationInChunk().x;
				int yP = blocks.get(i).getLocation().getLocationInChunk().y;
				Block newB = getBlocks()[x][y].getNextBlock();
				newB.setLocation(getBlocks()[xP][yP].getLocation());
				getBlocks()[xP][yP] = newB;
			}
		}
		
		
		/*for (int x = 0; x < World.CHUNK_SIZE; x++)
		{
			for (int y = 0; y < World.CHUNK_SIZE; y++)
			{
				Random rnd = new Random();

				ArrayList<Block> blocks = this.getNeighbourBlocks(x, y);
				getBlocks()[x][y] = blocks.get(r.nextInt(blocks.size())).getNextBlock();
				
				//System.out.println(getBlocks()[x][y]);
				//this.getBlocks()[x][y].setLocation(new WorldPoint((int)_location.getX(), (int)_location.getY(), x, y));
			}
		}*/
	}
	// TODO choose better name!
	private ArrayList<Block> getNeighbourBlocks(int x, int y)
	{
		ArrayList<Block> blocks = new ArrayList<Block>();
		for (int xd = -1; xd <= 1; xd++)
		{
			for (int yd = -1; yd <= 1; yd++)
			{
				try
				{
					Block b = this.getBlocks()[x+xd][y+yd];
					if (b != null)
						blocks.add(b);
				}
				catch (ArrayIndexOutOfBoundsException e)
				{
					// TODO rework
				}
			}
		}
		blocks.trimToSize();
		return blocks;
		//Random r = new Random();
		//return blocks.get(r.nextInt(blocks.size()));
	}
	
	/*@Override
	public String toString()
	{
		String s = "";
		int n = 0;
		for (int x = 0; x < World.CHUNK_SIZE; x++)
		{
			s += (++n) + "\t";
			for (int y = 0; y < World.CHUNK_SIZE; y++)
			{
				s += getBlocks()[x][y].getDataValue();
			}
			s += "\n";
		}
		return s;
	}*/
}

package destal.general.world;

import java.awt.Graphics;
import java.awt.Point;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.Random;
import java.util.Vector;

import destal.entities.Item;
import destal.entities.Values;
import destal.entities.blocks.Block;
import destal.entities.buildings.House;

public class Chunk implements Serializable
{
	private Point _location;
	private Block[][] _blocks;
	private Vector<Item> _items;
	private Vector<House> _houses;

	private Chunk()
	{
		setBlocks(new Block[World.CHUNK_SIZE][World.CHUNK_SIZE]);
		setItems(new Vector<Item>());
		_houses = new Vector<House>();
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
	
	public void buildHouse(House house)
	{
		_houses.add(house);
	}
	
	public Vector<House> getHouses()
	{
		return _houses;
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
	
	public Block getBlock(int x, int y)
	{
		for (Block[] bl : _blocks)
		{
			for (Block b : bl)
			{
				if (b.getLocation() == new WorldPoint(x, y))
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
			int x = fs.read();
			int y = fs.read();
			_houses.add(new House(new WorldPoint((int)_location.getX(), (int)_location.getY(), x, y)));
		}
		fs.close();
	}
	/*
	/**
	 * Returns a new Chunk generated based on the specified binary file
	 * @param file The binary file
	 * @throws IOException If an I/O error occurs
	 * @throws IllegalArgumentException If there is an illegal value in the input file
	 /
	public static Block[][] createFromFile(File file) throws IOException
	{
		FileInputStream fs = new FileInputStream(file);
		Block[][] blocks = new Block[World.CHUNK_SIZE][World.CHUNK_SIZE];
		
		for (int x = 0; x < World.CHUNK_SIZE; x++)
		{
			for (int y = 0; y < World.CHUNK_SIZE; y++)
			{
				blocks[x][y] = Block.create(fs.read());
			}
		}
		fs.close();
		return blocks;
	}
	*/
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
		_houses.trimToSize();
		fs.write(_houses.size());
		for (House h : _houses)
		{
			int x = h.getLocation().getLocationInChunk().x;
			int y = h.getLocation().getLocationInChunk().y;
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
				Random rnd = new Random();
				int r = rnd.nextInt(100);
				if (r <= 5)
				{
					this.getBlocks()[x][y] = Block.create(Values.BLOCK_TREE);
					// Test houses
					// TODO remove when finished
					House h = new House(new WorldPoint((int)_location.getX(), (int)_location.getY(), x, y));
					this.buildHouse(h);
				}
				else if (r <= 15)
				{
					this.getBlocks()[x][y] = Block.create(Values.BLOCK_STONE);
				}
				else
				{
					this.getBlocks()[x][y] = Block.create(Values.BLOCK_DIRT);
				}
				
				this.getBlocks()[x][y].setLocation(new WorldPoint((int)_location.getX(), (int)_location.getY(), x, y));
				
				
			}
		}
	}
	
	public byte[] toByteArray()
	{
		byte[] b = new byte[World.CHUNK_SIZE * World.CHUNK_SIZE];
		int i = 0;
		for (int x = 0; x < World.CHUNK_SIZE; x++)
		{
			for (int y = 0; y < World.CHUNK_SIZE; y++)
			{
				b[++i] = (byte)getBlocks()[x][y].getDataValue();
			}
		}
		return b;
	}
	
	@Override
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
	}
	
	public void paint(Graphics g)
	{
		for (int x = 0; x < World.CHUNK_SIZE; x++)
		{
			for (int y = 0; y < World.CHUNK_SIZE; y++)
			{
				getBlocks()[x][y].paint(g);
			}
		}
	}
}

package general;

import java.io.*;
import java.util.*;

import entities.*;


public class Chunk
{
	private Block[][] _blocks;
	private Item[][] _items;

	public Chunk()
	{
		setBlocks(new Block[World.CHUNK_SIZE][World.CHUNK_SIZE]);
		setItems(new Item[World.CHUNK_SIZE][World.CHUNK_SIZE]);
	}
	
	public Chunk(String chunkFilePath) throws IOException
	{
		this();
		File f = new File(chunkFilePath);
		loadFile(f);
	}
	
	public void setBlocks(Block[][] blocks) {
		this._blocks = blocks;
	}

	public Block[][] getBlocks() {
		return _blocks;
	}

	public void setItems(Item[][] items) {
		this._items = items;
	}

	public Item[][] getItems() {
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
			}
		}
		fs.close();
	}
	/**
	 * Returns a new Chunk generated based on the specified binary file
	 * @param file The binary file
	 * @throws IOException If an I/O error occurs
	 * @throws IllegalArgumentException If there is an illegal value in the input file
	 */
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
		fs.close();
	}
	/**
	 * Returns a new Chunk with random blocks
	 * @return The generated Chunk
	 */
	public static Chunk createDefaultChunk()
	{
		Chunk chunk = new Chunk();
		for (int x = 0; x < World.CHUNK_SIZE; x++)
		{
			for (int y = 0; y < World.CHUNK_SIZE; y++)
			{
				Random rnd = new Random();
				chunk.getBlocks()[x][y] = Block.create(rnd.nextInt(3));
			}
		}
		return chunk;
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
}

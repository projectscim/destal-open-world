package general;

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
			
			createLevel(3,3);
			System.out.println("created new level");
		}
	}
/*
	public void setChunks(Chunk[][] chunks)
	{
		this._chunks = chunks;
	}

	public Chunk[][] getChunks()
	{
		return _chunks;
	}*/
	/*
	public static Chunk[][] createFromFile(File file) throws IOException
	{
		FileInputStream fs = new FileInputStream(file);
		Chunk[][] chunk = new Chunk[World.LEVEL_SIZE][World.LEVEL_SIZE];
		
		for (int x = 0; x < World.LEVEL_SIZE; x++)
		{
			for (int y = 0; y < World.LEVEL_SIZE; y++)
			{
				//chunk[x][y] = Chunk.create(fs.read());
			}
		}
		fs.close();
		return chunk;
	}*/
	
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
	
	/*
	public void saveLevel() throws IOException, NullPointerException
	{
		FileOutputStream fs = new FileOutputStream(_levelFile);
		for (int x = 0; x < World.LEVEL_SIZE; x++)
		{
			for (int y = 0; y < World.LEVEL_SIZE; y++)
			{
				// Save the current Chunk
				_chunks[x][y].saveChunk(new File(_chunkDir.getPath() + "/chunk" + x + "_" + y));
				// TODO: Write Chunk data into Level file
			}
		}
		fs.close();
	}*/
	
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
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	private File getChunkFile(int x, int y) throws IOException
	{
		return new File(_dir + "chunk" + "_" + x + "_" + y);
	}
}

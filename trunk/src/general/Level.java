package general;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Level
{
	@SuppressWarnings("unused")
	private ArrayList<Character> _characters;
	private File _chunkDir;
	private File _levelFile;

	public Level(String filePath, String chunkDir) throws IOException
	{
		// Create data file
		_levelFile = new File(filePath);
		_chunkDir = new File(chunkDir);
		if (!_levelFile.exists())
		{
			_levelFile.createNewFile();
			// Create Chunk folder for the current Level
			_chunkDir.mkdir();
			
			this.createDefaultLevel();
			_characters = new ArrayList<Character>();
		}
		
		//this.saveLevel();
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
	
	public Chunk[][] createDefaultLevel() throws NullPointerException, IOException
	{
		Chunk[][] chunk = new Chunk[World.LEVEL_SIZE][World.LEVEL_SIZE];
		for (int x = 0; x < World.LEVEL_SIZE; x++)
		{
			for (int y = 0; y < World.LEVEL_SIZE; y++)
			{
				Chunk.createDefaultChunk().saveChunk(new File(_chunkDir.getPath() + "\\chunk" + x + "_" + y));
			}
		}
		return chunk;
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
				_chunks[x][y].saveChunk(new File(_chunkDir.getPath() + "\\chunk" + x + "_" + y));
				// TODO: Write Chunk data into Level file
			}
		}
		fs.close();
	}*/
	
	public Chunk getChunk(int x, int y) throws IOException
	{
		return new Chunk(_chunkDir.getPath()+"\\chunk" + x + "_" + y);
	}
}

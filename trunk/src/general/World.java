package general;

import java.io.*;
import java.util.*;
import java.awt.*;
import java.lang.*;

public class World
{
	public final static int CHUNK_SIZE = 128;
	public final static int LEVEL_SIZE = 16;
	public final static int LEVEL_QUANTITY = 3;
	public final static String PATH = "C:\\destal open world";
	public final static String WORLD_PATH = World.PATH + "\\world";
	public final static String LEVEL_PATH = World.WORLD_PATH + "\\lvl";

	private Level[] _levels;
	private String _name;
	
	public Level[] getLevels() {
		return _levels;
	}

	public void setLevels(Level[] levels) {
		this._levels = levels;
	}

	public World(String name) throws IOException
	{
		_name = name;
		_levels = new Level[LEVEL_QUANTITY];
		if (!(new File(WORLD_PATH + "\\" + _name + ".world")).exists())
		{
			createDefaultDirectories();
		}
		else
		{
			loadFile(new File(_name));
		}
		createLevels();
	}
	
	private void createDefaultDirectories() throws IOException
	{
		// Create directory for the worlds
		(new File(WORLD_PATH)).mkdir();
		// Create new World
		(new File(WORLD_PATH + "\\" + _name + ".world")).createNewFile();
		// Create directory for the levels of the new World
		(new File(LEVEL_PATH + "_" + _name)).mkdir();
	}
	
	private void createLevels() throws IOException
	{
		for (int i = 0; i < LEVEL_QUANTITY; i++)
		{
			_levels[i] = new Level(LEVEL_PATH + "_" + _name + "\\lvl" + i + ".lvl",
					LEVEL_PATH + "_" + _name + "\\chunks_" + i);
		}
	}
	
	private void loadFile(File file)
	{
//		TODO: Add loading of world-specific data here
		// Loading Level
	}
	
	public Chunk getChunk(int x, int y, int level) throws IOException
	{
		return getLevels()[level].getChunk(x, y);
	}
}

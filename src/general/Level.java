package general;

import java.io.*;
import java.util.*;
import java.awt.*;
import java.lang.*;

public class Level {

	private Chunk[][] _chunks;
	private ArrayList<Character> _characters;

	public Level(Chunk[][] chunks)
	{
		setChunks(chunks);
	}

	public void setChunks(Chunk[][] chunks)
	{
		this._chunks = chunks;
	}

	public Chunk[][] getChunks()
	{
		return _chunks;
	}
	
	
}

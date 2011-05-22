package general;

import java.awt.Point;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import entities.Player;


public class Client
{
	private Chunk _currentChunk;
	private Chunk[] _chunkBuffer;
	private ArrayList<Character> _characters;
	private Player _localPlayer;
	private GUI _gui;

	public Client() throws IOException
	{
		_currentChunk = new Chunk(new File("data/world/test.chnk"), new Point(0,0));
		_localPlayer = new Player();
		_gui = new GUI(600, 200, this);
	}
	
	public Chunk getCurrentChunk()
	{
		return _currentChunk;
	}

	public void setCurrentChunk(Chunk _currentChunk)
	{
		this._currentChunk = _currentChunk;
	}

	public Chunk[] getChunkBuffer()
	{
		return _chunkBuffer;
	}

	public void setChunkBuffer(Chunk[] _chunkBuffer)
	{
		this._chunkBuffer = _chunkBuffer;
	}

	public Player getLocalCharacter()
	{
		return _localPlayer;
	}
	
	public void run()
	{

	}
	
	public static void main(String[] args)
	{
		try
		{
			(new Client()).run();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

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
	
	private NetworkClient _networkClient;
	
	public Client() throws IOException
	{
		DataContainer.create();
		_localPlayer = new Player();
		_gui = new GUI(600, 200, this);
		_networkClient = new NetworkClient(this);
	}
	
	public Chunk getCurrentChunk()
	{
		return _currentChunk;
	}

	public void setCurrentChunk(Chunk _currentChunk)
	{
		_currentChunk = _currentChunk;
		_gui.getGame().setChunk(_currentChunk);
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
		(new Thread(_networkClient)).start();
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

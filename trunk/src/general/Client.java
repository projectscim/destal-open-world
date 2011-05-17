package general;

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
		_localPlayer = new Player();
		_gui = new GUI(600, 200, this);
		_currentChunk = new Chunk();
		_currentChunk.setBlocks(Chunk.createFromFile(new File("C:\\lappen.chnk")));
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
		try {
			(new Client()).run();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}

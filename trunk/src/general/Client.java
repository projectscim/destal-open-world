package general;

import java.util.ArrayList;

import entities.Player;


public class Client
{

	private Chunk currentChunk;
	private Chunk[] _chunkBuffer;
	private ArrayList<Character> _characters;
	private Player _localPlayer;
	private GUI _gui;

	public Client()
	{
		_localPlayer = new Player();
		_gui = new GUI(600, 200, this);
		_gui.addKeyListener(_localPlayer);
	}
	
	public Player getLocalCharacter()
	{
		return _localPlayer;
	}
	
	public void run()
	{
		_gui.run();
	}
	
	public static void main(String[] args)
	{
		(new Client()).run();
	}

}

package General;

import java.io.*;
import java.util.*;
import java.awt.*;
import java.lang.*;

import Entities.Player;

public class TCPClient {

	private Chunk currentChunk;
	private Chunk[] _chunkBuffer;
	private ArrayList<Character> _characters;
	private Player _localPlayer;
	private GUI _gui;

	public TCPClient()
	{
		_gui = new GUI(200, 200, this);
		_localPlayer = new Player();
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
		TCPClient client = new TCPClient();
		client.run();
	}

}

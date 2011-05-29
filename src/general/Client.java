package general;

import java.io.IOException;
import java.util.ArrayList;

import entities.Player;


public class Client
{
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

	public Chunk[] getChunkBuffer()
	{
		return _chunkBuffer;
	}

	public void setChunkBuffer(Chunk[] _chunkBuffer)
	{
		this._chunkBuffer = _chunkBuffer;
		_gui.getGame().setChunkBuffer(_chunkBuffer);
	}

	public Player getLocalCharacter()
	{
		return _localPlayer;
	}
	
	public void connect(String address)
	{
		_networkClient.connect(address);
	}
	
	public void run()
	{
		
	}
	
	public void connected()
	{
		_gui.setGUIMode (GUI.GUIMode.GAME);
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

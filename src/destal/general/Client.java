package destal.general;

import java.awt.Point;
import java.io.IOException;
import java.util.ArrayList;

import destal.entities.Player;
import destal.general.event.events.PlayerMovementEvent;
import destal.general.event.listener.IPlayerMovementListener;
import destal.general.net.MSGType;
import destal.general.net.Packet;
import destal.general.net.client.NetworkClient;
import destal.general.ui.GUI;
import destal.general.world.Chunk;

public class Client implements IPlayerMovementListener
{
	private Chunk[] _chunkBuffer;
	private ArrayList<Character> _characters;
	private Player _localPlayer;
	private GUI _gui;
	
	private NetworkClient _networkClient;
	
	public Client() throws IOException
	{
		DataContainer.create();
		_localPlayer = new Player(this);
		_localPlayer.addPlayerMovementListener(this);
		_gui = new GUI(600, 200, this);
		_networkClient = new NetworkClient(this);
	}

	public Chunk[] getChunkBuffer()
	{
		return _chunkBuffer;
	}

	public void setChunkBuffer(Chunk[] chunkBuffer)
	{
		_chunkBuffer = chunkBuffer;
		_gui.getGame().setChunkBuffer(_chunkBuffer);
	}

	public Player getLocalCharacter()
	{
		return _localPlayer;
	}
	
	public void connect(String address, String username)
	{
		_networkClient.connect(address, username);
	}
	
	public void run()
	{
		
	}
	
	public void connected()
	{
		_localPlayer.searchCurrentChunk();
		_gui.setGUIMode (GUI.GUIMode.GAME);
	}
	
	public void chunkNeeded(Point pos)
	{
		System.out.println("need new chunk");
		Packet p = new Packet(MSGType.MSG_CL_REQUEST_CHUNK);
		p.set((int)pos.getX());
		p.set((int)pos.getY());
		_networkClient.send(p);
	}
	
	public static void main(String[] args)
	{
		try
		{
			(new Client()).run();
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void playerMoved(PlayerMovementEvent e)
	{
		Packet p = new Packet(MSGType.MSG_CL_PLAYER_POSITION);
		p.set(e.getLocation().getX());
		p.set(e.getLocation().getY());
		_networkClient.send(p);
	}
}

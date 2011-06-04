package destal.general;

import java.awt.Point;
import java.util.ArrayList;

import destal.entities.HumanPlayer;
import destal.entities.characters.Player;
import destal.general.event.events.PacketReceivedClientEvent;
import destal.general.event.events.PlayerMovementEvent;
import destal.general.event.listener.PacketRecievedClientListener;
import destal.general.event.listener.PlayerMovementListener;
import destal.general.net.MSGType;
import destal.general.net.Packet;
import destal.general.net.client.NetworkClient;
import destal.general.ui.GUI;
import destal.general.world.Chunk;
import destal.general.world.WorldPoint;

public class Client implements PlayerMovementListener, PacketRecievedClientListener
{
	// TODO: divide into Client and GameClient?
	private Chunk[] _chunkBuffer;
	private ArrayList<Player> _characters;
	private HumanPlayer _localPlayer;
	private GUI _gui;
	private int _id;
	
	private NetworkClient _networkClient;
	
	public Client()
	{
		DataContainer.create();
		_localPlayer = new HumanPlayer(this);
		_localPlayer.addPlayerMovementListener(this);
		_gui = new GUI(600, 200, this);
		_networkClient = new NetworkClient();
		_networkClient.addPacketReceivedClientListener(this);
		_characters = new ArrayList<Player>();
		_characters.add(_localPlayer);
		// Just to test features
		// TODO: handling of new clients
	}

	public Chunk[] getChunkBuffer()
	{
		return _chunkBuffer;
	}

	public HumanPlayer getLocalCharacter()
	{
		return _localPlayer;
	}
	
	public ArrayList<Player> getCharacters()
	{
		return _characters;
	}
	
	public void connect(String address, String username)
	{
		_networkClient.connect(address, username);
	}
	
	public void run()
	{
		
	}
	
	public void chunkNeeded(Point pos)
	{
		System.out.println("need new chunk");
		Packet p = new Packet(MSGType.MSG_CL_REQUEST_CHUNK);
		p.set((int)pos.getX());
		p.set((int)pos.getY());
		_networkClient.send(p);
	}

	@Override
	public void playerMoved(PlayerMovementEvent e)
	{
		Packet p = new Packet(MSGType.MSG_CL_PLAYER_INPUT);
		p.set(e.getLocation().getX());
		p.set(e.getLocation().getY());
		_networkClient.send(p);
	}

	@Override
	public void serverConnected(PacketReceivedClientEvent e)
	{
		System.out.println("sucessfully connected to server");
		System.out.println("[Server] MOTD: " + e.getMOTD());
		_id = e.getClientID();
		_localPlayer.setID(_id);
	}

	@Override
	public void serverDisconnected(PacketReceivedClientEvent e)
	{
		System.out.println("disconnected from server");
		_gui.setGUIMode(GUI.GUIMode.MENU);
	}

	@Override
	public void serverResponseEnter(PacketReceivedClientEvent e)
	{
		System.out.println("received start info from server");
		_chunkBuffer = e.getChunkBuffer();
		for(Chunk c : _chunkBuffer)
		{
			c.initImages();
		}
		_localPlayer.setLocation(e.getPoint());
		_localPlayer.searchCurrentChunk();
		_gui.setGUIMode(GUI.GUIMode.GAME);
	}

	@Override
	public void serverResponseChunk(PacketReceivedClientEvent e)
	{
		System.out.println("received chunk from server");
		Chunk c = e.getChunk();
		c.initImages();
		for (int i = 0; i < _chunkBuffer.length; i++)
		{
			if(_chunkBuffer[i] == null)
			{
				_chunkBuffer[i] = c;
				break;
			}
		}
		_gui.repaint();
	}
	
	@Override
	public void serverResponsePlayerPositions(PacketReceivedClientEvent e)
	{
		int id = e.getClientID();
		WorldPoint loc = e.getPoint();
		
		if (id != _id)
		{
			for (Player p : _characters)
			{
				if (p.getID() == id)
				{
					p.setLocation(loc);
				}
			}
			System.out.println("Player " + e.getClientID() + " changed location to: " + e.getPoint());
		}
		_gui.repaint();
	}

	@Override
	public void serverNewClientConnected(PacketReceivedClientEvent e)
	{
		// TODO: remove them
		Player p = new Player();
		p.setID(e.getClientID());
		p.setLocation(e.getPoint());
		_characters.add(p);
		_gui.repaint();
		System.out.println("Added new character with id " + e.getClientID());
	}
	
	public static void main(String[] args)
	{
		(new Client()).run();
	}
}
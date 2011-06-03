package destal.general;

import java.awt.Point;
import java.util.ArrayList;

import destal.entities.HumanPlayer;
import destal.general.event.events.PacketReceivedClientEvent;
import destal.general.event.events.PlayerMovementEvent;
import destal.general.event.listener.PacketRecievedClientListener;
import destal.general.event.listener.PlayerMovementListener;
import destal.general.net.MSGType;
import destal.general.net.Packet;
import destal.general.net.client.NetworkClient;
import destal.general.ui.GUI;
import destal.general.world.Chunk;

public class Client implements PlayerMovementListener, PacketRecievedClientListener
{
	// TODO: divide into Client and GameClient?
	private Chunk[] _chunkBuffer;
	private ArrayList<Character> _characters;
	private HumanPlayer _localPlayer;
	private GUI _gui;
	private int _id;
	
	public int getID()
	{
		return _id;
	}
	
	public void setID(int id)
	{
		_id = id;
	}
	
	private NetworkClient _networkClient;
	
	public Client()
	{
		DataContainer.create();
		_localPlayer = new HumanPlayer(this);
		_localPlayer.addPlayerMovementListener(this);
		_gui = new GUI(600, 200, this);
		_networkClient = new NetworkClient();
		_networkClient.addPacketReceivedClientListener(this);
	}

	public Chunk[] getChunkBuffer()
	{
		return _chunkBuffer;
	}

	public HumanPlayer getLocalCharacter()
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
		Packet p = new Packet(MSGType.MSG_CL_PLAYER_POSITION);
		p.set(e.getLocation().getX());
		p.set(e.getLocation().getY());
		_networkClient.send(p);
	}

	@Override
	public void serverConnected(PacketReceivedClientEvent e)
	{
		System.out.println("sucessfully connected to server");
		System.out.println("[Server] MOTD: " + e.getMOTD());
		this.setID(e.getClientID());
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
	
	public static void main(String[] args)
	{
		(new Client()).run();
	}
}

/*******************************************************************************
 * destal open world, an open source java multiplayer game
 * Copyright (C) 2011 Alexander Belke, Dennis Sternberg, Steffen Schneider
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/
package destal.client;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Vector;

import destal.client.net.NetworkClient;
import destal.event.events.net.client.PacketReceivedClientEvent;
import destal.event.listener.PacketReceivedClientListener;
import destal.event.listener.PlayerActionListener;
import destal.event.listener.PlayerMovementListener;
import destal.shared.DataContainer;
import destal.shared.entity.character.HumanPlayer;
import destal.shared.entity.character.Player;
import destal.shared.event.player.PlayerActionEvent;
import destal.shared.event.player.PlayerMovementEvent;
import destal.shared.net.MSGType;
import destal.shared.net.Packet;
import destal.shared.world.Chunk;
import destal.shared.world.WorldPoint;

/**
 * The main class for the destal client application
 * @author Alex Belke, Dennis Sternberg, Steffen Schneider
 */
public class Client implements PlayerMovementListener, PacketReceivedClientListener, PlayerActionListener
{
	// TODO: divide into Client and GameClient?
	private Chunk[] _chunkBuffer;
	private ArrayList<Player> _characters;
	private HumanPlayer _localPlayer;
	private GUI _gui;
	private int _id;
	
	private NetworkClient _networkClient;
	
	/**
	 * Creates a new Client
	 */
	public Client()
	{
		DataContainer.create();
		_localPlayer = new HumanPlayer(this);
		_localPlayer.addPlayerMovementListener(this);
		_localPlayer.addPlayerActionListener(this);
		_gui = new GUI(600, 600, this);
		_networkClient = new NetworkClient();
		_networkClient.addPacketReceivedClientListener(this);
		_characters = new ArrayList<Player>();
		_characters.add(_localPlayer);
	}
	/**
	 * Returns the chunk buffer used by this client as an array 
	 * @return The chunk buffer used by this client
	 */
	public Chunk[] getChunkBuffer()
	{
		return _chunkBuffer;
	}
	
	/**
	 * Returns the local character associated with this client 
	 * @return The local character associated with this client
	 */
	public HumanPlayer getLocalCharacter()
	{
		return _localPlayer;
	}
	
	/**
	 * Returns the characters in the game as an ArrayList 
	 * @return The characters in the game as an ArrayList 
	 */
	public ArrayList<Player> getCharacters()
	{
		return _characters;
	}
	
	/**
	 * Tries to establish a connection to the specified server address
	 * @param address The address of the server
	 * @param username The client's user name
	 */
	public void connect(String address, String username)
	{
		_networkClient.connect(address, username);
	}
	
	/**
	 * TODO Alex: documentation
	 * @param pos
	 * @param prevChunkPos
	 */
	public void leftChunk(Point pos, Point prevChunkPos)
	{
		// TODO comment this!
		Chunk newCurrent = null;
		for (int i = 0; i < _chunkBuffer.length; i++)
		{
			if(_chunkBuffer[i] != null)
			{
				if(pos.equals(_chunkBuffer[i].getLocation()))
				{
					newCurrent = _chunkBuffer[i];
				}
				if(Math.abs(_chunkBuffer[i].getLocation().x - pos.x) > 1 ||
				   Math.abs(_chunkBuffer[i].getLocation().y - pos.y) > 1)
				{
					_chunkBuffer[i] = null;
				}
			}
		}
		
		int xDiff = prevChunkPos.x - pos.x;
		int yDiff = prevChunkPos.y - pos.y;
		
		Vector<Point> needed = new Vector<Point>();
		if(xDiff != 0)
		{
			for(int i = 0; i < 3; i++)
				needed.add(new Point(pos.x-xDiff, pos.y+i-1));
		}
		if(yDiff != 0)
		{
			for(int i = 0; i < 3; i++)
			{
				Point p = new Point(pos.x+i-1, pos.y-yDiff);
				if(!needed.equals(p))
					needed.add(p);
			}
		}
		
		System.out.println("needed chunks: " + needed);
		Packet p = new Packet(MSGType.MSG_CL_REQUEST_CHUNK);
		p.set(needed.toArray(new Point[0]));
		_networkClient.send(p);
		
		if(newCurrent != null)
		{
			_localPlayer.setCurrentChunk(newCurrent);
		}
	}

	// Eventhandler section
	
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
		Player[] players = e.getClientList();
		for (Player pl : players)
		{
			_characters.add(pl);
		}
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
		_localPlayer.setLocation(e.getPoint());
		_chunkBuffer = e.getChunkBuffer();
		for(Chunk c : _chunkBuffer)
		{
			if(_localPlayer.getLocation().getChunkLocation().equals(c.getLocation()))
			{
				_localPlayer.setCurrentChunk(c);
			}
		}
		_gui.setGUIMode(GUI.GUIMode.GAME);
	}

	@Override
	public void serverResponseChunk(PacketReceivedClientEvent e)
	{
		System.out.println("received chunk from server");
		Chunk c = e.getChunk();
		for (int i = 0; i < _chunkBuffer.length; i++)
		{
			if(_chunkBuffer[i] == null || _chunkBuffer[i].getLocation().equals(c.getLocation()))
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
		new Client();
	}
	@Override
	public void playerAction(PlayerActionEvent e) {}
	@Override
	public void playerBuildHouse(PlayerActionEvent e)
	{
		Packet p = new Packet(MSGType.MSG_CL_BUILD_HOUSE);
		p.set(e.getLocation().getX());
		p.set(e.getLocation().getY());
		p.set(e.getBuildingType());
		_networkClient.send(p);
	}
}

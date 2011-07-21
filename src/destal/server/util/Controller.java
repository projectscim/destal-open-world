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
package destal.server.util;

import java.awt.Point;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import destal.server.event.PacketReceivedServerEvent;
import destal.server.event.listener.PacketReceivedServerListener;
import destal.server.net.ClientConnection;
import destal.server.net.NetworkServer;
import destal.shared.entity.block.Block;
import destal.shared.entity.building.Building;
import destal.shared.entity.character.Player;
import destal.shared.entity.data.Values;
import destal.shared.net.MSGType;
import destal.shared.net.Packet;
import destal.shared.world.Chunk;
import destal.shared.world.World;
import destal.shared.world.WorldPoint;
/**
 * Handles world and players
 * @author Alex Belke, Dennis Sternberg, Steffen Schneider
 */
public class Controller implements PacketReceivedServerListener
{
	private World _world;
	private NetworkServer _netServer;
	private ArrayList<Player> _characters;
	
	private final File saveFile = new File("data/sv/players.ini");
	
	public Controller(NetworkServer netServer)
	{
		_netServer = netServer;
		_characters = new ArrayList<Player>();
		if (!saveFile.exists())
		{
			try
			{
				saveFile.createNewFile();
			} 
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
	}
	/**
	 * Loads the world with the specified name
	 * @param name The world's name
	 */
	public void loadWorld(String name)
	{
		_world = new World(name);
	}
	/**
	 * Adds a building at the specified location
	 * @param p The house's location
	 * @param buildingType The building type
	 */
	private void addBuilding(WorldPoint p, int buildingType)
	{
		Building h = null;
		try
		{
			h = Building.create(buildingType);
			h.setLocation(p);
			System.out.println(buildingType);
			_world.addBuilding(h);
		}
		catch (IllegalArgumentException e)
		{
			System.out.println("Invalid building type!");
		}

	}
	private void savePlayers()
	{
		try
		{
			FileOutputStream fout = new FileOutputStream(saveFile);
			Properties p = new Properties();
			for (Player pl : _characters)
			{
				p.setProperty(_netServer.getClientName(pl.getID())+"_x", ""+pl.getLocation().getX());
				p.setProperty(_netServer.getClientName(pl.getID())+"_y", ""+pl.getLocation().getY());
			}
			p.store(fout, "Do not change the content of this file!");
			fout.flush();
			fout.close();
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	private void savePlayer(String name, WorldPoint location)
	{
		try
		{
			FileOutputStream fout = new FileOutputStream(saveFile);
			Properties p = new Properties();
			p.setProperty(name+"_x", ""+location.getX());
			p.setProperty(name+"_y", ""+location.getY());
			
			p.store(fout, "Do not change the content of this file!");
			fout.flush();
			fout.close();
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	
	private Player loadPlayerData(String playerName) throws IOException
	{
		try
		{
			// TODO add exceptions if player name is not valid
			FileInputStream fin = new FileInputStream(saveFile);
			Properties p = new Properties();
			p.load(fin);
			double x = Double.parseDouble(p.getProperty(playerName+"_x"));
			double y = Double.parseDouble(p.getProperty(playerName+"_y"));
			Player pl = new Player();
			pl.setLocation(x, y);
			fin.close();
			return pl;
		}
		catch (FileNotFoundException e)
		{
			throw e;
		}
		catch (IOException e) 
		{
			throw e;
		}
		catch (NullPointerException e)
		{
			throw e;
		}
	}
	/**
	 * Updates the specified chunk
	 * @param chunkLocation A point which describes the position of the chunk in the world
	 * @throws IllegalArgumentException
	 */
	private void updateChunk(Point chunkLocation) throws IllegalArgumentException
	{
		if (chunkLocation.x > World.LEVEL_SIZE || chunkLocation.y > World.LEVEL_SIZE)
			throw new IllegalArgumentException("Invalid chunk size");
		Packet p = new Packet(MSGType.MSG_SV_RESPONSE_CHUNK);
		try 
		{
			p.set(_world.getChunk(chunkLocation.x, chunkLocation.y, 0));
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		
		for(Player chr : _characters)
		{
			Point pos = chr.getLocation().getChunkLocation();
			int xDiff = Math.abs(pos.x - chunkLocation.x);
			int yDiff = Math.abs(pos.x - chunkLocation.x);
			if(xDiff <= 1 && yDiff <= 1)
			{
				_netServer.send(chr.getID(), p);
			}
		}
	}
	
	@Override
	public void clientRequestEnter(PacketReceivedServerEvent e)
	{
		System.out.println("sending start info to client: '" + e.getClient() + "'");
		WorldPoint pos = null;
		try
		{
			pos = loadPlayerData(e.getClient().getName()).getLocation();
		}
		catch (Exception e1)
		{
			e1.printStackTrace();
			pos = new WorldPoint(World.LEVEL_SIZE/2,
								 World.LEVEL_SIZE/2,
								 World.CHUNK_SIZE/2,
								 World.CHUNK_SIZE/2);
		}

		Point chunkPos = pos.getChunkLocation();
		
		for(Player chr : _characters)
		{
			if(chr.getID() == e.getClient().getID())
			{
				chr.setLocation(pos);
				break;
			}
		}
		
		Packet p = new Packet(MSGType.MSG_SV_NEW_CLIENT_CONNECTED);
		p.set(e.getClient().getID());
		p.set(pos.getX());
		p.set(pos.getY());
		_netServer.broadcastPacket(p);
		
		Chunk[] buffer = new Chunk[9];
		int i = 0;
		for (int x = chunkPos.x-1; x <= chunkPos.x+1; x++)
		{
			for (int y = chunkPos.y-1; y <= chunkPos.y+1; y++)
			{
				buffer[i++] = _world.getLevels()[0].getChunk(x, y);
			}
		}
		
		p = new Packet(MSGType.MSG_SV_RESPONSE_ENTER);
		p.set(pos.getX());
		p.set(pos.getY());
		p.set(buffer);
		e.getClient().send(p);
	}

	@Override
	public void clientRequestChunk(PacketReceivedServerEvent e)
	{		
		// TODO Simply use update method?
		System.out.println("sending chunk to client: '" + e.getClient() + "'");
		
		Point[] points = e.getPoints();
		
		for(Point pos : points)
		{
			Packet p = new Packet(MSGType.MSG_SV_RESPONSE_CHUNK);
			p.set(_world.getLevels()[0].getChunk(pos.x, pos.y));
			e.getClient().send(p);
		}
	}
	
	
	public void broadcastPlayerPositions(int clientID, WorldPoint newLocation)
	{
		Packet p = new Packet(MSGType.MSG_SV_RESPONSE_PLAYER_POSITIONS);
		p.set(clientID);
		p.set(newLocation.getX());
		p.set(newLocation.getY());
		_netServer.broadcastPacket(p);
	}
	
	@Override
	public void clientConnected(PacketReceivedServerEvent e)
	{
		Packet p = new Packet(MSGType.MSG_SV_INIT);
		p.set(true);
		p.set("Welcome :)");
		p.set(e.getClient().getID());
		p.set(_characters.toArray(new Player[0]));
		e.getClient().send(p);
		
		Player pl = new Player();
		pl.setID(e.getClient().getID());
		_characters.add(pl);
	}
	
	@Override
	public void clientDisconnected(PacketReceivedServerEvent e)
	{
		for(Player chr : _characters)
		{
			if(chr.getID() == e.getClient().getID())
			{
				savePlayer(((ClientConnection)e.getSource()).getName(), chr.getLocation());
				_characters.remove(chr);
				break;
			}
		}
	}

	@Override
	public void clientBuildHouse(PacketReceivedServerEvent e)
	{
		addBuilding(e.getPoint(), e.getBuildingType());
		
		Point chunkLocation = e.getPoint().getChunkLocation();
		updateChunk(chunkLocation);
	}

	@Override
	public void clientMineBlock(PacketReceivedServerEvent e)
	{
		WorldPoint p = e.getPoint();
		Chunk c = null;
		try
		{
			c = _world.getChunk(p.getChunkLocation().x, p.getChunkLocation().y, 0);
			System.out.println(c.getLocation());
		}
		catch (IOException e1)
		{
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		// TODO false block?
		Block b = c.getBlock((int)p.getX(), (int)p.getY());
		if (b == null)
		{
			System.out.println("Invalid block location!");
		}
		else if (b.getDataValue() != Values.BLOCK_DIRT)
		{
			// TODO change content of player inventory RIGHT HERE
			System.out.println(b.getDataValue());
			// change block to dirt
			// TODO optimize, btw it does not work yet :(
			Point bp = b.getLocation().getLocationInChunk();
			c.getBlocks()[bp.x][bp.y] = Block.create(Values.BLOCK_DIRT);
			try {
				c.saveChunk(_world.getLevels()[0].getChunkFile(c.getLocation().x, c.getLocation().y));
			} catch (NullPointerException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			Point chunkLocation = c.getLocation();
			updateChunk(chunkLocation);
		}
	}
	@Override
	public void clientPlayerRequestMove(PacketReceivedServerEvent e)
	{
		for (Player p : _characters)
		{
			if (p.getID() == e.getClientID())
			{
				// because of this, it is impossible to cheat while moving just by changing the
				// client's source code
				double dx = e.getSndPoint().getX();
				double dy = e.getSndPoint().getY();
				double dist = Math.sqrt(dx*dx+dy*dy);
				
				if (dist != 0)
				{
					double xMove = dx/dist;
					double yMove = dy/dist;
					
					WorldPoint newLoc = new WorldPoint(p.getLocation().getX()+xMove*0.1,
													   p.getLocation().getY()+yMove*0.1);
					p.setLocation(newLoc);
					broadcastPlayerPositions(p.getID(), p.getLocation());
				}
				
				return;
			}
		}
	}
}

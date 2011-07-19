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
import java.io.IOException;
import java.util.ArrayList;

import destal.server.event.PacketReceivedServerEvent;
import destal.server.event.listener.PacketReceivedServerListener;
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

public class Controller implements PacketReceivedServerListener
{
	private World _world;
	// TODO: remove
	private NetworkServer _netServer;
	private ArrayList<Player> _characters;
	
	public Controller(NetworkServer netServer)
	{
		_netServer = netServer;
		_characters = new ArrayList<Player>();
	}
	
	public void loadWorld(String name)
	{
		_world = new World(name);
	}
	
	private void buildHouse(WorldPoint p, int buildingType)
	{
		Building h = null;
		try
		{
			h = Building.create(buildingType);
			h.setLocation(p);
			System.out.println(buildingType);
			_world.buildHouse(h);
		}
		catch (IllegalArgumentException e)
		{
			System.out.println("Invalid building type!");
		}

	}
	
	private void updateChunk(Chunk c)
	{
		Packet p = new Packet(MSGType.MSG_SV_RESPONSE_CHUNK);
		p.set(c);
		
		for(Player chr : _characters)
		{
			Point pos = chr.getLocation().getChunkLocation();
			int xDiff = Math.abs(pos.x - c.getLocation().x);
			int yDiff = Math.abs(pos.x - c.getLocation().x);
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
		// TODO: change default position
		WorldPoint pos = new WorldPoint(World.LEVEL_SIZE/2,
										World.LEVEL_SIZE/2,
										World.CHUNK_SIZE/2,
										World.CHUNK_SIZE/2);
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
		_netServer.send(-1, p);
		
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
		System.out.println("sending chunk to client: '" + e.getClient() + "'");
		
		Point[] points = e.getPoints();
		
		for(Point pos : points)
		{
			Packet p = new Packet(MSGType.MSG_SV_RESPONSE_CHUNK);
			p.set(_world.getLevels()[0].getChunk(pos.x, pos.y));
			e.getClient().send(p);
		}
	}
	
	@Override
	public void clientPlayerInput(PacketReceivedServerEvent e)
	{
		for(Player chr : _characters)
		{
			if(chr.getID() == e.getClient().getID())
			{
				chr.setLocation(e.getPoint().getX(), e.getPoint().getY());
				break;
			}
		}
		
		Packet p = new Packet(MSGType.MSG_SV_RESPONSE_PLAYER_POSITIONS);
		p.set(e.getClient().getID());
		p.set(e.getPoint().getX());
		p.set(e.getPoint().getY());
		_netServer.send(-1, p);
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
				_characters.remove(chr);
				break;
			}
		}
	}

	@Override
	public void clientBuildHouse(PacketReceivedServerEvent e)
	{
		buildHouse(e.getPoint(), e.getBuildingType());
		
		Point chunkLocation = e.getPoint().getChunkLocation();
		updateChunk(_world.getLevels()[0].getChunk(chunkLocation.x, chunkLocation.y));
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
			updateChunk(_world.getLevels()[0].getChunk(chunkLocation.x, chunkLocation.y));
		}
	}
}

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
package destal.util;

import java.awt.Point;
import java.util.ArrayList;

import destal.entity.building.Building;
import destal.entity.character.Player;
import destal.event.events.net.server.PacketReceivedServerEvent;
import destal.event.listener.PacketReceivedServerListener;
import destal.general.net.MSGType;
import destal.general.net.Packet;
import destal.general.net.server.NetworkServer;
import destal.general.world.Chunk;
import destal.general.world.World;
import destal.general.world.WorldPoint;

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
		Building h = Building.create(buildingType);
		h.setLocation(p);
		System.out.println(buildingType);
		_world.buildHouse(h);
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
}

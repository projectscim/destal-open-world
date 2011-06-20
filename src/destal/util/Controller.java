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

import destal.entity.building.House;
import destal.entity.character.Player;
import destal.event.events.net.server.PacketReceivedServerEvent;
import destal.event.listener.PacketReceivedServerListener;
import destal.general.net.MSGType;
import destal.general.net.Packet;
import destal.general.world.Chunk;
import destal.general.world.World;
import destal.general.world.WorldPoint;

public class Controller implements PacketReceivedServerListener
{
	private World _world;
	// TODO: remove?
	private ArrayList<Player> _characters;
	
	public Controller()
	{
		// TODO: remove?
		_characters = new ArrayList<Player>();
	}
	
	public void loadWorld(String name)
	{
		_world = new World(name);
	}
	
	@Override
	public void clientRequestEnter(PacketReceivedServerEvent e)
	{
		System.out.println("sending start info to client: '" + e.getClient() + "'");
		// TODO: change default position
		WorldPoint pos = new WorldPoint(40, 40);
		Point chunkPos = pos.getChunkLocation();
		
		Chunk[] buffer = new Chunk[9];
		int i = 0;
		for (int x = chunkPos.x-1; x <= chunkPos.x+1; x++)
		{
			for (int y = chunkPos.y-1; y <= chunkPos.y+1; y++)
			{
				buffer[i++] = _world.getLevels()[0].getChunk(x, y);
			}
		}
		
		Packet p = new Packet(MSGType.MSG_SV_RESPONSE_ENTER);
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
	public void clientPlayerInput(PacketReceivedServerEvent e) { }
	@Override
	public void clientConnected(PacketReceivedServerEvent e) { }
	@Override
	public void clientDisconnected(PacketReceivedServerEvent e) { }

	@Override
	public void clientBuildHouse(PacketReceivedServerEvent e)
	{
		House h = new House(e.getPoint());
		_world.buildHouse(h);
	}
}

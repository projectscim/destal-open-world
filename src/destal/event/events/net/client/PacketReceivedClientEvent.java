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
package destal.event.events.net.client;

import java.util.EventObject;

import destal.client.net.NetworkClient;
import destal.general.world.Chunk;
import destal.general.world.WorldPoint;
import destal.shared.entity.character.Player;

public class PacketReceivedClientEvent extends EventObject
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -2780775745052260188L;
	private Chunk _chunk[];
	private WorldPoint _point;
	private String _MOTD;
	private int _clientID;
	private Player[] _clientList;
	
	public PacketReceivedClientEvent(NetworkClient source)
	{
		super(source);
		_chunk = new Chunk[9];
		_point = new WorldPoint(0,0);
		_MOTD = "";
	}
	
	public Player[] getClientList()
	{
		return _clientList;
	}
	
	public void setClientList(Player[] players)
	{
		_clientList = players;
	}	
	
	public NetworkClient getNetworkClient()
	{
		return (NetworkClient)getSource();
	}
	
	public void setChunk(Chunk chunk)
	{
		_chunk[0] = chunk;
	}
	
	public Chunk getChunk()
	{
		return _chunk[0];
	}
	
	public void setChunkBuffer(Chunk[] chunk)
	{
		_chunk = chunk;
	}
	
	public Chunk[] getChunkBuffer()
	{
		return _chunk;
	}
	
	public void setPoint(WorldPoint point)
	{
		_point = point;
	}
	
	public void setPoint(double x, double y)
	{
		_point.setLocation(x, y);
	}
	
	public void setPoint(int x, int y)
	{
		_point.setLocation(x, y);
	}
	
	public WorldPoint getPoint()
	{
		return _point;
	}
	
	public void setMOTD(String MOTD)
	{
		_MOTD = MOTD;
	}
	
	public String getMOTD()
	{
		return _MOTD;
	}
	
	public void setClientID(int id)
	{
		_clientID = id;
	}
	
	public int getClientID()
	{
		return _clientID;
	}
}

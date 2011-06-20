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
package destal.general.event.events;

import java.awt.Point;
import java.util.EventObject;

import destal.general.net.server.ClientConnection;
import destal.general.world.WorldPoint;

public class PacketReceivedServerEvent extends EventObject
{
	private WorldPoint _point;
	private Point[] _points;
	
	public PacketReceivedServerEvent(ClientConnection client)
	{
		super(client);
		_point = new WorldPoint(0,0);
	}
	
	public ClientConnection getClient()
	{
		return (ClientConnection)getSource();
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
	
	public void setPoints(Point[] points)
	{
		_points = points;
	}
	
	public Point[] getPoints()
	{
		return _points;
	}
}
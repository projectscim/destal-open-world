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
package destal.shared.entity;


import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.io.Serializable;

import destal.shared.util.DataContainer;
import destal.shared.world.World;
import destal.shared.world.WorldPoint;

public abstract class Entity implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 457110500357850757L;
	private WorldPoint _location;

	public Entity()
	{
		_location = new WorldPoint();
	}
	
	protected Image getImage()
	{
		return DataContainer.getTexture(this.getDataValue());
	}

	public WorldPoint getLocation()
	{
		return _location;
	}

	public void setLocation(WorldPoint value)
	{
		_location.setLocation(value);
	}
	
	public void setLocation(double x, double y)
	{
		_location.setLocation(x, y);
	}

	public void paint(Graphics g)
	{
		g.drawImage(getImage(), (int)_location.getX()-16, (int)_location.getY()-16, null);
	}
	/**
	 * 
	 * @param g
	 * @param p The point in the upper left hand corner of the Graphics object on which to paint on
	 */
	public void paint(Graphics g, WorldPoint p)
	{
		Point loc = _location.getLocationOnPanel(p.getX(), p.getY());
		g.drawImage(getImage(), (int)loc.getX()-World.BLOCK_PAINTSIZE/2, (int)loc.getY()-World.BLOCK_PAINTSIZE/2, null);
	}
	
	public abstract int getDataValue();
}

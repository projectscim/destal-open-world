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
package destal.shared.event;


import java.util.EventObject;

import destal.shared.world.WorldPoint;

/**
 * An event which indicates that the player has moved
 *
 */
public class PlayerMovementEvent extends EventObject
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -5490445822316987431L;
	private WorldPoint _location;
	
	public PlayerMovementEvent(Object source)
	{
		super(source);
	}
	public PlayerMovementEvent(Object source, WorldPoint newLocation)
	{
		this(source);
		setLocation(newLocation);
	}
	/**
	 * Returns the new location of the player
	 * @return The new location of the player as a WorldPoint
	 */
	public WorldPoint getLocation()
	{
		return _location;
	}
	public void setLocation(WorldPoint location)
	{
		_location = location;
	}
}

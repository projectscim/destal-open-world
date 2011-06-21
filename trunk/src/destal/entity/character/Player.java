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
package destal.entity.character;

import destal.entity.Entity;
import destal.entity.data.Values;

public class Player extends Entity
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1411457638971740437L;
	private int _id;
	/**
	 * Sets the ID of the player
	 * @param id The ID
	 */
	public void setID(int id)
	{
		_id = id;
	}
	/**
	 * Returns the ID of the player
	 * @return The player ID
	 */
	public int getID()
	{
		return _id;
	}
	
	@Override
	public int getDataValue()
	{
		return Values.CHAR_PLAYER;
	}
}

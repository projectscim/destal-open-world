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
package destal.entity.building;

import destal.entity.Entity;
import destal.entity.data.Values;
import destal.general.world.WorldPoint;


public class House extends Entity
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 4193686143716527925L;

	public House(WorldPoint location)
	{
		super();
		this.setLocation(location);
	}
	
	@Override
	public int getDataValue()
	{
		return Values.HOUSE_HOUSE;
	}
}

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
package destal.shared.entity.building;

import destal.shared.entity.Entity;
import destal.shared.entity.data.Values;


public abstract class Building extends Entity
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 4193686143716527925L;
	
	public static Building create(int dataValue)
	{
		switch (dataValue)
		{
			case Values.HOUSE_HOUSE:
				return new House();
			case Values.HOUSE_BLACKSMITH:
				return new Blacksmith();
			case Values.HOUSE_CASTLE:
				return new Castle();
			case Values.HOUSE_HARBOUR:
			    return new Harbour();
			default:
				throw new IllegalArgumentException();
		}
	}
}

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
package destal.shared.entity.block;

import destal.shared.entity.data.Values;

public class Water extends Block
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7235116806365048279L;

	@Override
	public int getDataValue()
	{
		return Values.BLOCK_WATER;
	}

	@Override
	public double getBlockChangePossibility()
	{
		return 0.01;
	}

	@Override
	public Block getNeighbourBlock()
	{
		return Block.create(Values.BLOCK_SAND);
	}
}

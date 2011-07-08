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

import java.util.Random;

import destal.shared.entity.IWalkable;
import destal.shared.entity.data.Values;


public class Dirt extends Block implements IWalkable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -4964933620557301608L;

	@Override
	public int getDataValue()
	{
		return Values.BLOCK_DIRT;
	}

	@Override
	public double getBlockChangePossibility()
	{
		return 0.1;
	}

	@Override
	public Block getNeighbourBlock()
	{
		Random r = new Random();
		int x = r.nextInt(5);
		if (x <= 1)
		{
			return Block.create(Values.BLOCK_STONE);
		}
		else if (x <= 3)
		{
			return Block.create(Values.BLOCK_SAND);
		}
		else
		{
			return Block.create(Values.BLOCK_TREE);
		}
	}
}

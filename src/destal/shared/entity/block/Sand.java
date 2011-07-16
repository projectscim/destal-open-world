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

public class Sand extends Block implements IWalkable
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6874386300168786435L;

	@Override
	public int getDataValue()
	{
		return Values.BLOCK_SAND;
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
		int x = r.nextInt(2);
		if (x == 0)
		{
			return Block.create(Values.BLOCK_DIRT);
		}
		else
		{
			return Block.create(Values.BLOCK_WATER);
		}
	}
}

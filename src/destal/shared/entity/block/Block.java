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

import java.util.ArrayList;
import java.util.Random;

import destal.client.event.BlockClickedEvent;
import destal.client.event.listener.BlockListener;
import destal.shared.entity.Entity;
import destal.shared.entity.data.Values;


public abstract class Block extends Entity
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 3086831376838522472L;
	
	private ArrayList<BlockListener> _blockListener = new ArrayList<BlockListener>();
	
	public abstract double getBlockChangePossibility();
	public abstract Block getNeighbourBlock();
	

	public Block getNextBlock()
	{
		Random r = new Random();
		double x = r.nextDouble();
		if (x <= this.getBlockChangePossibility())
		{
			return getNeighbourBlock();
		}
		else
		{
			return Block.create(this.getDataValue());
		}
	}
	
	public void addBlockListener(BlockListener l)
	{
		_blockListener.add(l);
	}
	public void removeBlockListener(BlockListener l)
	{
		_blockListener.remove(l);
	}
	public void invokeBlockClicked(BlockClickedEvent e)
	{
		for (BlockListener l : _blockListener)
		{
			l.blockClicked(e);
		}
	}
	
	/**
	 * Generates a Block from the data value
	 * @param value The data value of the Block
	 * @return The Block
	 * @throws IllegalArgumentException If the data value is illegal or out of range
	 */
	public static Block create(int value) throws IllegalArgumentException
	{
		switch (value)
		{
			case Values.BLOCK_DIRT:
				return new Dirt();
			case Values.BLOCK_STONE:
				return new Stone();
			case Values.BLOCK_TREE:
				return new Tree();
			case Values.BLOCK_SAND:
				return new Sand();
			case Values.BLOCK_WATER:
				return new Water();
			case Values.BLOCK_WOOD:
				return new Wood();
			default:
				throw new IllegalArgumentException();
		}
	}
}

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
package destal.entity.item;

import destal.entity.Entity;
import destal.entity.data.Values;


public abstract class Item extends Entity
{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5474357130021157378L;
	
	private int _quantity;

	public void setQuantity(int quantity)
	{
		_quantity = quantity;
	}

	public int getQuantity()
	{
		return _quantity;
	}
	
	public void increaseQuantity(int quantity)
	{
		_quantity += quantity;
	}
	
	public void decreaseQuantity(int quantity)
	{
		_quantity -= quantity;
	}
	
	public static Item create(int value) throws IllegalArgumentException
	{
		switch (value)
		{
			case Values.ITEM_DIRT:
				return new DirtItem();
			case Values.ITEM_STONE:
				return new StoneItem();
			case Values.ITEM_SAND:
				return new SandItem();
			case Values.ITEM_WOOD:
				return new WoodItem();
			default:
				throw new IllegalArgumentException();
		}
	}
	
}

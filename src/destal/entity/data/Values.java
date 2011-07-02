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
package destal.entity.data;

public class Values
{
	// TODO: rework this...
	// Description:
	// Blocks:   	0x0?
	// Houses:   	0x1?
	// Characters:  0x2?
	//
	
	// Block section
	public final static int BLOCK_DIRT 	= 0x01;
	public final static int BLOCK_STONE = 0x02;
	public final static int BLOCK_TREE 	= 0x03;
	public final static int BLOCK_SAND 	= 0x04;
	public final static int BLOCK_WATER	= 0x05;
	public final static int BLOCK_WOOD 	= 0x06;
	
	// Item section
	public final static int ITEM_DIRT 	= 0x10;
	public final static int ITEM_STONE  = 0x11;
	public final static int ITEM_TREE 	= 0x12;
	public final static int ITEM_SAND 	= 0x13;
	public final static int ITEM_WATER	= 0x14;
	public final static int ITEM_WOOD 	= 0x15;
	
	// House section
	public static final int HOUSE_HOUSE = 0x20;
	public static final int HOUSE_SECONDHOUSE = 0x21;
	
	// Player section
	public final static int CHAR_PLAYER = 0x30;

}

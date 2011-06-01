package entities.blocks;

import entities.Values;
import entities.characters.Entity;


public abstract class Block extends Entity
{
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
			default:
				throw new IllegalArgumentException();
		}
	}
}

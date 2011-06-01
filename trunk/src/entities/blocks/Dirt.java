package entities.blocks;

import entities.IWalkable;
import entities.Values;


public class Dirt extends Block implements IWalkable
{
	@Override
	public int getDataValue()
	{
		return Values.BLOCK_DIRT;
	}
}

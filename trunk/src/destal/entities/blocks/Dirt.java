package destal.entities.blocks;

import destal.entities.IWalkable;
import destal.entities.Values;


public class Dirt extends Block implements IWalkable
{
	@Override
	public int getDataValue()
	{
		return Values.BLOCK_DIRT;
	}
}

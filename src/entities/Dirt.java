package entities;


public class Dirt extends Block implements IWalkable
{
	@Override
	public int getDataValue()
	{
		return Values.BLOCK_DIRT;
	}
}

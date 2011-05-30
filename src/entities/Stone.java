package entities;


public class Stone extends Block implements IWalkable
{
	@Override
	public int getDataValue()
	{
		return Values.BLOCK_STONE;
	}
}

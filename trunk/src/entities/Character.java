package entities;

public abstract class Character extends Entity
{
	@Override
	public int getDataValue()
	{
		return Values.ENTITY_CHARACTER;
	}
}

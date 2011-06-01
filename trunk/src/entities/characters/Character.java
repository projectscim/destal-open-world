package entities.characters;

import entities.Values;

public abstract class Character extends Entity
{
	@Override
	public int getDataValue()
	{
		return Values.ENTITY_CHARACTER;
	}
}

package destal.entities.characters;

import destal.entities.Values;

public abstract class Character extends Entity
{
	@Override
	public int getDataValue()
	{
		return Values.ENTITY_CHARACTER;
	}
}

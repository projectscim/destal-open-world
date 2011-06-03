package destal.entities.characters;

import destal.entities.Values;

public class Player extends Entity
{
	
	@Override
	public int getDataValue()
	{
		return Values.ENTITY_CHARACTER;
	}
}

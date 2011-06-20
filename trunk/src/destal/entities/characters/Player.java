package destal.entities.characters;

import destal.entities.Values;

public class Player extends Entity
{
	private int _id;
	
	public void setID(int id)
	{
		_id = id;
	}
	public int getID()
	{
		return _id;
	}
	
	@Override
	public int getDataValue()
	{
		return Values.CHAR_PLAYER;
	}
}

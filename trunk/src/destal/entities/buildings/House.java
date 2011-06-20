package destal.entities.buildings;

import destal.entities.Values;
import destal.entities.characters.Entity;
import destal.general.world.WorldPoint;


public class House extends Entity
{
	public House(WorldPoint location)
	{
		super();
		this.setLocation(location);
	}
	
	@Override
	public int getDataValue()
	{
		return Values.HOUSE_HOUSE;
	}
}

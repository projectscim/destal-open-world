package destal.entity.building;

import destal.entity.data.Values;
import destal.general.world.WorldPoint;

public class House extends Building
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 4193686143716527925L;
	
	@Override
	public int getDataValue()
	{
		return Values.HOUSE_HOUSE;
	}

}

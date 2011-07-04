package destal.shared.entity.building;

import destal.shared.entity.data.Values;

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

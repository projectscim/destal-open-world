package destal.shared.entity.building;

import destal.shared.entity.data.Values;

public class Blacksmith extends Building
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6520665411536847754L;

	@Override
	public int getDataValue()
	{
		return Values.HOUSE_BLACKSMITH;
	}
}

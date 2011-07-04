package destal.entity.building;

import destal.entity.data.Values;

public class Blacksmith extends Building
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6520665411536847754L;

	@Override
	public int getDataValue()
	{
		// TODO Auto-generated method stub
		return Values.HOUSE_BLACKSMITH;
	}
}

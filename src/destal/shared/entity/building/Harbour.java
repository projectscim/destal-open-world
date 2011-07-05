package destal.shared.entity.building;

import destal.shared.entity.data.Values;

public class Harbour extends Building
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -3933479291356787254L;

	@Override
	public int getDataValue()
	{
		return Values.HOUSE_HARBOUR;
	}
}

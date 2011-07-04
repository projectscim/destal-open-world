package destal.shared.entity.building;

import destal.shared.entity.data.Values;

public class Castle extends Building
{
	

	/**
	 * 
	 */
	private static final long serialVersionUID = -5473760717437095699L;
	
	@Override
	public int getDataValue()
	{
		return Values.HOUSE_CASTLE;
	}

}

package destal.entity.building;

import destal.entity.data.Values;
import destal.general.world.WorldPoint;

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

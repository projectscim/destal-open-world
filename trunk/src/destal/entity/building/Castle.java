package destal.entity.building;

import destal.entity.data.Values;
import destal.general.world.WorldPoint;

public class Castle extends Building
{
	

	/**
	 * 
	 */
	private static final long serialVersionUID = -5473760717437095699L;

	public Castle(WorldPoint location)
	{
		super(location);
	}
	
	@Override
	public int getDataValue()
	{
		// TODO Add data value
		return 0;
	}

}

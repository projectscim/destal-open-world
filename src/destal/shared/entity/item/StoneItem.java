package destal.shared.entity.item;

import destal.shared.entity.data.Values;

public class StoneItem extends Item
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -104353609521602225L;

	@Override
	public int getDataValue()
	{
		return Values.ITEM_STONE;
	}

}

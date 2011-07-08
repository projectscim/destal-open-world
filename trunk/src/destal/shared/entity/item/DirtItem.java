package destal.shared.entity.item;

import destal.shared.entity.data.Values;

public class DirtItem extends Item
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8501700644125876339L;

	@Override
	public int getDataValue()
	{
		return Values.ITEM_DIRT;
	}

}

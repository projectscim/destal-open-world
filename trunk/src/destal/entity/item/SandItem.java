package destal.entity.item;

import destal.entity.data.Values;

public class SandItem extends Item {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7299925593592550488L;

	@Override
	public int getDataValue()
	{
		return Values.ITEM_SAND;
	}

}

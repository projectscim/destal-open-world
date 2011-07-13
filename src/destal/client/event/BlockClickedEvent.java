package destal.client.event;

import java.util.EventObject;

import destal.shared.entity.block.Block;


public class BlockClickedEvent extends EventObject
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -3748942436913378728L;

	private Block _block;
	
	public Block getBlock()
	{
		return _block;
	}
	public void setBlock(Block block)
	{
		_block = block;
	}
	
	public BlockClickedEvent(Object source)
	{
		super(source);
	}
	
}

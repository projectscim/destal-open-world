package destal.entities.blocks;

import java.awt.Color;
import java.awt.Graphics;

import destal.entities.Values;


public class Tree extends Block
{
	@Override
	public void paint(Graphics g)
	{
		g.setColor(Color.YELLOW);
		super.paint(g);
	}

	@Override
	public int getDataValue()
	{
		return Values.BLOCK_TREE;
	}
}

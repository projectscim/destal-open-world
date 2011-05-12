package entities;

import java.awt.*;

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
		return Values.TREE;
	}

}

package entities;

import java.awt.Color;
import java.awt.Graphics;

public class Dirt extends Block
{
	@Override
	public void paint(Graphics g)
	{
		g.setColor(Color.GREEN);
		super.paint(g);
	}

	@Override
	public int getDataValue()
	{
		return Values.DIRT;
	}

}

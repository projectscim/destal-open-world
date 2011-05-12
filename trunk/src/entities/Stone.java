package entities;

import java.awt.*;

public class Stone extends Block
{
	@Override
	public void paint(Graphics g)
	{
		g.setColor(Color.GRAY);
		super.paint(g);
	}

	@Override
	public int getDataValue()
	{
		return Values.STONE;
	}

}

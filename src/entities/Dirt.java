package entities;

import java.awt.Color;
import java.awt.Graphics;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Dirt extends Block
{
	@Override
	public void paint(Graphics g)
	{
		try
		{
			this.setImage(ImageIO.read(new File("data/gfx/dirt.png")));
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		super.paint(g);
	}

	@Override
	public int getDataValue()
	{
		return Values.DIRT;
	}

}

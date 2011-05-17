package entities;

import java.awt.*;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Stone extends Block
{
	@Override
	public void paint(Graphics g)
	{
		try
		{
			this.setImage(ImageIO.read(new File("C:\\Stone.png")));
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
		return Values.STONE;
	}

}

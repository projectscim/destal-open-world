package entities;

import java.awt.*;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Stone extends Block
{
	public Stone()
	{
		super();
		try
		{
			this.setImage(ImageIO.read(new File("data/gfx/stone.png")));
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public int getDataValue()
	{
		return Values.STONE;
	}
}

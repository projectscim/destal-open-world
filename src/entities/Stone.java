package entities;

import general.World;

import java.io.*;
import java.util.*;
import java.awt.*;
import java.lang.*;

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

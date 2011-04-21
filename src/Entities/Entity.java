package Entities;

import java.io.*;
import java.util.*;
import java.awt.*;
import java.lang.*;

public abstract class Entity
{

	private Point _location;

	public Point getLocation()
	{
		return _location;
	}

	public void setLocation(Point value)
	{
		_location = value;
	}

	public abstract void paint(Graphics g);

}

package entities;

import java.io.*;
import java.util.*;
import java.awt.*;
import java.lang.*;

public abstract class Entity
{

	public Entity()
	{
		_location = new Point();
	}
	
	private Point _location;

	public Point getLocation()
	{
		return _location.getLocation();
	}

	public void setLocation(Point value)
	{
		_location.setLocation(value);
	}
	
	public void setLocation(double x, double y)
	{
		_location.setLocation(x, y);
	}

	public abstract void paint(Graphics g);

}

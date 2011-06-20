package destal.entities.characters;


import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.io.Serializable;

import destal.general.DataContainer;
import destal.general.world.WorldPoint;
// TODO: change package to destal.entities
public abstract class Entity implements Serializable
{
	private WorldPoint _location;

	public Entity()
	{
		_location = new WorldPoint();
	}
	
	protected Image getImage()
	{
		return DataContainer.getTexture(this.getDataValue());
	}

	public WorldPoint getLocation()
	{
		return _location;
	}

	public void setLocation(WorldPoint value)
	{
		_location.setLocation(value);
	}
	
	public void setLocation(double x, double y)
	{
		_location.setLocation(x, y);
	}

	public void paint(Graphics g)
	{
		g.drawImage(getImage(), (int)_location.getX()-16, (int)_location.getY()-16, null);
	}
	/**
	 * 
	 * @param g
	 * @param p The point in the upper left hand corner of the Graphics object on which to paint on
	 */
	public void paint(Graphics g, WorldPoint p)
	{
		Point loc = _location.getLocationOnPanel(p.getX(), p.getY());
		g.drawImage(getImage(), (int)loc.getX()-16, (int)loc.getY()-16, null);
	}
	
	public abstract int getDataValue();
}

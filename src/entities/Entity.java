package entities;

import general.DataContainer;
import general.WorldPoint;

import java.awt.*;
import java.io.Serializable;

public abstract class Entity implements Serializable
{
	private Image _image;
	private WorldPoint _location;

	public Entity()
	{
		_location = new WorldPoint();
		if(DataContainer.check())
		{
			this.initImage();
		}
	}
	
	public void initImage()
	{
		this.setImage(DataContainer.getTexture(this.getDataValue()));
	}
	
	protected Image getImage()
	{
		return _image;
	}
	
	protected void setImage(Image _image)
	{
		this._image = _image;
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
		g.drawImage(_image, (int)_location.getX()-16, (int)_location.getY()-16, null);
	}
	/**
	 * 
	 * @param g
	 * @param p The point in the upper left hand corner of the Graphics object on which to paint on
	 */
	public void paint(Graphics g, WorldPoint p)
	{
		Point loc = _location.getLocationOnPanel(p.getX(), p.getY());
		g.drawImage(_image, (int)loc.getX(), (int)loc.getY(), null);
	}
	
	public abstract int getDataValue();
}

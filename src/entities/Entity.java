package entities;

import general.DataContainer;

import java.awt.*;
import java.io.Serializable;

public abstract class Entity implements Serializable
{
	private Image _image;
	private Point _location;

	public Entity()
	{
		_location = new Point();
		if(DataContainer.check())
		{
			this.setImage(DataContainer.getTexture(this.getDataValue()));
		}
	}
	
	protected Image getImage()
	{
		return _image;
	}
	
	protected void setImage(Image _image)
	{
		this._image = _image;
	}

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

	public void paint(Graphics g)
	{
		g.drawImage(_image, (int)this.getLocation().getX() - 16, (int)this.getLocation().getY() - 16, null);
	}
	
	public abstract int getDataValue();
}

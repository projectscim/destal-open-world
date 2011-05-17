package entities;

import general.World;

import java.awt.Graphics;
import java.awt.Image;


public abstract class Block extends Entity
{
	private Image _image;
	
	protected Image getImage()
	{
		return _image;
	}
	protected void setImage(Image _image)
	{
		this._image = _image;
	}
	@Override
	public void paint(Graphics g)
	{
		g.drawImage(_image, (int)this.getLocation().getX() - 16, (int)this.getLocation().getY() - 16, null);
	}
	/**
	 * Generates a Block from the data value
	 * @param value The data value of the Block
	 * @return The Block
	 * @throws IllegalArgumentException If the data value is illegal or out of range
	 */
	public static Block create(int value) throws IllegalArgumentException
	{
		switch (value)
		{
			case Values.DIRT:
				return new Dirt();
			case Values.STONE:
				return new Stone();
			case Values.TREE:
				return new Tree();
			default:
				throw new IllegalArgumentException();
		}
	}
	
	public abstract int getDataValue();
}

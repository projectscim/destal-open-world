/*******************************************************************************
 * destal open world, an open source java multiplayer game
 * Copyright (C) 2011 Alexander Belke, Dennis Sternberg, Steffen Schneider
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/
package destal.shared;

import java.awt.Image;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import destal.shared.entity.data.Values;


public class DataContainer
{
	private ArrayList<Texture> _textures;
	private static DataContainer _self;
	
	private class Texture
	{
		private Image _image;
		private int _ID;
		private String _file;
		
		public Texture(int ID, String file)
		{
			_ID = ID;
			_file = file;
		}
		
		public void load() throws Exception
		{
			try
			{
				System.out.println(getClass().getResource(_file));
				_image = ImageIO.read(getClass().getResource(_file));

			}
			catch(Exception ex)
			{
				throw ex;
			}
		}
		
		public Image getImage()
		{
			return _image;
		}
		
		public int getID()
		{
			return _ID;
		}
		
		public String getFile()
		{
			return _file;
		}
	}
	
	public DataContainer()
	{
		_textures = new ArrayList<Texture>();
		
		_textures.add(new Texture(Values.BLOCK_DIRT, "/gfx/dirt.png"));
		_textures.add(new Texture(Values.BLOCK_STONE, "/gfx/stone.png"));
		_textures.add(new Texture(Values.BLOCK_TREE, "/gfx/tree.png"));
		_textures.add(new Texture(Values.BLOCK_SAND, "/gfx/sand.png"));
		_textures.add(new Texture(Values.BLOCK_WATER, "/gfx/water.png"));
		_textures.add(new Texture(Values.BLOCK_WOOD, "/gfx/wood.png"));
		_textures.add(new Texture(Values.HOUSE_HOUSE, "/gfx/house.gif"));
		_textures.add(new Texture(Values.CHAR_PLAYER, "/gfx/Player.gif"));
		_textures.add(new Texture(Values.HOUSE_BLACKSMITH, "/gfx/house1.gif"));
		_textures.add(new Texture(Values.HOUSE_CASTLE, "/gfx/castle.gif"));
		/*
		_textures.add(new Texture(Values.BLOCK_DIRT, "/dirt.png"));
		_textures.add(new Texture(Values.BLOCK_STONE, "/stone.png"));
		_textures.add(new Texture(Values.BLOCK_TREE, "/tree.png"));
		_textures.add(new Texture(Values.ENTITY_CHARACTER, "/player.gif"));
		*/
		for(int i = 0; i < _textures.size(); i++)
		{
			try
			{
				_textures.get(i).load();
				System.out.println("loaded file: '" + _textures.get(i).getFile() + "'");
			}
			catch (Exception e)
			{
				System.out.println("couldn't load file: '" + _textures.get(i).getFile() + "'");
			}
		}
	}
	
	public static void create()
	{
		_self = new DataContainer();
	}
	
	public static boolean check()
	{
		return _self != null;
	}
	
	public static Image getTexture(int tex) throws IllegalArgumentException
	{
		for(int i = 0; i < _self._textures.size(); i++)
		{
			if(_self._textures.get(i).getID() == tex)
				return _self._textures.get(i).getImage();
		}
		throw new IllegalArgumentException("Couldn't load file (false texture id?)");
	}
}

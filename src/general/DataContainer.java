package general;

import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import entities.Values;

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
		_textures.add(new Texture(Values.ENTITY_CHARACTER, "/gfx/Player.gif"));
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
	
	public static Image getTexture(int tex)
	{
		for(int i = 0; i < _self._textures.size(); i++)
		{
			if(_self._textures.get(i).getID() == tex)
				return _self._textures.get(i).getImage();
		}
		return null;
	}
}

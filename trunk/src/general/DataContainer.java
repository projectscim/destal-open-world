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
	private static DataContainer _data = new DataContainer();
	
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
		
		public void load() throws IOException
		{
			_image = ImageIO.read(new File(_file));
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
		
		_textures.add(new Texture(Values.BLOCK_DIRT, "data/gfx/dirt.png"));
		_textures.add(new Texture(Values.BLOCK_STONE, "data/gfx/stone.png"));
		_textures.add(new Texture(Values.BLOCK_TREE, "data/gfx/tree.png"));
		_textures.add(new Texture(Values.ENTITY_CHARACTER, "data/gfx/player.gif"));
		
		for(int i = 0; i < _textures.size(); i++)
		{
			try
			{
				_textures.get(i).load();
			}
			catch (IOException e)
			{
				System.out.println("Couldn't load file: '" + _textures.get(i).getFile() + "'");
			}
		}
	}
	
	public static Image getTexture(int tex)
	{
		for(int i = 0; i < _data._textures.size(); i++)
		{
			if(_data._textures.get(i).getID() == tex)
				return _data._textures.get(i).getImage();
		}
		return null;
	}
}

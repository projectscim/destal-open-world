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
package destal.general.ui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.JPanel;

import destal.general.world.Chunk;
import destal.general.world.World;
import destal.general.world.WorldPoint;
import destal.shared.entity.building.Building;
import destal.shared.entity.character.HumanPlayer;
import destal.shared.entity.character.Player;


public class ChunkPanel extends JPanel implements KeyListener
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 5076622625109760874L;
	private HumanPlayer _player;
	private GUI _gui;
	
	public ChunkPanel (int x, int y, int width, int height, GUI gui)
	{
		super();
		_gui = gui;
		_player = _gui.getClient().getLocalCharacter();
		this.setBounds(x, y, width, height);
		setVisible(true);
		setDoubleBuffered(true);
	}
	
	@Override
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		g.setColor(Color.BLACK);
		g.fillRect((int)g.getClipBounds().getX(), (int)g.getClipBounds().getY(), (int)g.getClipBounds().getWidth(), (int)g.getClipBounds().getHeight());
		// Add what's to draw:
		WorldPoint p = new WorldPoint(_player.getLocation().getX()-this.getWidth()/2/World.BLOCK_PAINTSIZE,
								      _player.getLocation().getY()-this.getHeight()/2/World.BLOCK_PAINTSIZE);
		Vector<Building> buildings = new Vector<Building>();
		for (Chunk c : _gui.getClient().getChunkBuffer())
		{
			if(c == null)
				continue;
			
			for (int x = 0; x < World.CHUNK_SIZE; x++)
			{
				for (int y = 0; y < World.CHUNK_SIZE; y++)
				{
					Point loc = c.getBlocks()[x][y].getLocation().getLocationOnPanel((int)p.getX(), (int)p.getY());
					if (loc.getX() >= 0 && loc.getX()-World.BLOCK_PAINTSIZE <= this.getWidth() &&
						loc.getY() >= 0 && loc.getY()-World.BLOCK_PAINTSIZE <= this.getHeight())
					{
						c.getBlocks()[x][y].paint(g, p);
					}
				}
			}
			// TODO optimize?
			for (Building h : c.getHouses())
			{
				buildings.add(h);
			}
		}
		for (Building h : buildings)
		{
			Point loc = h.getLocation().getLocationOnPanel((int)p.getX(), (int)p.getY());
			if (loc.getX() >= 0 && loc.getX()-World.BLOCK_PAINTSIZE <= this.getWidth() &&
				loc.getY() >= 0 && loc.getY()-World.BLOCK_PAINTSIZE <= this.getHeight())
			{
				h.paint(g, p);
			}
		}
		_player.paint(g, p);
		
		ArrayList<Player> players = _gui.getClient().getCharacters();
		{
			for (Player pl : players)
			{
				pl.paint(g, p);
			}
		}
	}

	@Override
	public void keyPressed(KeyEvent arg0)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent e)
	{
		
	}
}

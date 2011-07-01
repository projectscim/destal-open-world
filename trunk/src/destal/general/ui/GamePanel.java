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
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.JPanel;

import destal.entity.building.House;
import destal.entity.character.HumanPlayer;
import destal.entity.character.Player;
import destal.event.events.player.PlayerMovementEvent;
import destal.event.listener.PlayerMovementListener;
import destal.general.gp.BuildingMenu;
import destal.general.gp.Inventory;
import destal.general.world.Chunk;
import destal.general.world.World;
import destal.general.world.WorldPoint;

public class GamePanel extends JPanel implements MouseMotionListener, PlayerMovementListener, MouseListener, ComponentListener
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2370270205864065025L;
	private GUI _gui;
	private MouseEvent _lastMouseEvent;
	private HumanPlayer _player;
	private Inventory _inventory;
	private BuildingMenu _buildingMenu;
	
	public static final int INVENTORY_HEIGHT = World.BLOCK_PAINTSIZE*2;
	public static final int BUILDING_MENU_HEIGHT = World.BLOCK_PAINTSIZE*4;
	
	public GamePanel (int width, int height, GUI gui)
	{
		super();
		this.setBounds(0, 0, width, height);
		_gui = gui;
		_player = _gui.getClient().getLocalCharacter();
		_inventory = new Inventory(this.getWidth(), INVENTORY_HEIGHT, _player);
		_buildingMenu = new BuildingMenu(0,
										 this.getHeight()-BUILDING_MENU_HEIGHT,
										 this.getWidth(), BUILDING_MENU_HEIGHT,
										 _player);
		this.add(_buildingMenu);
		this.add(_inventory);
		this.addMouseMotionListener(this);
		this.addMouseMotionListener(_player);
		this.addMouseListener(_player);
		this.addKeyListener(_player);
		this.addKeyListener(_buildingMenu);
		this.addComponentListener(this);
		System.out.println(this.getBounds().toString());
		_player.setContainer(this);
		_player.addPlayerMovementListener(this);
		setDoubleBuffered(true);
	}
	
	@Override
	public void paintComponent(Graphics g)
	{
		g.setColor(Color.BLACK);
		g.fillRect((int)g.getClipBounds().getX(), (int)g.getClipBounds().getY(), (int)g.getClipBounds().getWidth(), (int)g.getClipBounds().getHeight());
		// Add what's to draw:
		WorldPoint p = new WorldPoint(_player.getLocation().getX()-this.getWidth()/2/World.BLOCK_PAINTSIZE,
								      _player.getLocation().getY()-(this.getHeight()+INVENTORY_HEIGHT)/2/World.BLOCK_PAINTSIZE);
		Vector<House> houses = new Vector<House>();
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
			(new House(new WorldPoint(20,20))).paint(g);
			// TODO optimize?
			for (House h : c.getHouses())
			{
				houses.add(h);
			}
		}
		for (House h : houses)
		{
			Point loc = h.getLocation().getLocationOnPanel((int)p.getX(), (int)p.getY());
			if (loc.getX() >= 0 && loc.getX()-World.BLOCK_PAINTSIZE <= this.getWidth() &&
				loc.getY() >= 0 && loc.getY()-World.BLOCK_PAINTSIZE <= this.getHeight())
			{
				h.paint(g, p);
			}
		}
		if (_lastMouseEvent != null)
		{
			g.setColor(Color.GREEN);
			g.fillOval(_lastMouseEvent.getX(), _lastMouseEvent.getY(), 5, 5);
		}
		_player.paint(g, p);
		
		ArrayList<Player> players = _gui.getClient().getCharacters();
		{
			for (Player pl : players)
			{
				pl.paint(g, p);
			}
		}
		//this.paintComponents(g);
		_buildingMenu.paint(g);
	}
	
	@Override
	public void mouseDragged(MouseEvent e) { }

	@Override
	public void mouseMoved(MouseEvent e)
	{
		_lastMouseEvent = e;
		this.invokeRepaint();
		if (e.getY() < INVENTORY_HEIGHT)
		{
			_gui.enableCursor();
		}
		else
		{
			_gui.disableCursor();
		}
	}

	public void invokeRepaint()
	{
		_gui.repaint();		
	}
	public void invokeRepaint(int x1, int y1, int x2, int y2)
	{
		_gui.repaint(x1, y1, x2, y2);		
	}

	@Override
	public void playerMoved(PlayerMovementEvent e)
	{
		this.repaint();
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void componentHidden(ComponentEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void componentMoved(ComponentEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void componentResized(ComponentEvent arg0)
	{
		this._inventory.setBounds(getBounds().x,
								  getBounds().y,
								  getBounds().width,
								  INVENTORY_HEIGHT);
		this._buildingMenu.setBounds(getBounds().x,
									 getBounds().y+getBounds().height-BUILDING_MENU_HEIGHT,
									 getBounds().width,
									 BUILDING_MENU_HEIGHT);
		
	}

	@Override
	public void componentShown(ComponentEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}

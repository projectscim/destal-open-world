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
package destal.client.ui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import javax.swing.JPanel;

import destal.client.event.PlayerActionEvent;
import destal.client.event.PlayerMovementEvent;
import destal.client.event.listener.PlayerActionListener;
import destal.client.event.listener.PlayerMovementListener;
import destal.shared.entity.character.HumanPlayer;
import destal.shared.world.World;

public class GamePanel extends JPanel implements MouseMotionListener,
												 MouseListener,
												 ComponentListener,
												 PlayerActionListener
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
	private ChunkPanel _chunkPanel;
	
	public static final int INVENTORY_HEIGHT = World.BLOCK_PAINTSIZE*2;
	public static final int BUILDING_MENU_HEIGHT = World.BLOCK_PAINTSIZE*4;
	
	public GamePanel (int width, int height, GUI gui)
	{
		super();
		this.setBounds(0, 0, width, height);
		_gui = gui;
		_player = _gui.getClient().getLocalCharacter();
		_inventory = new Inventory(this.getWidth(), INVENTORY_HEIGHT, _player);

		_chunkPanel = new ChunkPanel(0,
									 INVENTORY_HEIGHT,
									 (int)getBounds().getWidth(),
									 (int)getBounds().getHeight()-INVENTORY_HEIGHT-BUILDING_MENU_HEIGHT,
									 _gui);
		_buildingMenu = new BuildingMenu(0,
										 getHeight()-BUILDING_MENU_HEIGHT,
										 getWidth(), BUILDING_MENU_HEIGHT,
										 _player);
		
		this.add(_buildingMenu);
		this.add(_inventory);
		this.add(_chunkPanel);
		this.validate();
		
		this.addMouseMotionListener(this);
		_chunkPanel.addMouseMotionListener(_player);
		_chunkPanel.addMouseListener(_player);
		this.addKeyListener(_player);
		this.addKeyListener(_buildingMenu);
		this.addComponentListener(this);
		_player.addPlayerActionListener(this);

		_player.setContainer(_chunkPanel);
		setDoubleBuffered(true);
	}
	
	@Override
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		if (_lastMouseEvent != null)
		{
			g.setColor(Color.GREEN);
			g.fillOval(_lastMouseEvent.getX(), _lastMouseEvent.getY(), 5, 5);
		}
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
			//_gui.enableCursor();
		}
		else
		{
			//_gui.disableCursor();
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
	public void mouseClicked(MouseEvent e) { }

	@Override
	public void mouseEntered(MouseEvent e) { }

	@Override
	public void mouseExited(MouseEvent e) { }

	@Override
	public void mousePressed(MouseEvent e) { }

	@Override
	public void mouseReleased(MouseEvent e) { }

	@Override
	public void componentHidden(ComponentEvent e) { }

	@Override
	public void componentMoved(ComponentEvent e) { }

	@Override
	public void componentResized(ComponentEvent e)
	{
		this._inventory.setBounds(getBounds().x,
								  getBounds().y,
								  getBounds().width,
								  INVENTORY_HEIGHT);
		this._buildingMenu.setBounds(getBounds().x,
									 getBounds().y+getBounds().height-BUILDING_MENU_HEIGHT,
									 getBounds().width,
									 BUILDING_MENU_HEIGHT);
		this._chunkPanel.setBounds(0,
				 				   INVENTORY_HEIGHT,
				 				   getBounds().width,
				 				   getBounds().height-INVENTORY_HEIGHT-BUILDING_MENU_HEIGHT);
		
	}

	@Override
	public void componentShown(ComponentEvent e) {}

	@Override
	public void playerAction(PlayerActionEvent e) {}

	@Override
	public void playerBuildHouse(PlayerActionEvent e)
	{
		this.requestFocusInWindow();
	}

	@Override
	public void playerBlockClicked(PlayerActionEvent e) {}

	@Override
	public void playerRequestMove(PlayerActionEvent e) {
		// TODO Auto-generated method stub
		
	}
}

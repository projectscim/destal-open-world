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
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

import destal.shared.entity.character.HumanPlayer;
import destal.shared.entity.character.HumanPlayer.PlayerState;
import destal.shared.entity.data.Values;
import destal.shared.util.DataContainer;

public class BuildingMenu extends JPanel implements KeyListener, ActionListener, ComponentListener
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 5471169876111787240L;

	private enum MenuMode {MINIMIZED, MAXIMIZED};
	private MenuMode _mode;
	private HumanPlayer _player;
	private JButton[] _button;
	
	public BuildingMenu (int x, int y, int width, int height, HumanPlayer player)
	{
		super();
		_player = player;
		setMode(MenuMode.MAXIMIZED);
		this.setBounds(x,y,width,height);
		this.setOpaque(true);
		this.setBackground(Color.GREEN);

		setDoubleBuffered(true);
		this.addComponentListener(this);
		_button = new JButton[]{new JButton(),
								new JButton(),
                				new JButton(),
                				new JButton(),
                				new JButton ("Move")};
		_button[0].setIcon(new ImageIcon(DataContainer.getTexture(Values.HOUSE_HOUSE)));
		_button[0].setActionCommand(Values.HOUSE_HOUSE+"");
		_button[1].setIcon(new ImageIcon(DataContainer.getTexture(Values.HOUSE_BLACKSMITH)));
		_button[1].setActionCommand(Values.HOUSE_BLACKSMITH+"");
		_button[2].setIcon(new ImageIcon(DataContainer.getTexture(Values.HOUSE_CASTLE)));
		_button[2].setActionCommand(Values.HOUSE_CASTLE+"");
		_button[3].setIcon(new ImageIcon(DataContainer.getTexture(Values.HOUSE_HARBOUR)));
		_button[3].setActionCommand(Values.HOUSE_HARBOUR+"");
		for (int i = 0; i < _button.length; i++)
		{
			_button[i].setSize(100, 100);
			_button[i].setDoubleBuffered(true);
			_button[i].addActionListener(this);
			_button[i].setVisible(true);
			this.add(_button[i]);
		}
		this.validate();
		this.setVisible(true);
	}
	
	public MenuMode getMode()
	{
		return _mode;
	}

	public void setMode(MenuMode mode)
	{
		_mode = mode;
		if (_mode == MenuMode.MINIMIZED)
		{
			_player.setPlayerState(PlayerState.MOVING);
		}
		else if (_mode == MenuMode.MAXIMIZED)
		{
			_player.setPlayerState(PlayerState.BUILDING);
		}
	}
	
	@Override
	public void paintComponent (Graphics g)
	{
		super.paintComponent(g);
		Rectangle r = this.getBounds();
		g.setColor(Color.GREEN);
		g.drawRect(r.x, r.y, r.width, r.height);
		if (_mode == MenuMode.MAXIMIZED)
		{
			g.fillRect(r.x, r.y, r.width, r.height);	
		}
		else if (_mode == MenuMode.MINIMIZED)
		{
			g.fillRect(r.x, r.y+r.height/2, r.height/2, r.height/2);
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {}

	@Override
	public void keyReleased(KeyEvent e) {}

	@Override
	public void keyTyped(KeyEvent e)
	{
		if (e.getKeyChar() == 'b')
		{
			System.out.println("change mode");
			if (_mode == MenuMode.MINIMIZED)
			{
				setMode(MenuMode.MAXIMIZED);
			}
			else if (_mode == MenuMode.MAXIMIZED)
			{
				setMode(MenuMode.MINIMIZED);
			}
			this.repaint();
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) 
	{
		_player.setCurrentBuilding(Integer.parseInt(e.getActionCommand()));
    	if (e.getActionCommand().equals("Move"))
    	{

    	}
	}

	@Override
	public void componentHidden(ComponentEvent e) {}

	@Override
	public void componentMoved(ComponentEvent e) {}

	@Override
	public void componentResized(ComponentEvent e)
	{
		for (int i = 0; i < _button.length; i++)
		{
			Rectangle r = getBounds();
			_button[i].setLocation(r.x, r.y);
			_button[i].setSize(100, 100);
		}
		this.invalidate();
		this.validate();
		this.repaint();
	}

	@Override
	public void componentShown(ComponentEvent e) {}
}
	
    
    
    
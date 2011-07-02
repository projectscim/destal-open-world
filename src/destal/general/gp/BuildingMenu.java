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
package destal.general.gp;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.ImageObserver;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;

import destal.entity.character.HumanPlayer;
import destal.entity.character.HumanPlayer.PlayerState;
import destal.entity.data.Values;
import destal.util.DataContainer;

public class BuildingMenu extends JPanel implements KeyListener, ActionListener
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 5471169876111787240L;

	private enum MenuMode {MINIMIZED, MAXIMIZED};
	private MenuMode mode;
	private HumanPlayer _player;
	private JButton[] _button;
	
	public BuildingMenu (int x, int y, int width, int height, HumanPlayer player)
	{
		super();
		_player = player;
		mode = MenuMode.MINIMIZED;
		this.setBounds(x,y,width,height);
		this.setOpaque(true);
		this.setVisible(true);
		setDoubleBuffered(true);
		_button = new JButton[]{new JButton (setIcon(new ImageIcon(DataContainer.getTexture(Values.HOUSE_HOUSE)))),
                new JButton (setIcon(new ImageIcon(DataContainer.getTexture(Values.HOUSE_SECONDHOUSE))))};
		for (int i = 0; i < _button.length; i++)
		{
			Rectangle r = this.getBounds();
			_button[i].setLocation(r.x, r.y);
			_button[i].setSize(r.width,r.height);
			this.add(_button[i]);
			_button[i].addActionListener(this);
		}

	}
	
	private Icon setIcon(ImageIcon imageIcon) {
		// TODO Auto-generated method stub
		return null;
	}

	private JComponent _container;
	
	public void setContainer(JComponent container)
	{
		_container = container;
	}
	
	@Override
	public void paint (Graphics g)
	{
		Rectangle r = this.getBounds();
		g.setColor(Color.RED);
		if (mode == MenuMode.MAXIMIZED)
		{
			g.fillRect(r.x, r.y, r.width, r.height);			
		}
		else if (mode == MenuMode.MINIMIZED)
		{
			g.fillRect(r.x, r.y+r.height/2, r.height/2, r.height/2);
		}
	}

	@Override
	public void keyPressed(KeyEvent e) 	{}

	@Override
	public void keyReleased(KeyEvent e) {}

	@Override
	public void keyTyped(KeyEvent e)
	{
		if (e.getKeyChar() == 'b')
		{
			if (mode == MenuMode.MINIMIZED)
			{
				mode = MenuMode.MAXIMIZED;
				_player.setPlayerState(PlayerState.BUILDING);
			}
			else if (mode == MenuMode.MAXIMIZED)
			{
				mode = MenuMode.MINIMIZED;
				_player.setPlayerState(PlayerState.MOVING);
			}
			if (_container != null)
			{
				_container.repaint();
			}
			this.repaint();
		}
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
	
}
	
    
    
    
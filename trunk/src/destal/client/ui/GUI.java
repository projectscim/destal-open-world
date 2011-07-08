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
import java.awt.Cursor;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;
import javax.swing.JPanel;

import destal.client.Client;


public class GUI extends JFrame implements ComponentListener
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 5429551745650490493L;
	private Client _client;
	private JPanel[] _panels;
	private JPanel _curPanel;

	private GUIMode _guiMode;
	
	public enum GUIMode {TITLE, MENU, OPTIONS, GAME}
	
	public GUI(int width, int height, Client client)
	{
	    super("destal open world");
	    
		_client = client;
		_panels = new JPanel[GUIMode.values().length];
		
	    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    this.setBounds(0,0,width,height);
	    //this.setResizable(false);
	    setLocationRelativeTo(null);
	    
	    _panels[GUIMode.MENU.ordinal()] = new MenuPanel(this);
	    _panels[GUIMode.GAME.ordinal()] = new GamePanel(width, height, this);
	    _panels[GUIMode.OPTIONS.ordinal()] = new OptionPanel(this);
	    
	    for(int i = 0; i < _panels.length; i++)
	    {
	    	if(_panels[i] != null)
	    	{
		    	_panels[i].setLayout(null);
	    	}
	    }
	    
	    this.setGUIMode(GUIMode.MENU);
	    
	    this.setVisible(true);
	    this.toFront();
	    
	    setBackground(Color.RED);
	    //this.addMouseMotionListener(_client.getLocalCharacter());
	}
	
	public Client getClient()
	{
		return _client;
	}
	
	public void setGUIMode(GUIMode mode)
	{
		_guiMode = mode;
		// TODO remove?
		if (_guiMode == GUIMode.GAME)
		{
			//disableCursor();
			//this.setExtendedState(JFrame.MAXIMIZED_BOTH);
		}
		else
		{
			enableCursor();
		}
		JPanel panel = _panels[_guiMode.ordinal()];
		if(panel != null)
		{
			if (_curPanel != null)
			{
				this.remove(_curPanel);
			}
			_curPanel = panel;
			
			this.add(_curPanel);
			this.invalidate();
			this.validate();
			_curPanel.requestFocusInWindow();
			this.repaint();
		}
	}
	
	public void disableCursor()
	{
        Cursor c = Toolkit.getDefaultToolkit().createCustomCursor(
                new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB),
                new Point(1, 1), "Custom Cursor");
 
        this.setCursor(c);
	}
	
	public void enableCursor()
	{
		Cursor c = Cursor.getDefaultCursor();
		this.setCursor(c);
	}

	@Override
	public void componentHidden(ComponentEvent e) { }

	@Override
	public void componentMoved(ComponentEvent e) { }

	@Override
	public void componentResized(ComponentEvent e) { }

	@Override
	public void componentShown(ComponentEvent e) { }
}


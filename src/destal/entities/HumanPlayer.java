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
package destal.entities;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;

import destal.entities.characters.Player;
import destal.general.Client;
import destal.general.event.events.PlayerMovementEvent;
import destal.general.event.listener.PlayerMovementListener;
import destal.general.ui.GamePanel;
import destal.general.world.Chunk;

public class HumanPlayer extends Player implements KeyListener, MouseMotionListener, PlayerMovementListener
{
	private ArrayList<PlayerMovementListener> _playerMovementListener;
	private MouseEvent _lastMouseEvent;
	private GamePanel _gamePanel;
	private Chunk _currentChunk;
	private Client _client;

	
	public HumanPlayer()
	{
		super();
		_playerMovementListener = new ArrayList<PlayerMovementListener>();
		this.addPlayerMovementListener(this);
	}
	
	public HumanPlayer(Client client)
	{
		this();
		_client = client;
	}
	
	public void setCurrentChunk(Chunk c)
	{
		_currentChunk = c;
	}
	
	public void setContainer(GamePanel container)
	{
		_gamePanel = container;
	}

	public void move(int direction)
	{
		Point p = new Point(_gamePanel.getWidth()/2, _gamePanel.getHeight()/2);
		double dx = (p.distance(new Point(_lastMouseEvent.getX(), _lastMouseEvent.getY())) > 1 ? (_lastMouseEvent.getX()-p.getX()) * 0.3 / p.distance(new Point(_lastMouseEvent.getX(), _lastMouseEvent.getY())) : 0);
		double dy = (p.distance(new Point(_lastMouseEvent.getY(), _lastMouseEvent.getY())) > 1 ? (_lastMouseEvent.getY()-p.getY()) * 0.3 / p.distance(new Point(_lastMouseEvent.getX(), _lastMouseEvent.getY())) : 0);
			
		//if (_currentChunk.getBlock((int)this.getLocation().getX(), (int)this.getLocation().getY()) instanceof IWalkable)
		{
			this.setLocation(this.getLocation().getX()+dx*direction, this.getLocation().getY()+dy*direction);
		}		
		this.invokePlayerMoved();
	}
	
	/**
	 * Adds the specified player movement listener to receive movement events from this player
	 */
	public void addPlayerMovementListener(PlayerMovementListener listener)
	{
		_playerMovementListener.add(listener);
	}
	/**
	 * [intern]
	 * Invokes all playerMoved() methods in the specified listeners
	 */
	private void invokePlayerMoved()
	{
		PlayerMovementEvent e = new PlayerMovementEvent(this, this.getLocation());
		for (PlayerMovementListener l : _playerMovementListener)
		{
			l.playerMoved(e);
		}
	}
	
	@Override
	public void paint(Graphics g)
	{
		g.drawImage(this.getImage(), _gamePanel.getWidth()/2, _gamePanel.getHeight()/2, null);
	}
	
	
	@Override
	public void keyPressed(KeyEvent e) { }

	@Override
	public void keyReleased(KeyEvent e) { }

	@Override
	public void keyTyped(KeyEvent e)
	{
		this.move((e.getKeyChar() == 'w') ? 1 : ((e.getKeyChar() == 's') ? -1 : 0));
	}
	
	@Override
	public void mouseDragged(MouseEvent e) { }

	@Override
	public void mouseMoved(MouseEvent e)
	{
		_lastMouseEvent = e;
	}

	@Override
	public void playerMoved(PlayerMovementEvent e)
	{
		if(!getLocation().getChunkLocation().equals(_currentChunk.getLocation()))
		{
			System.out.println("left chunk");
			_client.leftChunk(getLocation().getChunkLocation(), _currentChunk.getLocation());
		}
	}

}

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
package destal.entity.character;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;

import destal.event.events.player.PlayerActionEvent;
import destal.event.events.player.PlayerMovementEvent;
import destal.event.listener.PlayerActionListener;
import destal.event.listener.PlayerMovementListener;
import destal.general.client.Client;
import destal.general.ui.GamePanel;
import destal.general.world.Chunk;
import destal.general.world.World;
import destal.general.world.WorldPoint;

public class HumanPlayer extends Player implements KeyListener, MouseMotionListener, PlayerMovementListener, MouseListener
{
	private ArrayList<PlayerMovementListener> _playerMovementListener;
	private ArrayList<PlayerActionListener> _playerActionListener;
	private MouseEvent _lastMouseEvent;
	private GamePanel _gamePanel;
	private Chunk _currentChunk;
	private Client _client;
	private PlayerState _playerState;
	
	public enum PlayerState {MOVING, BUILDING}
	
	public HumanPlayer()
	{
		super();
		_playerMovementListener = new ArrayList<PlayerMovementListener>();
		_playerActionListener = new ArrayList<PlayerActionListener>();
		_playerState = PlayerState.MOVING;
		
		this.addPlayerMovementListener(this);
	}
	
	public HumanPlayer(Client client)
	{
		this();
		_client = client;
	}
	
	public void setPlayerState(PlayerState p)
	{
		_playerState = p;
	}
	
	public PlayerState getPlayerState()
	{
		return _playerState;
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
		if (_playerState != PlayerState.MOVING)
		{
			return;
		}
		Point p = new Point(_gamePanel.getWidth()/2, _gamePanel.getHeight()/2);
		double dx = (p.distance(new Point(_lastMouseEvent.getX(), _lastMouseEvent.getY())) > 1 ? (_lastMouseEvent.getX()-p.getX()) * 0.3 / p.distance(new Point(_lastMouseEvent.getX(), _lastMouseEvent.getY())) : 0);
		double dy = (p.distance(new Point(_lastMouseEvent.getY(), _lastMouseEvent.getY())) > 1 ? (_lastMouseEvent.getY()-p.getY()) * 0.3 / p.distance(new Point(_lastMouseEvent.getX(), _lastMouseEvent.getY())) : 0);
		this.setLocation(this.getLocation().getX()+dx*direction, this.getLocation().getY()+dy*direction);	
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
	 * Adds the specified player action listener to receive movement events from this player
	 */
	public void addPlayerActionListener(PlayerActionListener listener)
	{
		_playerActionListener.add(listener);
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
		if (e.getKeyChar() == 'b')
		{
			this._playerState = PlayerState.BUILDING;
		}
		else if (e.getKeyChar() == 'w' || e.getKeyChar() == 's')
		{
			this._playerState = PlayerState.MOVING;
			this.move((e.getKeyChar() == 'w') ? 1 : ((e.getKeyChar() == 's') ? -1 : 0));
		}
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

	@Override
	public void mouseClicked(MouseEvent e)
	{
		// TODO Build house
		if (_playerState == PlayerState.BUILDING)
		{
			System.out.println("Build house");
			// upper left hand corner
			WorldPoint p = new WorldPoint(this.getLocation().getX()-_gamePanel.getWidth()/2/World.BLOCK_PAINTSIZE,
					this.getLocation().getY()-_gamePanel.getHeight()/2/World.BLOCK_PAINTSIZE);
			// location of the house
			WorldPoint h = new WorldPoint(p.getX()+e.getX()/World.BLOCK_PAINTSIZE,
										  p.getY()+e.getY()/World.BLOCK_PAINTSIZE);
			PlayerActionEvent pe = new PlayerActionEvent(this);
			pe.setLocation(h);
			for (PlayerActionListener l : _playerActionListener)
			{
				l.playerBuildHouse(pe);
			}
			
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

}

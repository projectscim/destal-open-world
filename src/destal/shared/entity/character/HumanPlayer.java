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
package destal.shared.entity.character;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;

import javax.swing.JPanel;

import destal.client.Client;
import destal.event.listener.PlayerActionListener;
import destal.event.listener.PlayerMovementListener;
import destal.general.world.Chunk;
import destal.general.world.World;
import destal.general.world.WorldPoint;
import destal.shared.entity.data.Values;
import destal.shared.entity.item.Item;
import destal.shared.event.player.PlayerActionEvent;
import destal.shared.event.player.PlayerMovementEvent;

public class HumanPlayer extends Player implements KeyListener, MouseMotionListener, PlayerMovementListener, MouseListener
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -6618735625250923438L;
	private ArrayList<PlayerMovementListener> _playerMovementListener;
	private ArrayList<PlayerActionListener> _playerActionListener;
	private MouseEvent _lastMouseEvent;
	private JPanel _gamePanel;
	private Chunk _currentChunk;
	private Client _client;
	private PlayerState _playerState;
	private ArrayList<Item> _items;
	private int _currentBuilding;
	
	public enum PlayerState {MOVING, BUILDING}
	/**
	 * Creates a new player
	 */
	public HumanPlayer()
	{
		super();
		_playerMovementListener = new ArrayList<PlayerMovementListener>();
		_playerActionListener = new ArrayList<PlayerActionListener>();
		_playerState = PlayerState.MOVING;
		_items = new ArrayList<Item>();
		this.addItems(Values.ITEM_WOOD, 5);
		
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
	
	public void setContainer(JPanel gamePanel)
	{
		_gamePanel = gamePanel;
	}

	public int getItemQuantity(int dataValue)
	{
		for (Item i : _items)
		{
			if (i.getDataValue() == dataValue)
			{
				return i.getQuantity();
			}
		}
		return 0;
	}
	
	public void move(int direction)
	{
		Point p = new Point(_gamePanel.getWidth()/2, _gamePanel.getHeight()/2);
		double dx = (p.distance(new Point(_lastMouseEvent.getX(), _lastMouseEvent.getY())) > 1 ? (_lastMouseEvent.getX()-p.getX()) * 0.3 / p.distance(new Point(_lastMouseEvent.getX(), _lastMouseEvent.getY())) : 0);
		double dy = (p.distance(new Point(_lastMouseEvent.getY(), _lastMouseEvent.getY())) > 1 ? (_lastMouseEvent.getY()-p.getY()) * 0.3 / p.distance(new Point(_lastMouseEvent.getX(), _lastMouseEvent.getY())) : 0);
		this.setLocation(this.getLocation().getX()+dx*direction, this.getLocation().getY()+dy*direction);	
		this.invokePlayerMoved();
	}
	/**
	 * Adds the specified quantity of the item specified by the dataValue
	 * to the player's inventory
	 * @param dataValue The data value which specifies the item type
	 * @param quantity The quantity to be added
	 */
	public void addItems(int dataValue, int quantity)
	{
		for (Item i : _items)
		{
			if (i.getDataValue() == dataValue)
			{
				i.increaseQuantity(quantity);
				return;
			}
		}
		_items.add(Item.create(dataValue));
		addItems(dataValue, quantity);
	}
	/**
	 * Removes the specified quantity of the item specified by the dataValue
	 * from the player's inventory
	 * @param dataValue The data value which specifies the item type
	 * @param quantity The quantity to be added
	 */
	public void removeItems(int dataValue, int quantity) throws IllegalArgumentException
	{
		for (Item i : _items)
		{
			if (i.getDataValue() == dataValue)
			{
				if (i.getQuantity() < quantity)
				{
					throw new IllegalArgumentException("Specified quantity too high");
				}
				i.decreaseQuantity(quantity);
				return;
			}
		}
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
		System.out.println("key pressed");
		if (e.getKeyChar() == 'w' || e.getKeyChar() == 's')
		{
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
			pe.setBuildingType(_currentBuilding);
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

	public void setCurrentBuilding(int currentBuilding)
	{
		if ((currentBuilding & 0x20) !=  0x20)
			throw new IllegalArgumentException("Invalid building data value");
		this._currentBuilding = currentBuilding;
	}

	public int getCurrentBuilding()
	{
		return _currentBuilding;
	}
}

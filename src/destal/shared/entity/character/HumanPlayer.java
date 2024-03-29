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
import destal.client.event.PlayerActionEvent;
import destal.client.event.PlayerMovementEvent;
import destal.client.event.listener.PlayerActionListener;
import destal.client.event.listener.PlayerMovementListener;
import destal.shared.entity.IWalkable;
import destal.shared.entity.block.Block;
import destal.shared.entity.data.Values;
import destal.shared.entity.item.Item;
import destal.shared.world.Chunk;
import destal.shared.world.World;
import destal.shared.world.WorldPoint;
// TODO move to client package?
public class HumanPlayer extends Player implements KeyListener, MouseMotionListener, MouseListener
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -6618735625250923438L;
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
		_playerActionListener = new ArrayList<PlayerActionListener>();
		_playerState = PlayerState.MOVING;
		_items = new ArrayList<Item>();
		this.addItems(Values.ITEM_WOOD, 5);
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
		PlayerActionEvent e = new PlayerActionEvent(this, PlayerActionEvent.EventType.REQUEST_MOVE);
		Point p = new Point(_gamePanel.getWidth()/2, _gamePanel.getHeight()/2);
		Point mousePos = new Point(_lastMouseEvent.getX(), _lastMouseEvent.getY());
		double dx = p.distance(mousePos) > World.PLAYER_PAINTSIZE ? (_lastMouseEvent.getX()-p.getX()) / p.distance(mousePos) : 0;
		double dy = p.distance(mousePos) > World.PLAYER_PAINTSIZE ? (_lastMouseEvent.getY()-p.getY()) / p.distance(mousePos) : 0;
		
		e.setLocation(this.getLocation());
		e.setDirection(new WorldPoint(dx, dy));
		
		for (PlayerActionListener l : _playerActionListener)
		{
			l.playerRequestMove(e);
		}
		
		/*
		Point p = new Point(_gamePanel.getWidth()/2, _gamePanel.getHeight()/2);
		Point mousePos = new Point(_lastMouseEvent.getX(), _lastMouseEvent.getY());
		double dx = p.distance(mousePos) > World.PLAYER_PAINTSIZE ? (_lastMouseEvent.getX()-p.getX()) * 0.3 / p.distance(mousePos) : 0;
		double dy = p.distance(mousePos) > World.PLAYER_PAINTSIZE ? (_lastMouseEvent.getY()-p.getY()) * 0.3 / p.distance(mousePos) : 0;
		
		WorldPoint newLoc = new WorldPoint(this.getLocation().getX()+dx*direction, this.getLocation().getY()+dy*direction);
		
		Block[] destinations = new Block[] {_currentChunk.getBlock((int)newLoc.x, (int)newLoc.y),
											_currentChunk.getBlock((int)newLoc.x, (int)newLoc.y+1),
											_currentChunk.getBlock((int)newLoc.x+1, (int)newLoc.y),
											_currentChunk.getBlock((int)newLoc.x+1, (int)newLoc.y+1)};
		//Block destination = _currentChunk.getBlock((int)newLoc.x, (int)newLoc.y);
		boolean walkable = true;
		for (Block b : destinations)
		{
			// TODO improve chunk loading
			// b == null -> b is not within the current chunk
			// load from world?/server request?
			if (b != null)
			{
				if (!(b instanceof IWalkable))
				{
					walkable = false;
				}
			}
		}
		
		if (walkable)
		{
			this.setLocation(newLoc);	
			this.invokePlayerMoved();
		}
		else
		{
			//System.out.println(destination.getDataValue());
		}*/
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

	public void checkChunk()
	{
		if(!getLocation().getChunkLocation().equals(_currentChunk.getLocation()))
		{
			System.out.println("left chunk");
			_client.leftChunk(getLocation().getChunkLocation(), _currentChunk.getLocation());
		}
	}
	/**
	 * Adds the specified player action listener to receive movement events from this player
	 */
	public void addPlayerActionListener(PlayerActionListener listener)
	{
		_playerActionListener.add(listener);
	}
	
	@Override
	public void paint(Graphics g)
	{
		// TODO replace 16 by size of sprite
		g.drawImage(this.getImage(), _gamePanel.getWidth()/2-16, _gamePanel.getHeight()/2-16, null);
	}

	@Override
	public void keyPressed(KeyEvent e) { }

	@Override
	public void keyReleased(KeyEvent e) { }

	@Override
	public void keyTyped(KeyEvent e)
	{
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
			PlayerActionEvent pe = new PlayerActionEvent(this, PlayerActionEvent.EventType.BUILD_HOUSE);
			pe.setLocation(h);
			pe.setBuildingType(_currentBuilding);
			for (PlayerActionListener l : _playerActionListener)
			{
				l.playerBuildHouse(pe);
			}
		}
		else if (_playerState == PlayerState.MOVING)
		{
			// mine the block
			//
			PlayerActionEvent pe = new PlayerActionEvent(this, PlayerActionEvent.EventType.BLOCK_CLICKED);
			// upper left hand corner
			WorldPoint p = new WorldPoint(this.getLocation().getX()-_gamePanel.getWidth()/2/World.BLOCK_PAINTSIZE,
					this.getLocation().getY()-_gamePanel.getHeight()/2/World.BLOCK_PAINTSIZE);
			// location of the house
			WorldPoint h = new WorldPoint(p.getX()+e.getX()/World.BLOCK_PAINTSIZE,
										  p.getY()+e.getY()/World.BLOCK_PAINTSIZE);
			pe.setLocation(h);
			for (PlayerActionListener l : _playerActionListener)
			{
				l.playerBlockClicked(pe);
			}
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) { }

	@Override
	public void mouseExited(MouseEvent e) { }

	@Override
	public void mousePressed(MouseEvent e) { }

	@Override
	public void mouseReleased(MouseEvent e) { }

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

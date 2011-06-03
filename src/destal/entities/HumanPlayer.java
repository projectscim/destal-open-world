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
	
	public void searchCurrentChunk()
	{
		// move this somewhere else
		Chunk newCurrent = null;
		for (int i = 0; i < _client.getChunkBuffer().length; i++)
		{
			if(_client.getChunkBuffer()[i] == null)
				continue;
			
			if(getLocation().getChunkLocation().equals(_client.getChunkBuffer()[i].getLocation()))
			{
				newCurrent = _client.getChunkBuffer()[i];
			}
			if(Math.abs(_client.getChunkBuffer()[i].getLocation().x - getLocation().getChunkLocation().x) > 1 ||
			   Math.abs(_client.getChunkBuffer()[i].getLocation().y - getLocation().getChunkLocation().y) > 1)
			{
				_client.getChunkBuffer()[i] = null;
			}
		}
		
		if(newCurrent != null)
		{
			_currentChunk = newCurrent;
		}
		else
		_client.chunkNeeded(getLocation().getChunkLocation());
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
			searchCurrentChunk();
		}
	}

}

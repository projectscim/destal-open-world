package entities;

import general.GamePanel;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

public class Player extends Character implements KeyListener, MouseMotionListener
{
	private MouseEvent _lastMouseEvent;
	private GamePanel _gamePanel;
	
	public Player()
	{
		super();
		this.setLocation(20, 100);
	}
	
	public Player(GamePanel container)
	{
		this();
		_gamePanel = container;
	}
	
	public void setContainer(GamePanel container)
	{
		_gamePanel = container;
	}

	public void move(boolean forward)
	{
		double dx = (this.getLocation().distance(new Point(_lastMouseEvent.getX(), _lastMouseEvent.getY())) > 1 ? (_lastMouseEvent.getX()-this.getLocation().getX()) * 3 / this.getLocation().distance(new Point(_lastMouseEvent.getX(), _lastMouseEvent.getY())) : 0);
		double dy = (this.getLocation().distance(new Point(_lastMouseEvent.getY(), _lastMouseEvent.getY())) > 1 ? (_lastMouseEvent.getY()-this.getLocation().getY()) * 3 / this.getLocation().distance(new Point(_lastMouseEvent.getX(), _lastMouseEvent.getY())) : 0);
				
		if (forward)
		{
			this.setLocation(this.getLocation().getX()+dx, this.getLocation().getY()+dy);
		}
		else
		{
			this.setLocation(this.getLocation().getX()-dx, this.getLocation().getY()-dy);
		}
		_gamePanel.invokeRepaint();
	}
	
	@Override
	public void paint(Graphics g)
	{
		g.drawImage(this.getImage(), _gamePanel.getWidth()/2, _gamePanel.getHeight()/2, null);
	}
	
	
	@Override
	public void keyPressed(KeyEvent e)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyReleased(KeyEvent e)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent e)
	{
		this.move((e.getKeyChar() == 'w') ? true : false);
	}
	
	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseMoved(MouseEvent e)
	{
		_lastMouseEvent = e;
	}

}

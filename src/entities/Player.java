package entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

public class Player extends Character implements KeyListener, MouseMotionListener
{
	private MouseEvent _lastMouseEvent;
	
	public Player()
	{
		super();
		this.setLocation(20, 100);
	}
	
	@Override
	public void paint(Graphics g)
	{
		g.setColor(Color.BLACK);
		g.fillRect((int)this.getLocation().getX()-5, (int)this.getLocation().getY()-5, 10, 10);
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
		/*
		double dx, dy;
		if (this.getLocation().distance(new Point(_lastMouseEvent.getX(), _lastMouseEvent.getY())) != 0)
		{
			dx = (_lastMouseEvent.getX()-this.getLocation().getX()) * 3 / this.getLocation().distance(new Point(_lastMouseEvent.getX(), _lastMouseEvent.getY()));
			dy = (_lastMouseEvent.getY()-this.getLocation().getY()) * 3 / this.getLocation().distance(new Point(_lastMouseEvent.getX(), _lastMouseEvent.getY()));
		}
		else
		{
			dx = 0;
			dy = 0;
		}*/
		
		// this is shorter, Alex'll be happy :D
		double dx = (this.getLocation().distance(new Point(_lastMouseEvent.getX(), _lastMouseEvent.getY())) > 1 ? (_lastMouseEvent.getX()-this.getLocation().getX()) * 3 / this.getLocation().distance(new Point(_lastMouseEvent.getX(), _lastMouseEvent.getY())) : 0);
		double dy = (this.getLocation().distance(new Point(_lastMouseEvent.getY(), _lastMouseEvent.getY())) > 1 ? (_lastMouseEvent.getY()-this.getLocation().getY()) * 3 / this.getLocation().distance(new Point(_lastMouseEvent.getX(), _lastMouseEvent.getY())) : 0);
				
		switch (e.getKeyChar())
		{
			case 'w':
				this.setLocation(this.getLocation().getX()+dx, this.getLocation().getY()+dy);
				break;
			case 's':
				this.setLocation(this.getLocation().getX()-dx, this.getLocation().getY()-dy);
				break;
				
		}
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

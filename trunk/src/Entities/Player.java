package Entities;

import java.io.*;
import java.util.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.lang.*;

public class Player extends Character implements KeyListener, MouseMotionListener
{
	private MouseEvent _lastMouseEvent;
	
	public Player()
	{
		super();
	}
	
	@Override
	public void paint(Graphics g)
	{
		g.setColor(Color.BLACK);
		g.fillRect((int)this.getLocation().getX()-5, (int)this.getLocation().getY()-5, 10, 10);
		if (_lastMouseEvent != null)
		{
			//double dx = (_lastMouseEvent.getX()-this.getLocation().getX()) * 30 / this.getLocation().distance(_lastMouseEvent.getLocationOnScreen());
			//double dy = (_lastMouseEvent.getY()-this.getLocation().getY()) * 30 / this.getLocation().distance(_lastMouseEvent.getLocationOnScreen());
			//g.drawOval((int)(this.getLocation().getX()+dx), (int)(this.getLocation().getY()+dy), 6, 6);
			g.drawOval((int)_lastMouseEvent.getX(), (int)_lastMouseEvent.getY(), 6, 6);
		}
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
		double dx = (_lastMouseEvent.getX()-this.getLocation().getX()) * 3 / this.getLocation().distance(new Point(_lastMouseEvent.getX(), _lastMouseEvent.getY()));
		double dy = (_lastMouseEvent.getY()-this.getLocation().getY()) * 3 / this.getLocation().distance(new Point(_lastMouseEvent.getX(), _lastMouseEvent.getY()));
		
		System.out.println(dx*dx+dy*dy);
		
		switch (e.getKeyChar())
		{
			case 'w':
				this.setLocation(this.getLocation().getX()+dx, this.getLocation().getY()+dy);
				break;
			case 's':
				this.setLocation(this.getLocation().getX()-dx, this.getLocation().getY()-dy);
				break;
			case 'a':
				this.setLocation(this.getLocation().getX()-1, this.getLocation().getY());
				break;
			case 'd':
				this.setLocation(this.getLocation().getX()+1, this.getLocation().getY());
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

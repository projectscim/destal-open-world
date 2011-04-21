package Entities;

import java.io.*;
import java.util.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.lang.*;

public class Player extends Character implements KeyListener
{

	public Player()
	{
		super();
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
		switch (e.getKeyChar())
		{
			case 'w':
				this.setLocation(this.getLocation().getX(), this.getLocation().getY()-1);
				break;
			case 's':
				this.setLocation(this.getLocation().getX(), this.getLocation().getY()+1);
				break;
			case 'a':
				this.setLocation(this.getLocation().getX()-1, this.getLocation().getY());
				break;
			case 'd':
				this.setLocation(this.getLocation().getX()+1, this.getLocation().getY());
				break;
				
		}
		
	}

}

package general;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import entities.Player;

public class GamePanel implements MouseMotionListener
{
	private GUI _gui;
	private MouseEvent _lastMouseEvent;
	private Player _player;
	
	
	public GamePanel (GUI gui, Player player)
	{
		super();
		_gui = gui;
		_player = player;
	}
	
	public void paint(Graphics g)
	{
		// Add what's to draw:
		g.setColor(Color.BLACK);
		if (_lastMouseEvent != null)
		{
			g.drawOval(_lastMouseEvent.getX(), _lastMouseEvent.getY(), 3, 3);
		}
		
		_player.paint(g);
		//
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

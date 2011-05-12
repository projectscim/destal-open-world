package general;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import entities.Player;

public class GamePanel extends JPanel implements MouseMotionListener
{
	private GUI _gui;
	private MouseEvent _lastMouseEvent;
	private Player _player;
	
	public GamePanel (GUI gui, Player player)
	{
		super();
		_gui = gui;
		_player = player;
		this.addMouseMotionListener(this);
		this.addMouseMotionListener(_player);
		this.addKeyListener(_player);
	}
	
	public void paint(Graphics g)
	{
		super.paint(g);
		disableCursor();
		g.setColor(Color.BLACK);
		if (_lastMouseEvent != null)
		{
			g.drawOval(_lastMouseEvent.getX(), _lastMouseEvent.getY(), 3, 3);
		}
		
		_player.paint(g);
	}

	public void disableCursor()
	{
        Cursor c = Toolkit.getDefaultToolkit().createCustomCursor(
                new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB),
                new Point(1, 1), "Custom Cursor");
 
        this.setCursor(c);
	}
	public void enableCursor()
	{
		Cursor c = Cursor.getDefaultCursor();
		this.setCursor(c);
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

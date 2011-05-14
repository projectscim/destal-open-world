package general;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
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
		
		_player.setContainer(this);
		
		setDoubleBuffered(true);
		setOpaque(true);
		setBackground(Color.BLUE);
	}
	
	@Override
	public void paintComponent(Graphics g)
	{
		// Add what's to draw:
		g.setColor(Color.BLACK);
		if (_lastMouseEvent != null)
		{
			g.drawOval(_lastMouseEvent.getX(), _lastMouseEvent.getY(), 3, 3);
		}
		_player.paint(g);
	}

	@Override
	public void mouseDragged(MouseEvent e)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseMoved(MouseEvent e)
	{
		_lastMouseEvent = e;
		this.invokeRepaint();
	}

	public void invokeRepaint()
	{
		_gui.repaint();		
	}
}

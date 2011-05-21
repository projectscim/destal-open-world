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
	private Chunk _chunk;
	
	public GamePanel (GUI gui, Player player, Chunk chunk)
	{
		super();
		_gui = gui;
		_player = player;
		_chunk = chunk;
		this.addMouseMotionListener(this);
		this.addMouseMotionListener(_player);
		this.addKeyListener(_player);
		_player.setContainer(this);
		setDoubleBuffered(true);
		System.out.println(_chunk.toString());
	}
	
	@Override
	public void paintComponent(Graphics g)
	{
		// Add what's to draw:

		for (int x = 0; x <= _gui.getWidth()/World.BLOCK_PAINTSIZE; x++)
		{
			for (int y = 0; y <= _gui.getHeight()/World.BLOCK_PAINTSIZE; y++)
			{
				_chunk.getBlocks()[x][y].paint(g);
			}
		}
		g.setColor(Color.BLACK);
		if (_lastMouseEvent != null)
		{
			g.setColor(Color.GREEN);
			g.fillOval(_lastMouseEvent.getX(), _lastMouseEvent.getY(), 5, 5);
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
	public void invokeRepaint(int x1, int y1, int x2, int y2)
	{
		_gui.repaint(x1, y1, x2, y2);		
	}
	
}

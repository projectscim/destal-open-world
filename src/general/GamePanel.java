package general;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Point2D;

import javax.swing.JPanel;

import entities.Player;

public class GamePanel extends JPanel implements MouseMotionListener
{
	private GUI _gui;
	private MouseEvent _lastMouseEvent;
	private Player _player;
	private Chunk _chunk;
	
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
	}
	
	public void setChunk(Chunk chunk)
	{
		_chunk = chunk;
	}
	
	@Override
	public void paintComponent(Graphics g)
	{
		// Add what's to draw:
		for (int x = 0; x < World.CHUNK_SIZE; x++)
		{
			for (int y = 0; y < World.CHUNK_SIZE; y++)
			{
				_chunk.getBlocks()[x][y].paint(g);
			}
		}
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

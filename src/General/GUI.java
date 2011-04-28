package General;

import java.io.*;
import java.util.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.lang.*;

import javax.swing.*;

public class GUI extends JFrame implements MouseMotionListener
{
	private TCPClient _client;
	private BufferStrategy _strategy;
	private GUIMode _guiMode;
	
	private MouseEvent _lastMouseEvent;
	
	private enum GUIMode {TITLE, MENU, GAME}
	
	private long _lastLoop;
	
	public GUI(int width, int height, TCPClient client)
	{
	    super("destal open world");
	    
		_guiMode = GUIMode.GAME;
		_client = client;
		
	    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    
	    JPanel panel = (JPanel) this.getContentPane();
	    panel.setPreferredSize(new Dimension(width,height));
	    panel.setLayout(null);
	    
	    this.setBounds(0,0,width,height);
	    
	    this.pack();
	    this.setVisible(true);
	    this.toFront();

	    this.addMouseMotionListener(this);
	    
	    // double buffer
	    this.createBufferStrategy(2);
		_strategy = this.getBufferStrategy();
	    _lastLoop = System.currentTimeMillis();
	}
	
	public void run()
	{
	    while(true)
	    {
	        if(System.currentTimeMillis() - _lastLoop >= 1000/50) // FPS
	        {
	            _lastLoop = System.currentTimeMillis();
	            this.paint(_strategy.getDrawGraphics());
	        }
	    }
	}
	
	public void paint(Graphics g)
	{
	    Graphics2D g2d = (Graphics2D)g;
	    g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
	    g2d.setColor(Color.WHITE);
		g2d.fillRect(0,0,this.getWidth(),this.getHeight());
		// Add what's to draw:
		switch (_guiMode)
		{
			case TITLE:
				this.paintTitleScreen(g2d);
				break;
			case MENU:
				this.paintMenu(g2d);
				break;
			case GAME:
				this.paintGame(g2d);
				break;
		}

		//
        g2d.dispose();
		_strategy.show();
    }
	
	private void disableCursor()
	{
        Cursor c = Toolkit.getDefaultToolkit().createCustomCursor(
                new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB),
                new Point(1, 1), "Custom Cursor");
 
        setCursor(c);
	}
	private void enableCursor()
	{
		Cursor c = Cursor.getDefaultCursor();
		setCursor(c);
	}
	
	private void paintTitleScreen(Graphics g)
	{
		g.setColor(Color.BLACK);
		g.drawString("destal open world rules!", 20, 60);
		enableCursor();
	}
	
	private void paintMenu(Graphics g)
	{
		// does not work yet
		//Button b = new Button("Start Game");
		//this.add(b);
	}
	private void paintGame(Graphics g)
	{
		disableCursor();
		g.setColor(Color.BLACK);
		if (_lastMouseEvent != null)
		g.drawOval(_lastMouseEvent.getX(), _lastMouseEvent.getY(), 3, 3);
		_client.getLocalCharacter().paint(g);
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

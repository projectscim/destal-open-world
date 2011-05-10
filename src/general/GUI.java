package general;

import java.io.*;
import java.util.*;
import java.awt.*;
import java.awt.event.*;
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
	private JPanel _panel;
	private Menu _menu;
	private Options _options;

	
	private MouseEvent _lastMouseEvent;
	
	protected enum GUIMode {TITLE, MENU, OPTIONS, GAME}
	
	private long _lastLoop;
	
	public GUI(int width, int height, TCPClient client)
	{
	    super("destal open world");
	    
		_client = client;
		
	    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    
	    _panel = new JPanel();
	    _panel.setPreferredSize(new Dimension(width,height));
	    _panel.setLayout(null);
	    
	    _menu = new Menu(this);
	    _menu.setPreferredSize(new Dimension(width,height));
	    _menu.setLayout(null);
	    
	    _options = new Options(this);
	    _options.setPreferredSize(new Dimension(width,height));
	    _options.setLayout(null);
	    
	    this.setGUIMode(GUIMode.MENU);
	    
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
	
	public void setGUIMode(GUIMode mode)
	{
		_guiMode = mode;
		switch (_guiMode)
		{
			case TITLE:
				break;
			case MENU:
				this.add(_menu);
				break;
			case OPTIONS:
				this.remove(_menu);
				this.add(_options);
				break;
			case GAME:
				this.remove(_menu);
				//_panel.addMouseMotionListener(_client.getLocalCharacter());
				this.add(_panel);
				break;
		}
	}
	
	public JPanel getGamePanel()
	{
		return _panel;
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
				break;
			case OPTIONS:
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


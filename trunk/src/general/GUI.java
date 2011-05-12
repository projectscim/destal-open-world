package general;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class GUI extends JFrame
{
	private Client _client;
	private BufferStrategy _strategy;
	private GUIMode _guiMode;
	private GamePanel _game;
	private MenuPanel _menu;
	private OptionPanel _options;
	
	private JPanel _curPanel; 

	
	private MouseEvent _lastMouseEvent;
	
	protected enum GUIMode {TITLE, MENU, OPTIONS, GAME}
	
	private long _lastLoop;
	
	public GUI(int width, int height, Client client)
	{
	    super("destal open world");
	    
		_client = client;
		
	    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    this.setBounds(0,0,width,height);
	    
	    _game = new GamePanel(this, _client.getLocalCharacter());
	    _game.setPreferredSize(new Dimension(width, height));
	    _game.setLayout(null);
	    
	    _menu = new MenuPanel(this);
	    _menu.setSize(new Dimension(width,height));
	    _menu.setLayout(null);
	    
	    _options = new OptionPanel(this);
	    _options.setSize(new Dimension(width,height));
	    _options.setLayout(null);
	    
	    this.setGUIMode(GUIMode.MENU);
	    
	    this.setVisible(true);
	    this.toFront();
	    
	    //this.addMouseMotionListener(_client.getLocalCharacter());

	    
	    // double buffer
	    this.createBufferStrategy(2);
		_strategy = this.getBufferStrategy();
	    _lastLoop = System.currentTimeMillis();
	}
	
	public void setGUIMode(GUIMode mode)
	{
		_guiMode = mode;
		if (_curPanel != null)
		{
			this.remove(_curPanel);
		}
		switch (_guiMode)
		{
			case TITLE:
				break;
			case MENU:
				_curPanel = _menu;
				break;
			case OPTIONS:
				_curPanel = _options;
				break;
			case GAME:
				_curPanel = _game;
				break;
		}
		if (_curPanel != null)
		{
			this.add(_curPanel);
			this.validate();
		}
		this.repaint();
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
		if (_guiMode == GUIMode.GAME)
		{
			_game.paint(g2d);
		}
		//
        g2d.dispose();
		_strategy.show();
    }
}


package general.ui;

import general.Client;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class GUI extends JFrame
{
	private Client _client;
	private GUIMode _guiMode;
	private GamePanel _game;
	private MenuPanel _menu;
	private OptionPanel _options;
	
	private JPanel _curPanel; 

	public enum GUIMode {TITLE, MENU, OPTIONS, GAME}
	
	public GUI(int width, int height, Client client)
	{
	    super("destal open world");
	    
		_client = client;
		
	    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    this.setBounds(0,0,width,height);
	    
	    _game = new GamePanel(this, _client.getLocalCharacter());
	    _game.setSize(new Dimension(width,height));
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
	    
	    setBackground(Color.RED);
	    //this.addMouseMotionListener(_client.getLocalCharacter());

	}
	
	public GamePanel getGame()
	{
		return _game;
	}
	
	public Client getClient()
	{
		return _client;
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
				disableCursor();
				break;
		}
		if (_curPanel != null)
		{
			this.add(_curPanel);
			this.invalidate();
			this.validate();
			_curPanel.requestFocusInWindow();
		}
		this.repaint();
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
}


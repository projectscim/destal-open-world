package destal.general.ui;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;
import javax.swing.JPanel;

import destal.general.Client;

public class GUI extends JFrame
{
	private Client _client;
	private JPanel[] _panels;
	private JPanel _curPanel;

	public enum GUIMode {TITLE, MENU, OPTIONS, GAME}
	
	public GUI(int width, int height, Client client)
	{
	    super("destal open world");
	    
		_client = client;
		_panels = new JPanel[GUIMode.values().length];
		
	    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    this.setBounds(0,0,width,height);
	    
	    _panels[GUIMode.MENU.ordinal()] = new MenuPanel(this);
	    _panels[GUIMode.GAME.ordinal()] = new GamePanel(this);
	    _panels[GUIMode.OPTIONS.ordinal()] = new OptionPanel(this);
	    
	    for(int i = 0; i < _panels.length; i++)
	    {
	    	if(_panels[i] != null)
	    	{
		    	_panels[i].setSize(new Dimension(width,height));
		    	_panels[i].setLayout(null);
	    	}
	    }
	    
	    this.setGUIMode(GUIMode.MENU);
	    
	    this.setVisible(true);
	    this.toFront();
	    
	    setBackground(Color.RED);
	    //this.addMouseMotionListener(_client.getLocalCharacter());
	}
	
	public Client getClient()
	{
		return _client;
	}
	
	public void setGUIMode(GUIMode mode)
	{
		if (mode == GUIMode.GAME)
		{
			disableCursor();
		}
		else
		{
			enableCursor();
		}
		JPanel panel = _panels[mode.ordinal()];
		if(panel != null)
		{
			if (_curPanel != null)
			{
				this.remove(_curPanel);
			}
			_curPanel = panel;
			
			this.add(_curPanel);
			this.invalidate();
			this.validate();
			_curPanel.requestFocusInWindow();
			this.repaint();
		}
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


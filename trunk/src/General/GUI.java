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

public class GUI extends Canvas
{
	private TCPClient _client;
	private BufferStrategy _strategy;
	
	private long _lastLoop;
	
	public GUI(int width, int height, TCPClient client)
	{
		_client = client;
		
	    JFrame container = new JFrame("destal open world");
	    container.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    
	    JPanel panel = (JPanel) container.getContentPane();
	    panel.setPreferredSize(new Dimension(width,height));
	    panel.setLayout(null);
	    
	    this.setBounds(0,0,width,height);
	    panel.add(this);
	    
	    container.pack();
	    container.setVisible(true);
	    container.toFront();
	    
        Cursor c = Toolkit.getDefaultToolkit().createCustomCursor(
                new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB),
                new Point(1, 1), "Custom Cursor");
 
        setCursor(c);

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
		g2d.drawString("destal open world rules!", 20, 60);
		_client.getLocalCharacter().paint(g);
		//
        g2d.dispose();
		_strategy.show();
    }
}

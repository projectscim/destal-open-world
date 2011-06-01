package destal.general.ui;

import java.awt.Dimension;
import java.io.OutputStream;

import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class ServerGUI extends JFrame
{
	private JTextArea _area;
	  
	public ServerGUI(int width, int height)
	{
		super();
		
	    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    this.setBounds(0,0,width,height);
		JPanel panel = (JPanel) this.getContentPane();
        panel.setPreferredSize(new Dimension(width,height));
        panel.setLayout(null);
		_area = new JTextArea ();
		_area.setBounds(10, 10, 200, 200);
	    panel.add(_area);
	    this.setVisible(true);
	    this.toFront();
	    
	}
	
	public void addMessage(String message)
	{
	    _area.append(message + "\n");
	}
}


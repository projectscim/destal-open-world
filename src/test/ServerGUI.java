package test;

import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class ServerGUI extends JFrame
{
	private JTextArea _area;
	
    public static void main(String[] args)
    {
    	new ServerGUI(800,600);
    }
    
	public ServerGUI(int width, int height)
	{
		super();
		
	    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    this.setBounds(0,0,width,height);
		JPanel panel = (JPanel) this.getContentPane();
        panel.setPreferredSize(new Dimension(width,height));
        panel.setLayout(null);
		JTextArea _area = new JTextArea ();
		_area.setBounds(10, 10, 200, 200);
	    panel.add(_area);
	    _area.append("blubb");
	    this.setVisible(true);
	    this.toFront();
	    
	}
}


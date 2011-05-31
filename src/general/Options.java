package general;


import java.awt.Button;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;

public class Options extends JPanel implements ActionListener
{
	private Button[] _button;
	private GUI _gui;
	
	public Options (GUI gui)
	{
		super();
		_gui = gui;
		_button = new Button[]{	new Button ("Pointless Button"),
								new Button ("Back") };
		for (int i = 0; i < _button.length; i++)
		{
			_button[i].setLocation(100, (i+1) * 20);
			_button[i].setSize(100, 20);
			this.add(_button[i]);
			_button[i].addActionListener(this);
		}
	}
    @Override
    public void actionPerformed(ActionEvent e)
    {
    	if (e.getActionCommand().equals("Pointless Button"))
    	{
    	}
    	if (e.getActionCommand().equals("Back"))
    	{
    		_gui.setGUIMode (GUI.GUIMode.MENU);
    	}
    }
}

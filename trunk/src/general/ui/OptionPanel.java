package general.ui;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

public class OptionPanel extends JPanel implements ActionListener
{
	private JButton[] _button;
	private GUI _gui;
	
	public OptionPanel (GUI gui)
	{
		super();
		_gui = gui;
		_button = new JButton[]{	new JButton ("Pointless Button"),
								new JButton ("Back") };
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

package general;


import java.io.*;
import java.util.*;
import java.awt.*;
import java.awt.event.*;
import java.lang.*;

import javax.swing.*;

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
			_button[i].setLocation(10, i * 10);
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

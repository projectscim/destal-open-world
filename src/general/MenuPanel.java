package general;


import java.io.*;
import java.util.*;
import java.awt.*;
import java.awt.event.*;
import java.lang.*;

import javax.swing.*;

public class MenuPanel extends JPanel implements ActionListener
{
	private Button[] _button;
	private GUI _gui;
	
	public MenuPanel (GUI gui)
	{
		super();
		_gui = gui;
		_button = new Button[]{	new Button ("Start Game"),
								new Button ("Options"),
								new Button ("Exit") };
		for (int i = 0; i < _button.length; i++)
		{
			_button[i].setLocation((i+1) * 100, 100);
			_button[i].setSize(100, 20);
			this.add(_button[i]);
			_button[i].addActionListener(this);
		}
	}
    @Override
    public void actionPerformed(ActionEvent e)
    {
    	if (e.getActionCommand().equals("Start Game"))
    	{
    		_gui.setGUIMode (GUI.GUIMode.GAME);
    	}
    	if (e.getActionCommand().equals("Options"))
    	{
    		_gui.setGUIMode (GUI.GUIMode.OPTIONS);
    	}
    	if (e.getActionCommand().equals("Exit"))
    	{
    		System.exit(0);
    	}
    }
}

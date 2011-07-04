/*******************************************************************************
 * destal open world, an open source java multiplayer game
 * Copyright (C) 2011 Alexander Belke, Dennis Sternberg, Steffen Schneider
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/
package destal.client.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class MenuPanel extends JPanel implements ActionListener
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 4586123693763691402L;
	private JButton[] _button;
	private JTextField[] _textField;
	private GUI _gui;
	
	public MenuPanel (GUI gui)
	{
		super();
		_gui = gui;
		
		_textField = new JTextField[] { new JTextField("localhost"),
										new JTextField("username") };
		for (int i = 0; i < _textField.length; i++)
		{
			_textField[i].setLocation(i * 300 + 50, 50);
			_textField[i].setSize(150, 20);
			this.add(_textField[i]);
		}

		_button = new JButton[]{	new JButton ("Start Game"),
								new JButton ("Options"),
								new JButton ("Exit") };
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
    		_gui.getClient().connect(_textField[0].getText(), _textField[1].getText());
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

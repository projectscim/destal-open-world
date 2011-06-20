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
package destal.general.ui;


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

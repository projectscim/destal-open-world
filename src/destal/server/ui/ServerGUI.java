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
package destal.server.ui;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import destal.server.net.ClientConnection;

public class ServerGUI extends JFrame implements ActionListener
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 722313357688165207L;
	private JTextArea _area;
	private JList _clientList;
	private JButton _kickButton;
	private JScrollPane _scroll;
	
	public ServerGUI(int width, int height)
	{
		super();
		
	    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    this.setBounds(0,0,width,height);
		JPanel panel = (JPanel) this.getContentPane();
        panel.setPreferredSize(new Dimension(width,height));
        panel.setLayout(null);
        
		_area = new JTextArea();
		_area.setEditable(false);
		
		_scroll = new JScrollPane(_area);
		_scroll.setBounds(10, 10, width/3*2-20, height-90);
		
		_clientList = new JList();
		_clientList.setBounds(20+width/3*2-20, 10, width/3-30, height-90);
		
		_kickButton = new JButton("Kick");
		_kickButton.setBounds(20+width/3*2-20, height-70, 100, 20);
		_kickButton.addActionListener(this);
		
	    panel.add(_scroll);
	    panel.add(_clientList);
	    panel.add(_kickButton);
	    
	    this.setVisible(true);
	    this.toFront();
	    
		OutputStream output = new OutputStream()
		{
			@Override
			public void write(int b) throws IOException
			{
				addMessage(String.valueOf((char) b));
			}
			
			@Override
			public void write(byte[] b, int off, int len)
			{
				addMessage(new String(b, off, len));
			}
		};
	    
	    System.setOut(new PrintStream(output, true));
	}
	
	private void addMessage(String str)
	{
		_area.append(str);
		_area.setCaretPosition(_area.getDocument().getLength());
	}
	
	public void setClientList(Vector<?> data)
	{
		_clientList.setListData(data);
	}
	
    @Override
    public void actionPerformed(ActionEvent e)
    {
    	if (e.getActionCommand().equals("Kick"))
    	{
    		ClientConnection clCon = (ClientConnection)_clientList.getSelectedValue();
    		if(clCon != null)
    		{
    			clCon.drop();
    		}
    	}
    }
}


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
package destal.general.gp;

import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JFrame;
import javax.swing.JPanel;

import destal.entity.character.HumanPlayer;
import destal.general.net.server.ClientConnection;

public class Inventory extends JPanel
{
	private HumanPlayer _player;
	
	public Inventory (int width, int height)
	{
		super();

		this.setBounds(0,0,width,height);
		this.setPreferredSize(new Dimension(width,height));
		this.setLayout(null);
    
		this.setVisible(true);
	}
	public void paintComponent (Graphics g)
	{
		
	}
}
	
    
    
	
	
	
	
    

    
	





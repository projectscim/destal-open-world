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

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.ImageObserver;

import javax.swing.JPanel;

import destal.entity.character.HumanPlayer;
import destal.entity.data.Values;
import destal.util.DataContainer;

public class Inventory extends JPanel
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 8289679756504049316L;
	private HumanPlayer _player;
	
	public Inventory (int width, int height, HumanPlayer player)
	{
		super();

		_player = player;
		this.setBounds(0,0,width,height);
    
		this.setVisible(true);
		setDoubleBuffered(true);
	}
	
	@Override
	public void paintComponent (Graphics g)
	{
		super.paintComponent(g);
		Rectangle r = this.getBounds();
		//System.out.println(r.toString());
		g.setColor(Color.LIGHT_GRAY);
		g.fillRect(r.x, r.y, r.width, r.height);
		g.setColor(Color.BLACK);
		g.drawImage(DataContainer.getTexture(Values.BLOCK_WOOD), 10, 15, null);
		g.drawString(""+_player.getItemQuantity(Values.ITEM_WOOD), 20, 15);
		g.drawImage(DataContainer.getTexture(Values.BLOCK_STONE), 60, 15, null);
		g.drawString(""+_player.getItemQuantity(Values.ITEM_STONE), 70, 15);
		g.drawImage(DataContainer.getTexture(Values.BLOCK_SAND), 110, 15, null);
		g.drawString(""+_player.getItemQuantity(Values.ITEM_SAND), 120, 15);
		
		//g.fillRect(r.x, r.y, r.width, r.height);
	}
	
	
}
	
    
    
	
	
	
	
    

    
	





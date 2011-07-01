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

public class BuildingMenu extends JPanel
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 7655907947328548564L;
	private HumanPlayer _player;
	
	public BuildingMenu (int x, int y, int width, int height)
	{
		super();
		this.setBounds(x,y,width,height);
    
		this.setVisible(true);
	}
	
	@Override
	public void resize(int width, int height)
	{
		//this.setBounds(0,0,width, height);
	}
	
	@Override
	public void paintComponent (Graphics g)
	{
		Rectangle r = this.getBounds();
		//System.out.println(r.toString());
		g.setColor(Color.LIGHT_GRAY);
		g.fillRect(r.x, r.y, r.width, r.height);
		g.setColor(Color.BLACK);

	}
	
	
}
	
    
    
    
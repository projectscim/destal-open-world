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
package destal.server.event.listener;

import destal.server.event.PacketReceivedServerEvent;


public interface PacketReceivedServerListener
{
	public void clientConnected(PacketReceivedServerEvent e);
	
	public void clientDisconnected(PacketReceivedServerEvent e);
	
	public void clientRequestEnter(PacketReceivedServerEvent e);
	
	public void clientRequestChunk(PacketReceivedServerEvent e);
	
	public void clientBuildHouse(PacketReceivedServerEvent e);
	
	public void clientMineBlock(PacketReceivedServerEvent e);

	public void clientPlayerRequestMove(PacketReceivedServerEvent e);
}

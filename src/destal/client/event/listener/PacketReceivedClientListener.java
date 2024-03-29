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
package destal.client.event.listener;

import destal.client.event.PacketReceivedClientEvent;

public interface PacketReceivedClientListener
{
	public void serverConnected(PacketReceivedClientEvent e);
	
	public void serverDisconnected(PacketReceivedClientEvent e);
	
	public void serverResponseEnter(PacketReceivedClientEvent e);
	
	public void serverResponseChunk(PacketReceivedClientEvent e);
	
	// TODO: replace by snapshot system?
	public void serverResponsePlayerPositions(PacketReceivedClientEvent e);
	// TODO: replace by snapshot system?
	public void serverNewClientConnected(PacketReceivedClientEvent e);
}

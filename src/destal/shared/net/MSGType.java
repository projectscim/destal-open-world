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
package destal.shared.net;

public class MSGType
{
	// Note: Client messages 0x0...
	//		 Server messages 0x1...
	
	public final static int PROTOCOL_VERSION = 3;
	
	// TODO: update
	/*
	 * == Protocol ==
	 * 
	 * -- Connecting --
	 * Client -> Server (MSG_CL_INIT: PROTOCOL_VERSION, name)
	 * Server -> Client (MSG_SV_INIT: accepted?, MOTD, ID)
	 * Client -> Server (MSG_CL_REQUEST_ENTER)
	 * Server -> Client (MSG_SV_RESPONSE_ENTER: xPos (Player), yPos (Player), Chunk/Array, Client list)
	 * TODO: replace by snapshot system?
	 * Server -> Client (all) (MSG_SV_NEW_CLIENT_CONNECTED: ID, xPos, yPos)
	 * 
	 * -- Chunk Request --
	 * Client -> Server (MSG_CL_REQUEST_CHUNK: Point/Array)
	 * Server -> Client (MSG_SV_RESPONSE_CHUNK: Chunk) // TODO: all in one packet?
	 * 
	 * TODO: replace by snapshot system?
	 * -- Player Position --
	 * Client -> Server (MSG_CL_PLAYER_INPUT: xPos, yPos)
	 * Server -> Client (all) (MSG_SV_RESPONSE_PLAYER_POSITIONS ID, xPos, yPos)
	 */
	
	public final static byte MSG_CL_INIT = 0x01;
	public final static byte MSG_CL_REQUEST_ENTER = 0x02;
	public final static byte MSG_CL_REQUEST_CHUNK = 0x03;
	public final static byte MSG_CL_PLAYER_INPUT = 0x04;
	public final static byte MSG_CL_BUILD_HOUSE = 0x05;
	
	public final static byte MSG_SV_INIT = 0x11;
	public final static byte MSG_SV_RESPONSE_ENTER = 0x12;
	public final static byte MSG_SV_RESPONSE_CHUNK = 0x13;
	public final static byte MSG_SV_RESPONSE_PLAYER_POSITIONS = 0x14;
	public final static byte MSG_SV_NEW_CLIENT_CONNECTED = 0x15;
	public final static byte MSG_SV_CLIENT_LIST = 0x16;
}

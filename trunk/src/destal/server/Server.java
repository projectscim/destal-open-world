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
package destal.server;

import destal.general.net.server.NetworkServer;
import destal.general.ui.ServerGUI;
import destal.util.Controller;

/**
 * The main class for the destal server application
 * @author Alex Belke, Dennis Sternberg, Steffen Schneider
 */
public class Server
{
	private Controller _controller;
	private NetworkServer _networkServer;
	private ServerGUI _serverGui;
	
	/**
	 * Creates a new server
	 * @param gui true to enable the server GUI
	 */
	public Server(boolean gui)
	{
		_networkServer = new NetworkServer(this);
		_controller = new Controller(_networkServer);
		if(gui)
		{
			_serverGui = new ServerGUI(800, 400);
		}
	}
	
	/**
	 * Returns the server GUI
	 * @return The server GUI
	 */
	public ServerGUI getServerGUI()
	{
		return _serverGui;
	}
	
	/**
	 * Returns the network controller of this server
	 * @return The network controller of this server
	 */
	public Controller getController()
	{
		return _controller;
	}
	
	/**
	 * Runs the server
	 */
	public void run()
	{
		(new Thread(_networkServer)).start();
		
		_controller.loadWorld("houses");
	}
	
	public static void main(String[] args)
	{
		boolean gui = true;
		for(String arg : args)
		{
			if(arg.equals("--nogui"))
			{
				gui = false;
			}
		}
		
		(new Server(gui)).run();
	}
}

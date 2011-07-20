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
package destal.server.net;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;

import destal.server.Server;
import destal.server.event.PacketReceivedServerEvent;
import destal.server.event.listener.PacketReceivedServerListener;
import destal.shared.net.Packet;

public class NetworkServer implements Runnable, PacketReceivedServerListener
{
	private Server _server;
	private ServerSocket _serverSocket;
	private Vector<ClientConnection> _clientConnections;
	
	/**
	 * Creates a new network server
	 * @param server
	 */
	public NetworkServer(Server server)
	{
		_server = server;
		_clientConnections = new Vector<ClientConnection>();
	}
	
	@Override
	public void run()
	{
		try 
		{
			_serverSocket = new ServerSocket(4185);
		}
		catch (IOException e)
		{
			e.printStackTrace();
			System.out.println("couldn't create socket");
			return;
		}
		while(true)
        {
			try
			{
				Socket s = _serverSocket.accept();
				ClientConnection clCon = new ClientConnection(s, getFreeId());
				clCon.addPacketReceivedServerListener(this);
				clCon.addPacketReceivedServerListener(_server.getController());
				_clientConnections.add(clCon);
	            new Thread(clCon).start();
	            System.out.println("new client: " + (_clientConnections.size()-1));
			}
			catch (Exception e)
			{
				System.out.println("exception occured (client listener)");
				e.printStackTrace();
				break;
			}
        }
	}
	
	/**
	 * Returns the next free client ID
	 * @return The next free client ID
	 */
	private int getFreeId()
	{
		int id = 0;
		int prevId;
		do
		{
			prevId = id;
			for(ClientConnection clCon : _clientConnections)
			{
				if(clCon.getID() == id)
				{
					id++;
					break;
				}
			}
		} while(prevId != id);
		return id;
	}
	/**
	 * Sends a packet to the client with the specified ID
	 * @param ID The client's ID
	 * @param data The packet to be sent
	 */
	public void send(int ID, Packet data)
	{
		// TODO remove when fully changed all send(-1, ...) to broadcast
		if (ID == -1)
			broadcastPacket(data);
		for(ClientConnection clientCon : _clientConnections)
		{
			if(clientCon.getID() == ID)
			{
				clientCon.send(data);
			}
		}
	}
	/**
	 * Sends a packet to all connected clients
	 * @param data The packet to be sent
	 */
	public void broadcastPacket(Packet data)
	{
		for(ClientConnection clientCon : _clientConnections)
		{
			clientCon.send(data);
		}
	}

	@Override
	public void clientConnected(PacketReceivedServerEvent e)
	{		
		// check if new client's name is individual
		for (ClientConnection c : _clientConnections)
		{
			if (e.getClient().getName().equalsIgnoreCase(c.getName()) &&
				e.getClient().getID() != c.getID())
			{
				e.getClient().drop();
			}
		}
		System.out.println("client connected: '" + e.getClient() + "'");
		
		if(_server.getServerGUI() != null)
		{
			_server.getServerGUI().setClientList(_clientConnections);
		}
	}

	@Override
	public void clientDisconnected(PacketReceivedServerEvent e)
	{
		System.out.println("client left: '" + e.getClient() + "'");
		_clientConnections.remove(e.getClient());
		
		if(_server.getServerGUI() != null)
		{
			_server.getServerGUI().setClientList(_clientConnections);
		}
	}
	
	@Override
	public void clientPlayerInput(PacketReceivedServerEvent e) { }
	@Override
	public void clientRequestEnter(PacketReceivedServerEvent e) { }
	@Override
	public void clientRequestChunk(PacketReceivedServerEvent e) { }
	@Override
	public void clientBuildHouse(PacketReceivedServerEvent e) { }

	@Override
	public void clientMineBlock(PacketReceivedServerEvent e) { }
}

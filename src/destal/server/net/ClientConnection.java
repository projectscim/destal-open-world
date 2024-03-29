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

import java.awt.Point;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

import destal.server.event.PacketReceivedServerEvent;
import destal.server.event.listener.PacketReceivedServerListener;
import destal.shared.net.MSGType;
import destal.shared.net.Packet;
import destal.shared.world.WorldPoint;
/**
 * Represents a connection between the server and a client
 * (server side)
 * @author Alex Belke, Dennis Sternberg, Steffen Schneider
 *
 */
public class ClientConnection implements Runnable
{
	private Socket _socket;
	private ObjectInputStream _input;
	private ObjectOutputStream _output;
	private ArrayList<PacketReceivedServerListener> _packetReceivedServerListener;
	
	private int _id;
	private String _name;
	/**
	 * Creates a new client connection
	 * @param s The client's network socket
	 * @param id The client ID
	 * @throws Exception
	 */
	public ClientConnection(Socket s, int id) throws Exception
	{
		_socket = s;
		_id = id;
		_input = new ObjectInputStream(_socket.getInputStream());
		_output = new ObjectOutputStream(_socket.getOutputStream());
		_packetReceivedServerListener = new ArrayList<PacketReceivedServerListener>();
		_name = "< connecting >";
	}
	
	@Override
	public void run()
	{
		System.out.println("thread started");
		try
        {
			boolean error = false;
			while(!error)
			{
				Packet p = recv();
				byte type = p.getType();
				//System.out.println("received packet from client: '" + this + "' (type: " + type + ")");
				// TODO use else if ?!
				if(type == MSGType.MSG_CL_INIT)
				{
					if((Integer)p.get() != MSGType.PROTOCOL_VERSION)
					{
						error = true;
						System.out.println("error: wrong version... dropping client");
						Packet r = new Packet(MSGType.MSG_SV_INIT);
						r.set(false);
						send(r);
					}
					else
					{
						_name = (String)p.get();
						for (PacketReceivedServerListener l : _packetReceivedServerListener)
						{
							l.clientConnected(new PacketReceivedServerEvent(this));
						}
					}
				}
				if(type == MSGType.MSG_CL_REQUEST_ENTER)
				{
					for (PacketReceivedServerListener l : _packetReceivedServerListener)
					{
						l.clientRequestEnter(new PacketReceivedServerEvent(this));
					}
				}
				if(type == MSGType.MSG_CL_REQUEST_CHUNK)
				{
					PacketReceivedServerEvent e = new PacketReceivedServerEvent(this);
					e.setPoints((Point[])p.get());
					for (PacketReceivedServerListener l : _packetReceivedServerListener)
					{
						l.clientRequestChunk(e);
					}
				}
				if (type == MSGType.MSG_CL_BUILD_HOUSE)
				{
					PacketReceivedServerEvent e = new PacketReceivedServerEvent(this);
					e.setPoint(new WorldPoint((Double)p.get(), (Double)p.get()));
					e.setBuildingType((Integer)p.get());
					for (PacketReceivedServerListener l : _packetReceivedServerListener)
					{
						l.clientBuildHouse(e);
					}
				}
				if (type == MSGType.MSG_CL_MINE_BLOCK)
				{
					PacketReceivedServerEvent e = new PacketReceivedServerEvent(this);
					e.setPoint(new WorldPoint((Double)p.get(), (Double)p.get()));
					for (PacketReceivedServerListener l : _packetReceivedServerListener)
					{
						l.clientMineBlock(e);
					}
				}
				if (type == MSGType.MSG_CL_REQUEST_MOVE)
				{
					PacketReceivedServerEvent e = new PacketReceivedServerEvent(this);
					e.setClientID((Integer)p.get());
					e.setPoint(new WorldPoint((Double)p.get(), (Double)p.get()));
					e.setSndPoint(new WorldPoint((Double)p.get(), (Double)p.get()));
					for (PacketReceivedServerListener l : _packetReceivedServerListener)
					{
						l.clientPlayerRequestMove(e);
					}
				}
			}
			_socket.close();
        }
		catch(Exception e)
		{
			System.out.println("lost client: '" + this + "'");
			e.printStackTrace();
		}
		for (PacketReceivedServerListener l : _packetReceivedServerListener)
		{
			l.clientDisconnected(new PacketReceivedServerEvent(this));
		}
	}
	/**
	 * Closes the connection to the client represented by this object
	 */
	public void drop()
	{
		System.out.println("dropping client: '" + this + "'");
		try
        {
			_socket.close();
        }
		catch(Exception e)
		{
			System.out.println("exception occured (drop client)");
		}
	}
	
	private Packet recv() throws Exception
	{
		return (Packet)_input.readObject();
	}
	/**
	 * Sends the specified packet
	 * @param data The packet
	 */
	public void send(Packet data)
	{
		try
        {
			_output.writeObject(data);
			_output.flush();
		}
		catch(IOException e)
		{
			System.out.println("exception occured: couldn't send the packet");
		}
	}
	/**
	 * Returns the ID of the client represented by this object
	 * @return The ID
	 */
	public int getID()
	{
		return _id;
	}
	/**
	 * Returns the name of the client represented by this object
	 * @return The client's username
	 */
	public String getName()
	{
		return _name;
	}
	
	@Override
	public String toString()
	{
		return getName() + " (" + getID() + ")";
	}
	
	public void addPacketReceivedServerListener(PacketReceivedServerListener listener)
	{
		_packetReceivedServerListener.add(listener);
	}
}

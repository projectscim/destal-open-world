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
package destal.general.net.server;

import java.awt.Point;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

import destal.event.events.net.server.PacketReceivedServerEvent;
import destal.event.listener.PacketReceivedServerListener;
import destal.general.net.MSGType;
import destal.general.net.Packet;
import destal.general.world.WorldPoint;

public class ClientConnection implements Runnable
{
	private Socket _socket;
	private ObjectInputStream _input;
	private ObjectOutputStream _output;
	private ArrayList<PacketReceivedServerListener> _packetReceivedServerListener;
	
	private int _id;
	private String _name;
	
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
				if (type == MSGType.MSG_CL_PLAYER_INPUT)
				{
					PacketReceivedServerEvent e = new PacketReceivedServerEvent(this);
					e.setPoint((Double)p.get(),(Double)p.get());
					for (PacketReceivedServerListener l : _packetReceivedServerListener)
					{
						l.clientPlayerInput(e);
					}
				}
				if (type == MSGType.MSG_CL_BUILD_HOUSE)
				{
					PacketReceivedServerEvent e = new PacketReceivedServerEvent(this);
					e.setPoint(new WorldPoint((Double)p.get(), (Double)p.get()));
					for (PacketReceivedServerListener l : _packetReceivedServerListener)
					{
						l.clientBuildHouse(e);
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
	
	public int getID()
	{
		return _id;
	}
	
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

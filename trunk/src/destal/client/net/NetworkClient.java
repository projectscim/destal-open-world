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
package destal.client.net;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

import destal.client.event.PacketReceivedClientEvent;
import destal.client.event.listener.PacketReceivedClientListener;
import destal.shared.entity.character.Player;
import destal.shared.net.MSGType;
import destal.shared.net.Packet;
import destal.shared.world.Chunk;
/**
 * Represents a connection between the server and a client
 * (client side)
 * @author Alex Belke, Dennis Sternberg, Steffen Schneider
 *
 */
public class NetworkClient implements Runnable
{
	private Socket _socket;
	private ObjectInputStream _input;
	private ObjectOutputStream _output;
	private ArrayList<PacketReceivedClientListener> _packetReceivedClientListener;
	
	private State _state;
	
	private enum State { NONE, CONNECTING, CHECKED, READY };
	
	public NetworkClient()
	{
		_packetReceivedClientListener = new ArrayList<PacketReceivedClientListener>();
		_state = State.NONE;
	}

	@Override
	public void run()
	{
		System.out.println("netclient started... listening...");
		try
		{
			boolean error = false;
			while(!error)
			{
				Packet r = recv();
				byte type = r.getType();
				//System.out.println("received packet (type: " + type + ")");
				
				if(type == MSGType.MSG_SV_INIT && _state == State.CONNECTING)
				{
					if(!(Boolean)r.get())
					{
						System.out.println("wrong version");
						error = true;
					}
					else
					{
						send(new Packet(MSGType.MSG_CL_REQUEST_ENTER));
						_state = State.CHECKED;
						
						PacketReceivedClientEvent e = new PacketReceivedClientEvent(this);
						e.setMOTD((String)r.get());
						e.setClientID((Integer)r.get());
						e.setClientList((Player[])r.get());
						System.out.println("Received client id: " + e.getClientID());
						for (PacketReceivedClientListener l : _packetReceivedClientListener)
						{
							l.serverConnected(e);
						}
					}
				}
				if (type == MSGType.MSG_SV_RESPONSE_ENTER && _state == State.CHECKED)
				{
					_state = State.READY;
					
					PacketReceivedClientEvent e = new PacketReceivedClientEvent(this);
					e.setPoint((Double)r.get(), (Double)r.get());
					e.setChunkBuffer((Chunk[])r.get());
					for (PacketReceivedClientListener l : _packetReceivedClientListener)
					{
						l.serverResponseEnter(e);
					}
				}
				if(_state == State.READY)
				{
					if (type == MSGType.MSG_SV_RESPONSE_CHUNK)
					{
						PacketReceivedClientEvent e = new PacketReceivedClientEvent(this);
						e.setChunk((Chunk)r.get());
						for (PacketReceivedClientListener l : _packetReceivedClientListener)
						{
							l.serverResponseChunk(e);
						}
					}
					else if (type == MSGType.MSG_SV_RESPONSE_PLAYER_POSITIONS)
					{
						PacketReceivedClientEvent e = new PacketReceivedClientEvent(this);
						int id = (Integer)r.get();
						double x = (Double)r.get();
						double y = (Double)r.get();
						e.setClientID(id);
						e.setPoint(x, y);
						for (PacketReceivedClientListener l : _packetReceivedClientListener)
						{
							l.serverResponsePlayerPositions(e);
						}
					}
					else if (type == MSGType.MSG_SV_NEW_CLIENT_CONNECTED)
					{
						PacketReceivedClientEvent e = new PacketReceivedClientEvent(this);
						int id = (Integer)r.get();
						double x = (Double)r.get();
						double y = (Double)r.get();
						e.setClientID(id);
						e.setPoint(x, y);
						for (PacketReceivedClientListener l : _packetReceivedClientListener)
						{
							l.serverNewClientConnected(e);
						}
					}
				}
			}
			_socket.close();
		}
		catch(Exception e)
		{
			System.out.println("lost connection");
			e.printStackTrace();
		}
		for (PacketReceivedClientListener l : _packetReceivedClientListener)
		{
			l.serverDisconnected(new PacketReceivedClientEvent(this));
		}
	}
	
	public void disconnect()
	{
		System.out.println("disconnecting from server");
		try
		{
			_socket.close();
		}
		catch (IOException e)
		{
			System.out.println("exception occured (disconnect)");
		}
	}
	
	public void connect(String address, String username)
	{
		System.out.println("connecting to '" + address + "'");
		try
		{
			_state = State.CONNECTING;
			_socket = new Socket(address, 4185);
			_output = new ObjectOutputStream(_socket.getOutputStream());
			_input = new ObjectInputStream(_socket.getInputStream());
			System.out.println("connected to server");
			
			Packet p = new Packet(MSGType.MSG_CL_INIT);
			p.set(MSGType.PROTOCOL_VERSION);
			p.set(username);
			send(p);
			
			(new Thread(this)).start();
		}
		catch(Exception e)
		{
			System.out.println("can't connect to server");
			//e.printStackTrace();
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
	
	public void addPacketReceivedClientListener(PacketReceivedClientListener listener)
	{
		_packetReceivedClientListener.add(listener);
	}
}

package destal.general;

import java.awt.Point;
import java.util.ArrayList;

import destal.entities.characters.Player;
import destal.general.event.events.ClientConnectedEvent;
import destal.general.event.events.PacketReceivedServerEvent;
import destal.general.event.listener.ClientConnectedListener;
import destal.general.event.listener.PacketRecievedServerListener;
import destal.general.net.MSGType;
import destal.general.net.Packet;
import destal.general.world.Chunk;
import destal.general.world.World;
import destal.general.world.WorldPoint;

public class Controller implements PacketRecievedServerListener, ClientConnectedListener
{
	private World _world;
	private ArrayList<Player> _characters;
	
	public Controller()
	{
	}
	
	public void loadWorld(String name)
	{
		_world = new World(name);
		_characters = new ArrayList<Player>();
	}
	
	@Override
	public void clientRequestEnter(PacketReceivedServerEvent e)
	{
		System.out.println("sending start info to client: '" + e.getClient() + "'");
		// TODO: change default position
		WorldPoint pos = new WorldPoint(40, 40);
		Point chunkPos = pos.getChunkLocation();
		
		Chunk[] buffer = new Chunk[9];
		int i = 0;
		for (int x = chunkPos.x-1; x <= chunkPos.x+1; x++)
		{
			for (int y = chunkPos.y-1; y <= chunkPos.y+1; y++)
			{
				buffer[i++] = _world.getLevels()[0].getChunk(x, y);
			}
		}
		
		Packet p = new Packet(MSGType.MSG_SV_RESPONSE_ENTER);
		p.set(pos.getX());
		p.set(pos.getY());
		p.set(buffer);
		e.getClient().send(p);
	}

	@Override
	public void clientRequestChunk(PacketReceivedServerEvent e)
	{		
		System.out.println("sending chunk to client: '" + e.getClient() + "'");
		
		Packet p = new Packet(MSGType.MSG_SV_RESPONSE_CHUNK);
		p.set(_world.getLevels()[0].getChunk((int)e.getPoint().getX(), (int)e.getPoint().getY()));
		e.getClient().send(p);
	}
	
	@Override
	public void clientPlayerPosition(PacketReceivedServerEvent e)
	{
		Player c = _characters.get(e.getClientID());
		c.setLocation(e.getPoint());
		System.out.println("Player " + e.getClientID() + " changed location to: " + e.getPoint().toString());
	}

	@Override
	public void clientConnected(PacketReceivedServerEvent e) { }
	@Override
	public void clientDisconnected(PacketReceivedServerEvent e) { }

	@Override
	public void clientConnected(ClientConnectedEvent e)
	{
		Player p = new Player();
		p.setLocation(0, 0);
		_characters.add(p);
	}
}

package destal.general.event.events;

import java.util.EventObject;

import destal.entities.characters.Player;


public class ClientConnectedEvent extends EventObject
{
	public Player _player;
	
	public Player getPlayer()
	{
		return _player;
	}
	public void setPlayer(Player player)
	{
		_player = player;
	}
	
	public ClientConnectedEvent(Object source)
	{
		super(source);
	}
}

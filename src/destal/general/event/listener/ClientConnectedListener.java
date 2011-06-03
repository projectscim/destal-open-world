package destal.general.event.listener;

import destal.general.event.events.ClientConnectedEvent;

public interface ClientConnectedListener
{
	void clientConnected(ClientConnectedEvent e);
}

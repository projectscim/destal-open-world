package destal.general.event.listener;

import destal.general.event.events.PacketReceivedServerEvent;

public interface PacketRecievedServerListener
{
	public void clientConnected(PacketReceivedServerEvent e);
	
	public void clientDisconnected(PacketReceivedServerEvent e);
	
	public void clientRequestEnter(PacketReceivedServerEvent e);
	
	public void clientRequestChunk(PacketReceivedServerEvent e);
	
	public void clientPlayerPosition(PacketReceivedServerEvent e);
}
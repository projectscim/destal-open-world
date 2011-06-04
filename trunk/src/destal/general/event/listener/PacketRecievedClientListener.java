package destal.general.event.listener;

import destal.general.event.events.PacketReceivedClientEvent;

public interface PacketRecievedClientListener
{
	public void serverConnected(PacketReceivedClientEvent e);
	
	public void serverDisconnected(PacketReceivedClientEvent e);
	
	public void serverResponseEnter(PacketReceivedClientEvent e);
	
	public void serverResponseChunk(PacketReceivedClientEvent e);
	
	public void serverResponsePlayerPositions(PacketReceivedClientEvent e);
	
	public void serverNewClientConnected(PacketReceivedClientEvent e);
}

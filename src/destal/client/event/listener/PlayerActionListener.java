package destal.client.event.listener;

import destal.client.event.PlayerActionEvent;

public interface PlayerActionListener
{
	/**
	 * Invoked when the player affects the world in some way
	 * @param e
	 */
	public void playerAction(PlayerActionEvent e);
	
	public void playerBuildHouse(PlayerActionEvent e);
	
	public void playerBlockClicked(PlayerActionEvent e);
	
	public void playerRequestMove(PlayerActionEvent e);
}

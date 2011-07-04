package destal.shared.event.listener;

import destal.shared.event.player.PlayerActionEvent;

public interface PlayerActionListener
{
	/**
	 * Invoked when the player affects the world in some way
	 * @param e
	 */
	public void playerAction(PlayerActionEvent e);
	
	public void playerBuildHouse(PlayerActionEvent e);
}

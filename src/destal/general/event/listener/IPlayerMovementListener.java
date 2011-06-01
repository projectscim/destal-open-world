package destal.general.event.listener;

import destal.general.event.events.PlayerMovementEvent;

/**
 * 
 * @author Steffen
 *
 */
public interface IPlayerMovementListener
{
	/**
	 * Invoked when a player moves
	 * @param e
	 */
	public void playerMoved(PlayerMovementEvent e);
}

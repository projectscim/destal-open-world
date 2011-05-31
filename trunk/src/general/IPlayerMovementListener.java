package general;

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

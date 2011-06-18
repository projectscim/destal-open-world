package destal.general;

import destal.general.net.server.NetworkServer;
import destal.general.ui.ServerGUI;

/**
 * The main class for the destal server application
 * @author Alex Belke, Dennis Sternberg, Steffen Schneider
 */
public class Server
{
	private Controller _controller;
	private NetworkServer _networkServer;
	private ServerGUI _serverGui;
	
	/**
	 * Creates a new server
	 * @param gui true to enable the server GUI
	 */
	public Server(boolean gui)
	{
		_controller = new Controller();
		_networkServer = new NetworkServer(this);
		if(gui)
		{
			_serverGui = new ServerGUI(800, 400);
		}
	}
	
	/**
	 * Returns the server GUI
	 * @return The server GUI
	 */
	public ServerGUI getServerGUI()
	{
		return _serverGui;
	}
	
	/**
	 * Returns the network controller of this server
	 * @return The network controller of this server
	 */
	public Controller getController()
	{
		return _controller;
	}
	
	/**
	 * Runs the server
	 */
	public void run()
	{
		(new Thread(_networkServer)).start();
		
		_controller.loadWorld("testhouses");
	}
	
	public static void main(String[] args)
	{
		boolean gui = true;
		for(String arg : args)
		{
			if(arg.equals("--nogui"))
			{
				gui = false;
			}
		}
		
		(new Server(gui)).run();
	}
}
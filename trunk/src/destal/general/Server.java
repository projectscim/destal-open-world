package destal.general;

import destal.general.net.server.NetworkServer;
import destal.general.ui.ServerGUI;

public class Server
{
	private Controller _controller;
	private NetworkServer _networkServer;
	private ServerGUI _serverGui;
	
	public Server(boolean gui)
	{
		_controller = new Controller();
		_networkServer = new NetworkServer(this);
		_networkServer.addClientConnectedListener(_controller);
		if(gui)
		{
			_serverGui = new ServerGUI(800, 400);
		}
	}
	
	public ServerGUI getServerGUI()
	{
		return _serverGui;
	}
	
	public Controller getController()
	{
		return _controller;
	}
	
	public void run()
	{
		(new Thread(_networkServer)).start();
		
		_controller.loadWorld("g");
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
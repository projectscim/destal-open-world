package destal.general;

import destal.general.net.server.NetworkServer;
import destal.general.ui.ServerGUI;

public class Server
{
	private Controller _controller;
	private NetworkServer _networkServer;
	private ServerGUI _serverGui;

	public static void main(String[] args)
	{
		(new Server()).run();
	}
	
	public Server()
	{
		_controller = new Controller();
		_networkServer = new NetworkServer(this, _controller);
		_serverGui = new ServerGUI(300, 300);
	}
	
	public void run()
	{
		(new Thread(_networkServer)).start();
		
		_controller.loadWorld("o");
	}
	
	public void showMessage(String message)
	{
		_serverGui.addMessage(message);
	}
}
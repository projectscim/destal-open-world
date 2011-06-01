package general;

import general.net.server.NetworkServer;

public class Server
{
	private Controller _controller;
	private NetworkServer _networkManager;

	public static void main(String[] args)
	{
		(new Server()).run();
	}
	
	public Server()
	{
		_controller = new Controller();
		_networkManager = new NetworkServer(this, _controller);
	}
	
	public void run()
	{
		(new Thread(_networkManager)).start();
		
		_controller.loadWorld("o");
	}
}
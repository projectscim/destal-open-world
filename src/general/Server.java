package general;

public class Server
{
	private Controller _controller;
	private NetworkManager _networkManager;

	public static void main(String[] args)
	{
		(new Server()).run();
	}
	
	public Server()
	{
		_controller = new Controller();
		_networkManager = new NetworkManager(this, _controller);
	}
	
	public void run()
	{
		(new Thread(_networkManager)).start();
		
		_controller.loadWorld("o");
	}
}
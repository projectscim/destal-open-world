package general;

public class Server
{
	private Controller _controller;
	private NetworkManager _networkManager;

	public static void main(String[] args) throws Exception
	{
		(new Server()).run();
	}
	
	public Server()
	{
		_networkManager = new NetworkManager(this);
	}
	
	public void run()
	{
		(new Thread(_networkManager)).start();
		
		// TODO: load world, handle players etc
	}
}
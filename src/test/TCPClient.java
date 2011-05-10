package test;

import java.io.*;
import java.net.*;

class TCPClient
{
	public static void main(String[] args) throws Exception
	{
		BufferedReader inFromUser = new BufferedReader( new InputStreamReader(System.in));
		
		Socket clientSocket = new Socket("localhost", 4185);
		ObjectOutputStream output = new ObjectOutputStream(clientSocket.getOutputStream());
		ObjectInputStream input = new ObjectInputStream(clientSocket.getInputStream());
		
		output.writeObject(inFromUser.readLine() + '\n');
		output.flush();
		
		String modifiedSentence = (String)input.readObject();
		
		System.out.println("FROM SERVER: " + modifiedSentence + '\n');
		clientSocket.close();
	}
}
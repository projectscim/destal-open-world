package test;

import java.io.*;
import java.net.*;

class TCPClient
{
	public static void main(String[] argv) throws Exception
	{
		BufferedReader inFromUser = new BufferedReader( new InputStreamReader(System.in));
		Socket clientSocket = new Socket("localhost", 4185);
		DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
		BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
		outToServer.writeBytes(inFromUser.readLine() + '\n');
		String modifiedSentence = inFromServer.readLine();
		System.out.println("FROM SERVER: " + modifiedSentence + '\n');
		clientSocket.close();
	}
}
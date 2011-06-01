package test;

import general.net.Packet;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

class TCPClient
{
	public static void main(String[] args) throws Exception
	{
		BufferedReader inFromUser = new BufferedReader( new InputStreamReader(System.in));
		
		Socket clientSocket = new Socket("localhost", 4185);
		ObjectOutputStream output = new ObjectOutputStream(clientSocket.getOutputStream());
		ObjectInputStream input = new ObjectInputStream(clientSocket.getInputStream());
		
		Packet p = new Packet((byte)0x01);
		p.set(inFromUser.readLine() + '\n');
		output.writeObject(p);
		output.flush();
		
		Packet r = (Packet)input.readObject();
		if(r.getType() == (byte)0x02)
		{
			String s = (String)r.get();
			int i = (Integer)r.get();
			System.out.println("Server: " + s + " - " + i);
		}
		
		clientSocket.close();
	}
}
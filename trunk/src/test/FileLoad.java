package test;

import java.io.*;

public class FileLoad
{
	public static void main(String[] args) throws Exception
	{
		File file = new File("C:\\Users\\Steffen\\testfile.tst");
		writeFile(file);
		readFile(file);

	}
	
	public static void writeFile(File file) throws Exception
	{
		FileOutputStream fs = new FileOutputStream(file);
		for (int i = 0; i < 256; i++)
		{
			fs.write(i);
		}
		fs.close();
	}
	
	public static void readFile(File file) throws Exception
	{
		FileInputStream fs = new FileInputStream(file);
		while(fs.available() > 0)
		{
			int i = fs.read();
			System.out.println(i);
		}
		fs.close();
	}
}

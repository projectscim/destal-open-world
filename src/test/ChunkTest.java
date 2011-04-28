package test;

import java.io.File;
import java.io.IOException;

import general.Chunk;

public class ChunkTest {

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception
	{
		System.out.println("Starte");
		Chunk myChunk = Chunk.createDefaultChunk();
		File f = new File("C:\\Users\\Steffen\\testfile.tst");
		myChunk.saveChunk(f);
		
		FileLoad.readFile(f);
		System.out.println("Beendet.");
	}

}

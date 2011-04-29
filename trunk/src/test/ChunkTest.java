package test;

import java.io.File;
import java.io.IOException;

import general.Chunk;
import general.World;

public class ChunkTest {

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception
	{
		System.out.println("Starte");
		/*Chunk myChunk = Chunk.createDefaultChunk();
		File f = new File("C:\\Users\\Steffen\\testfile.tst");
		myChunk.saveChunk(f);
		
		//FileLoad.readFile(f);*/
		World world = new World("Test4");
		System.out.println(world.getChunk(3, 5, 1).toString());
		System.out.println("Beendet.");
	}

}

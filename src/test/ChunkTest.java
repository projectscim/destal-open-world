package test;

import java.io.File;

import general.Chunk;
import general.World;

public class ChunkTest
{

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception
	{
		Chunk c = Chunk.createDefaultChunk();
		c.saveChunk(new File("C:\\lappen.chnk"));
	}

}

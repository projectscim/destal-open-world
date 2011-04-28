package entities;

import java.io.*;
import java.util.*;
import java.awt.*;
import java.lang.*;

public abstract class Block extends Entity
{
	public static Block create(int value) throws IllegalArgumentException
	{
		switch (value)
		{
			case Values.DIRT:
				return new Dirt();
			case Values.STONE:
				return new Stone();
			case Values.TREE:
				return new Tree();
			default:
				throw new IllegalArgumentException();
		}
	}
	
	public abstract int getDataValue();
}

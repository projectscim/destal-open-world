package Entities;

import java.io.*;
import java.util.*;
import java.awt.*;
import java.lang.*;

public abstract class Entity {

	private Point _location;

	public Point getLocation() {
		throw new UnsupportedOperationException("The method is not implemented yet.");
	}

	public void setLocation() {
		throw new UnsupportedOperationException("The method is not implemented yet.");
	}

	public abstract void paint(Graphics g);

}

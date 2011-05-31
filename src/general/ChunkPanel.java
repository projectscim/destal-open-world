package general;

import java.awt.Graphics;

import javax.swing.JPanel;

import entities.Player;

public class ChunkPanel extends JPanel
{
	private GUI _gui;
	private Chunk _chunk;
	private Chunk[] _chunkBuffer;
	private Player _player;
	
	public ChunkPanel (GUI gui, Chunk chunk)
	{
		super();
		_gui = gui;
		_chunk = chunk;
		setDoubleBuffered(true);
		System.out.println(_chunk.toString());
	}
	
	@Override
	public void paintComponent(Graphics g)
	{

	}
}

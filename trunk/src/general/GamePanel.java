package general;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

import javax.swing.JPanel;

import entities.Player;

public class GamePanel extends JPanel implements ActionListener, MouseMotionListener
{
	private GUI _gui;
	private MouseEvent _lastMouseEvent;
	private Player _player;
	
	public GamePanel (GUI gui, Player player)
	{
		super();
		_gui = gui;
	}
	
	public void paint(Graphics g)
	{
		_gui.disableCursor();
		g.setColor(Color.BLACK);
		if (_lastMouseEvent != null)
			g.drawOval(_lastMouseEvent.getX(), _lastMouseEvent.getY(), 3, 3);
		_player.paint(g);
	}
    @Override
    public void actionPerformed(ActionEvent e)
    {
    	if (e.getActionCommand().equals("Start Game"))
    	{
    		_gui.setGUIMode (GUI.GUIMode.GAME);
    	}
    	if (e.getActionCommand().equals("Options"))
    	{
    		_gui.setGUIMode (GUI.GUIMode.OPTIONS);
    	}
    	if (e.getActionCommand().equals("Exit"))
    	{
    		System.exit(0);
    	}
    }

	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseMoved(MouseEvent e)
	{
		_lastMouseEvent = e;
	}
}

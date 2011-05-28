package general;


import java.awt.Button;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;

public class MenuPanel extends JPanel implements ActionListener
{
	private Button[] _button;
	private TextField _textField;
	private GUI _gui;
	private String _serverIp;
	
	public MenuPanel (GUI gui)
	{
		super();
		_gui = gui;
		
		_textField = new TextField("Server IP");
		_textField.setLocation(50, 50);
		_textField.setSize(150, 20);
		this.add(_textField);
		_button = new Button[]{	new Button ("Start Game"),
								new Button ("Options"),
								new Button ("Exit") };
		for (int i = 0; i < _button.length; i++)
		{
			_button[i].setLocation((i+1) * 100, 100);
			_button[i].setSize(100, 20);
			this.add(_button[i]);
			_button[i].addActionListener(this);
		}
		
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
}

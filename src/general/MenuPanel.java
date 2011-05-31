package general;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class MenuPanel extends JPanel implements ActionListener
{
	private JButton[] _button;
	private JTextField[] _textField;
	private GUI _gui;
	
	public MenuPanel (GUI gui)
	{
		super();
		_gui = gui;
		
		_textField = new JTextField[] { new JTextField("localhost"),
										new JTextField("username") };
		for (int i = 0; i < _textField.length; i++)
		{
			_textField[i].setLocation(i * 300 + 50, 50);
			_textField[i].setSize(150, 20);
			this.add(_textField[i]);
		}

		_button = new JButton[]{	new JButton ("Start Game"),
								new JButton ("Options"),
								new JButton ("Exit") };
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
    		_gui.getClient().connect(_textField[0].getText(), _textField[1].getText());
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

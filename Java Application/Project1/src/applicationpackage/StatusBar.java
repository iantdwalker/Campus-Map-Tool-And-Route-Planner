package applicationpackage;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Panel;
 
/**
 * A class that extends Panel to display a status bar.
 */
public class StatusBar extends Panel
{
	private int       _fields;
  private int[]     _insets;
 	private String[]  _messages;
  private Font      _font       = new Font("Verdana", Font.PLAIN, 12);
  private Color     _background = new Color(204,204,204);
	private Color     _darkGray   = new Color(128,128,128);
	 
	/**
   * Constructor.
   */
	StatusBar()
	{
		this(1);
	}
 
	/**
   * Constructor.
   * @param fields
   */
	StatusBar(int fields)
	{
		this(fields, null);
	}
 
  /**
   * Constructor.
   * @param insets
   * @param fields
   */
	StatusBar(int fields, int[] insets)
	{
		_fields  = fields;
		_insets  = insets;
 
		if (_insets == null)
		{
			_insets = new int[_fields];
      
			for (int i = 0; i < _insets.length; i++)
      {
        _insets[i] = i * 50;
      }				
		}
 
		_messages = new String[_fields];
    
		for (int i = 0; i < _messages.length; i++)
    {
      _messages[i] = "";
    }		
 
		setLayout(null);
		setSize(3000, 21);
		setBackground(_background);
	}
 
	/**
   * Draw method to paint the status bar.
   * @param g
   */
	public void paint(Graphics g)
	{
		g.setFont(_font);	
		int offset = 0;
    
		for (int i = 0; i < _fields - 1; i++)
		{
			g.setColor(Color.BLACK);
			g.drawString(_messages[i], offset + 5, 17);
 			offset = _insets[i + 1];		
		}
    
		g.setColor(_darkGray);
		g.drawLine(offset, 1, 2998, 0);
		 
		g.setColor(Color.BLACK);
		g.drawString(_messages[_fields - 1], offset + 5, 17);
	}
 
  /**
   * Set the array of messages for the display fields and paint them.
   * @param messages
   */
	public void setText(String[] messages)
	{
		_messages = messages;
		repaint();
	} 
	
  /**
   * Sets the number of fields, offset measurements and string messages. 
   * @param messages
   * @param insets
   * @param fields
   */
	public void setText(int fields, int[] insets, String[] messages)
	{
		_fields   = fields;
		_insets   = insets;
		_messages = messages;
		repaint();
	} 
	
  /**
   * Get the string array of messages being displayed.
   * @return String[]
   */
	public String[] getText()
	{
		return _messages;
	}
}
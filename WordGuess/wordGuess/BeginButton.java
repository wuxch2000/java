package wordGuess;

import javax.swing.*;

import java.awt.event.ActionListener;
import java.beans.*;

public class BeginButton extends JButton
{
	private WordGuessPanel m_panel;
	public BeginButton(WordGuessPanel panel)
	{
		super();
		m_panel = panel;
		m_is_started = false;
		setText(getStatusString(m_is_started));
		addActionListener(EventHandler.create(ActionListener.class, this, "click"));
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void click()
	{
		toggleStatus();
		m_panel.update();
		return;
	}
	private void toggleStatus()
	{
//		m_is_started = Boolean.valueOf(!m_is_started);
		if( m_is_started == false )
			m_is_started = true;
		setText(getStatusString(m_is_started));
	}
	
	private boolean m_is_started;
	
	private static String getStatusString(boolean status)
	{
		if( status )
			return String.valueOf("下一个");
		else
			return String.valueOf("开始");
	}
	
}

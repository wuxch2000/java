package wordGuess;

import java.awt.*;
import javax.swing.*;


public class WordGuessFrame extends JFrame
{
	public WordGuessFrame()
	{
		setBasicInfo();
		addComponent();
	}

	private void addComponent()
	{
		Container contentPane = getContentPane();
		
		WordGuessPanel panel = new WordGuessPanel();
		contentPane.add(panel);
		
		return;
	}
	
	private void setBasicInfo()
	{
//		获取屏幕参数
		Toolkit kit = Toolkit.getDefaultToolkit();
		Dimension screen_size = kit.getScreenSize();
		int height = screen_size.height / 2;
		int width  = screen_size.width / 2;
		
		setSize(width, height);
		setTitle(m_title);
		setResizable(true);
		setLocationByPlatform(true);
		
		return;
	}
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final String m_title = "Word Guessing!";
}

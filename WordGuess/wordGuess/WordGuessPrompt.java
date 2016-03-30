package wordGuess;

import java.awt.*;

import javax.swing.*;

public class WordGuessPrompt extends JLabel
{

	public WordGuessPrompt()
	{
		super();
		setText("Çë°´¼ü");
		Font old_font = getFont();
		setFont(new Font(	old_font.getName(),
							old_font.getStyle(),
							old_font.getSize()*10));
//		setAlignmentX(JLabel.CENTER_ALIGNMENT);
//		setAlignmentY(JLabel.CENTER_ALIGNMENT);
		setVerticalAlignment(CENTER);
		setHorizontalAlignment(CENTER);

	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}

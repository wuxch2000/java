package wordGuess;

import java.awt.*;
import javax.swing.*;

import wordLib.WordLib;

public class WordGuessPanel extends JPanel
{

	public WordGuessPanel()
	{
		super();
		setLayout(new BorderLayout());
		addPrompt();
		addProgress();
		addButton();
		
	}
	
	private void addButton()
	{
		m_begin_button = new BeginButton(this);
		this.add(m_begin_button,BorderLayout.SOUTH);
	}
	private void addPrompt()
	{
		m_prompt = new WordGuessPrompt();
		this.add(m_prompt,BorderLayout.CENTER);
	}
	private void addProgress()
	{
		int max_value = WordLib.getInitSize();
		m_progress = new JProgressBar(0,max_value);
		m_progress.setValue(0);
		m_progress.setStringPainted(true);
		this.add(m_progress,BorderLayout.NORTH);
	}
	private void updateProgess()
	{
		int progress = WordLib.getInitSize() - WordLib.getCurrentSize();
		m_progress.setValue(progress);
	}
	
	private void updatePrompt()
	{
		m_prompt.setText(WordLib.getWord());
		return;
	}
	
	public void update()
	{
		updatePrompt();
		updateProgess();
	}
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private WordGuessPrompt m_prompt;
	private JProgressBar m_progress;
	
	private JButton m_begin_button;

	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
	}

}

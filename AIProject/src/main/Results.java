package main;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextField;

public class Results extends JFrame{
	
	private JTextField classifiedAs;
	private JTextField question;
	
	private JTextField resultDoc;
	
	private JButton correct;
	private JButton notCorrect;
	
	GridBagConstraints gbc = new GridBagConstraints();
	
	public Results() {
		super("Classify");
		setLayout(new GridBagLayout());
		
		gbc.insets = new Insets(4,4,4,4);
		
		classifiedAs = new JTextField("This document has been classified as: ");
		classifiedAs.setEditable(false);
		classifiedAs.setBorder(null);
		gbc.gridx = 1;
		gbc.gridy= 0;
		add(classifiedAs, gbc);
		
		question = new JTextField("Is this correct?");
		question.setEditable(false);
		question.setBorder(null);
		gbc.gridx  = 1;
		gbc.gridy = 1;
		add(question,gbc);
		
		resultDoc = new JTextField();
		resultDoc.setEditable(false);
		resultDoc.setBorder(null);
		gbc.gridx = 1;
		gbc.gridy = 0;
		add(resultDoc, gbc);
		
		correct = new JButton("Yes");
		gbc.gridx = 0;
		gbc.gridy = 2;
		add(correct, gbc);
		
		notCorrect = new JButton("No");
		gbc.gridx = 2;
		gbc.gridy = 2;
		add(notCorrect, gbc);

		
		
	}

}

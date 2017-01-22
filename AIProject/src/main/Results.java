package main;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JFrame;
import javax.swing.JTextField;

public class Results extends JFrame{
	
	private JTextField catAclasses;
	private JTextField catBclasses;
	private JTextField accuracy;
	
	private JTextField resultCatA;
	private JTextField resultCatB;
	private JTextField resultAccuracy;
	
	GridBagConstraints gbc = new GridBagConstraints();
	
	public Results() {
		super("Results");
		setLayout(new GridBagLayout());
		
		gbc.insets = new Insets(4,4,4,4);
		
		catAclasses = new JTextField("Number of catagory A documents: ");
		catAclasses.setEditable(false);
		catAclasses.setBorder(null);
		gbc.gridx = 0;
		gbc.gridy= 0;
		add(catAclasses, gbc);
		
		catBclasses = new JTextField("Number of catagory B documents: ");
		catBclasses.setEditable(false);
		catBclasses.setBorder(null);
		gbc.gridx  = 0;
		gbc.gridy = 1;
		add(catBclasses,gbc);
		
		accuracy = new JTextField("Accuracy: ");
		accuracy.setEditable(false);
		accuracy.setBorder(null);
		gbc.gridx = 0;
		gbc.gridy = 2;
		add(accuracy, gbc);
		
		resultCatA = new JTextField();
		resultCatA.setEditable(false);
		resultCatA.setBorder(null);
		gbc.gridx = 1;
		gbc.gridy = 0;
		add(resultCatA, gbc);
		
		resultCatB = new JTextField();
		resultCatB.setEditable(false);
		resultCatB.setBorder(null);
		gbc.gridx = 1;
		gbc.gridy = 1;
		add(resultCatB, gbc);
		
		resultAccuracy = new JTextField();
		resultAccuracy.setEditable(false);
		resultAccuracy.setBorder(null);
		gbc.gridx = 1;
		gbc.gridy = 2;
		add(resultAccuracy, gbc);
		
		
	}

}

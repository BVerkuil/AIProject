package main;

import java.util.HashMap;

import javax.swing.JFrame;

import classifiers.simpleClassifier;

public class Main {
	
	public static void main(String[] args) {
		HashMap<String, String> dataSet = new HashMap<String, String>();
		dataSet.put("Ham", "../AIProject/Ham");
		dataSet.put("Spam", "../AIProject/Spam");
		
//		GUI gui = new GUI();
//		gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		gui.setSize(650, 300);
//		gui.setVisible(true);
		
 		simpleClassifier simpleClassifier = new simpleClassifier();
		simpleClassifier.trainClassifier(dataSet, 0.9);
		System.out.println(simpleClassifier.testAccuracy("Ham"));
	}

	
	
}

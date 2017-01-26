package main;

import javax.swing.JFrame;

import classifiers.correctClassifier;;

public class Main {

	public static void main(String[] args) {
		correctClassifier correctClassifier = new correctClassifier(0.9);
		GUI gui = new GUI(correctClassifier);
		gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		gui.setSize(750, 300);
		gui.setVisible(true);

		
		System.out.println("making Ham");
//		correctClassifier.addType("Ham", "/home/bart/Documents/AI-Project/corpus-mails/Ham");
//		correctClassifier.addType("Ham", "../AIProject/Ham");
//		correctClassifier.addType("Spam", "/home/bart/Documents/AI-Project/corpus-mails/Spam");
//		correctClassifier.addType("Spam", "../AIProject/Spam");
//		correctClassifier.selectVocabulary(300);
//		correctClassifier.testClassifier();
	}

}

package main;

import classifiers.correctClassifier;;

public class Main {

	public static void main(String[] args) {
		//		GUI gui = new GUI();
		//		gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//		gui.setSize(650, 300);
		//		gui.setVisible(true);

		correctClassifier correctClassifier = new correctClassifier();
		correctClassifier.addType("Ham", "../AIProject/Ham");
		correctClassifier.addType("Spam", "../AIProject/Spam");
		System.out.println(correctClassifier.features.size());
		correctClassifier.selectFeatures();
	}

}

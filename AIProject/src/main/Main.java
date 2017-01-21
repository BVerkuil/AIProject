package main;

import java.util.HashMap;

import classifiers.correctClassifier;;

public class Main {
	
	public static void main(String[] args) {
		HashMap<String, String> dataSet = new HashMap<String, String>();
		dataSet.put("Ham", "../AIProject/Ham");
		dataSet.put("Spam", "../AIProject/Spam");
		
//		GUI gui = new GUI();
//		gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		gui.setSize(650, 300);
//		gui.setVisible(true);
		
 		correctClassifier correctClassifier = new correctClassifier();
 		correctClassifier.addType("../AIProject/Ham", "Ham");
 		Document document = new Document("test", "../AIProject/Ham/3-375msg1.txt");
 		System.out.println(document.getFeatures().toString());
	}

	
	
}

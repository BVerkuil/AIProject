package main;

import java.util.HashMap;

import classifiers.simpleClassifier;

public class Main {
	
	public static void main(String[] args) {
		HashMap<String, String> dataSet = new HashMap<String, String>();
		dataSet.put("Ham", "../AIProject/Ham");
		dataSet.put("Spam", "../AIProject/Spam");
		
 		simpleClassifier simpleClassifier = new simpleClassifier();
		simpleClassifier.trainClassifier(dataSet, 0.9);
	}

	
	
}

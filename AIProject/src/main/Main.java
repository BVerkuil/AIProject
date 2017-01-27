package main;

import javax.swing.JFrame;

import classifiers.correctClassifier;;

public class Main {

	public static void main(String[] args) {
		correctClassifier correctClassifier = new correctClassifier(0.9, 300);
		GUI gui = new GUI(correctClassifier);
		gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		gui.setSize(750, 300);
		gui.setVisible(true);
	}

}

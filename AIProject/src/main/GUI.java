package main;

import java.awt.CardLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.Iterator;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

import classifiers.correctClassifier;

public class GUI extends JFrame {

	private JPanel cards;
	
//card 1 variables
	private JTextField folder1;
	private JTextField folder2;
	private JTextField ratio;
	private JTextField catAName;
	private JTextField catBName;
	private JTextField catA;
	private JTextField catB;
	private JTextField staticVocab;
	private JTextField vocabSize;
	private JTextField tRatio;
	private String catAFolder;
	private String catBFolder;
	private Double trainingRatio;
	private JButton f1Button;
	private JButton f2Button;
	private JButton nextButton;
	private JFileChooser fc;
	private JFileChooser fc2;
	
//card 2 variables
	private JTextField staticAccuracy;
	private JTextField accuracy;
	private JTextField folder3;
	private JTextField toBeClassified;
	private JButton f3Button;
	private JFileChooser fc3;
	private String toBeClassifiedPath;
	private JButton classify;
	private JButton back;
	private JButton trainingHelp;

//card 3 variables
	private JTextField classifiedAs;
	private JTextField question;
	private JTextField resultDoc;
	private JButton correct;
	private JButton notCorrect;
	
//other variables
	private Iterator iterate;
	
	private CardLayout cardLayout;

	GridBagConstraints gbc = new GridBagConstraints();

	public GUI(correctClassifier correctClassifier) {
		super("Classifier");
		setLayout(new FlowLayout());

		JPanel card1 = new JPanel(new GridBagLayout());
		// gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.insets = new Insets(4, 4, 4, 4);

		gbc.weightx = 0.25;
		gbc.weighty = 1;

		catAName = new JTextField("Name: ");
		catAName.setEditable(false);
		catAName.setBorder(null);
		gbc.gridx = 0;
		gbc.gridy = 0;
		card1.add(catAName, gbc);

		catBName = new JTextField("Name: ");
		catBName.setEditable(false);
		catBName.setBorder(null);
		gbc.gridx = 0;
		gbc.gridy = 1;
		card1.add(catBName, gbc);
		
		catA = new JTextField(20);
		gbc.gridx= 1;
		gbc.gridy= 0;
		card1.add(catA, gbc);
		
		catB = new JTextField(20);
		gbc.gridx = 1;
		gbc.gridy = 1;
		card1.add(catB, gbc);

		tRatio = new JTextField("Training ratio: ");
		tRatio.setEditable(false);
		tRatio.setBorder(null);
		gbc.gridx = 0;
		gbc.gridy = 2;
		card1.add(tRatio, gbc);
		
		staticVocab = new JTextField("Vocabulary size: ");
		staticVocab.setEditable(false);
		staticVocab.setBorder(null);
		gbc.gridx = 0;
		gbc.gridy= 3;
		card1.add(staticVocab, gbc);
		
		vocabSize = new JTextField(20);
		gbc.gridx = 1;
		gbc.gridy = 3;
		card1.add(vocabSize, gbc);
		

		folder1 = new JTextField(20);
		gbc.gridx = 2;
		gbc.gridy = 0;
		card1.add(folder1, gbc);

		folder2 = new JTextField(20);
		gbc.gridx = 2;
		gbc.gridy = 1;
		card1.add(folder2, gbc);

		ratio = new JTextField(20);
		gbc.gridx = 1;
		gbc.gridy = 2;
		card1.add(ratio, gbc);

		f1Button = new JButton("Choose folder");
		gbc.gridx = 3;
		gbc.gridy = 0;
		card1.add(f1Button, gbc);

		f2Button = new JButton("Choose folder");
		gbc.gridx = 3;
		gbc.gridy = 1;
		card1.add(f2Button, gbc);

		nextButton = new JButton("next");
		gbc.gridx = 3;
		gbc.gridy = 4;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		card1.add(nextButton, gbc);

		
		//Second screen will be made
		
		JPanel card2 = new JPanel(new GridBagLayout());

		toBeClassified = new JTextField("Folder to Classify: ");
		toBeClassified.setEditable(false);
		toBeClassified.setBorder(null);
		gbc.gridx = 0;
		gbc.gridy = 1;
		card2.add(toBeClassified, gbc);
		
		staticAccuracy = new JTextField("The total accuracy is: ");
		staticAccuracy.setEditable(false);
		staticAccuracy.setBorder(null);
		gbc.gridx =0;
		gbc.gridy=0;
		card2.add(staticAccuracy, gbc);
		
		accuracy = new JTextField(20);
		accuracy.setEditable(false);
		accuracy.setBorder(null);
		gbc.gridx = 1;
		gbc.gridy= 0;
		card2.add(accuracy, gbc);

		folder3 = new JTextField(20);
		gbc.gridx = 1;
		gbc.gridy = 1;
		card2.add(folder3, gbc);

		f3Button = new JButton("Choose folder");
		gbc.gridx = 2;
		gbc.gridy = 1;
		card2.add(f3Button, gbc);

		classify = new JButton("Classify!");
		gbc.gridx = 1;
		gbc.gridy = 2;
		card2.add(classify, gbc);

		back = new JButton("Back");
		gbc.gridx = 0;
		gbc.gridy = 2;
		card2.add(back, gbc);
		
		trainingHelp = new JButton("Help training!");
		gbc.gridx=2;
		gbc.gridy=2;
		card2.add(trainingHelp, gbc);

		//Third screen will be made
		
		JPanel card3 = new JPanel(new GridBagLayout());

		gbc.insets = new Insets(4, 4, 4, 4);

		classifiedAs = new JTextField();
		classifiedAs.setEditable(false);
		classifiedAs.setBorder(null);
		gbc.gridx = 1;
		gbc.gridy = 0;
		gbc.fill = gbc.CENTER;
		card3.add(classifiedAs, gbc);

		question = new JTextField("What is the correct class?");
		question.setEditable(false);
		question.setBorder(null);
		gbc.gridx = 1;
		gbc.gridy = 1;
		gbc.fill = gbc.CENTER;
		card3.add(question, gbc);

		resultDoc = new JTextField();
		resultDoc.setEditable(false);
		resultDoc.setBorder(null);
		gbc.gridx = 1;
		gbc.gridy = 0;
		card3.add(resultDoc, gbc);

		correct = new JButton("Yes");
		gbc.gridx = 0;
		gbc.gridy = 2;
		card3.add(correct, gbc);

		notCorrect = new JButton("No");
		gbc.gridx = 2;
		gbc.gridy = 2;
		card3.add(notCorrect, gbc);

		cards = new JPanel(new CardLayout());

		cards.add(card1, "Card 1");
		cards.add(card2, "Card 2");
		cards.add(card3, "Card 3");

		cardLayout = (CardLayout) cards.getLayout();
		cardLayout.show(cards, "Card 1");

		getContentPane().add(cards);

		fc = new JFileChooser();
		fc.setDialogTitle("Choose folder");
		fc.setCurrentDirectory(new java.io.File("."));
		fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

		fc2 = new JFileChooser();
		fc2.setDialogTitle("Choose folder");
		fc2.setCurrentDirectory(new java.io.File("."));
		fc2.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

		fc3 = new JFileChooser();
		fc3.setDialogTitle("Choose folder");
		fc3.setCurrentDirectory(new java.io.File("."));
		fc3.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

		
		f1Button.addActionListener(event -> {
			int option = fc.showOpenDialog((Component) event.getSource());
			if (option == JFileChooser.APPROVE_OPTION) {
				try {
					folder1.setText(fc.getSelectedFile().getAbsolutePath());
					
				} catch (Exception e) {
					e.printStackTrace(System.out);
				}
			}
		});
		
		f2Button.addActionListener(event -> {
			int option = fc2.showOpenDialog((Component) event.getSource());
			if (option == JFileChooser.APPROVE_OPTION) {
				try {
					folder2.setText(fc2.getSelectedFile().getAbsolutePath());
				} catch (Exception e) {
					e.printStackTrace(System.out);
				}
			}
			
		});
		
		nextButton.addActionListener(event -> {
			correct.setText(catA.getText());
			notCorrect.setText(catB.getText());
			correctClassifier.addType(catA.getText(), folder1.getText());
			correctClassifier.addType(catB.getText(), folder2.getText());
			try {
				correctClassifier.trainingRatio = Double.parseDouble(ratio.getText());
				correctClassifier.selectVocabulary(Integer.parseInt(vocabSize.getText()));
			} catch (NumberFormatException e) {
				System.out.println("The number was not correct");
			}
			correctClassifier.testClassifier();
			
			cardLayout.show(cards, "Card 2");
		});
		
		f3Button.addActionListener(event -> {
			int option = fc3.showOpenDialog((Component) event.getSource());
			if (option == JFileChooser.APPROVE_OPTION) {
				try {
					folder3.setText(fc3.getSelectedFile().getAbsolutePath());
				} catch (Exception e) {
					e.printStackTrace(System.out);
				}
			}
		});
		
		back.addActionListener(event -> {
			cardLayout.show(cards, "Card 1");
		});
		
		trainingHelp.addActionListener(event -> {
			iterate = correctClassifier.wrongClassified.iterator();
			if(iterate.hasNext()) {
				System.out.println(((Document) iterate.next()).getName());
				classifiedAs.setText("Document \"" + ((Document) iterate.next()).getName() +  "\" has not been classified correct ");
			}
			
			resultDoc.setText("spam");
			cardLayout.show(cards, "Card 3");
		});
		
		correct.addActionListener(event -> {
			if(iterate.hasNext()) {
			classifiedAs.setText("Document \"" + ((Document) iterate.next()).getName() +  "\" is classified as: ");
			} else {
				// set new accuracy
				cardLayout.show(cards, "Card 2");
			}
			resultDoc.setText("spm"); //moet een getter bij voor het volgende document
		});
		
		notCorrect.addActionListener(event -> {
			if(iterate.hasNext()) {
				classifiedAs.setText("Document \"" + ((Document) iterate.next()).getName() +  "\" is classified as: ");
				} else {
					// set new accuracy
					cardLayout.show(cards, "Card 2");
				}
			resultDoc.setText("ham"); //moet dezelfde getter bij
		});

	}

	public String getCatAFolder() {
		return catAFolder;
	}

	public String getCatBFolder() {
		return catBFolder;
	}

	public Double getRatio() {
		return trainingRatio;
	}

}

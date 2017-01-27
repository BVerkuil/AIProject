package main;

import java.awt.CardLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.text.DecimalFormat;
import java.util.Iterator;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import classifiers.correctClassifier;

public class GUI extends JFrame {

	private JPanel cards;

	// card 1 variables
	private JTextField folder1;
	private JTextField folder2;
	private JTextField ratio;
	private JLabel catAName;
	private JLabel catBName;
	private JTextField catA;
	private JTextField catB;
	private JLabel staticVocab;
	private JTextField vocabSize;
	private JLabel tRatio;
	private String catAFolder;
	private String catBFolder;
	private Double trainingRatio;
	private JButton f1Button;
	private JButton f2Button;
	private JButton nextButton;
	private JFileChooser fc;
	private JFileChooser fc2;
	private JLabel loadingLabel;

	// card 2 variables
	private JTextField accuracy;
	private JTextField folder3;
	private JTextField toBeClassified;
	private JButton f3Button;
	private JFileChooser fc3;
	private String toBeClassifiedPath;
	private JButton classify;
	private JButton back;
	private JButton trainingHelp;

	// card 3 variables
	private JTextField classifiedAs;
	private JTextField question;
	private JTextField resultDoc;
	private JButton correct;
	private JButton notCorrect;

	// other variables
	private Iterator<Document> iterate;
	private Document nextDoc;

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

		catAName = new JLabel("Name: ");
		gbc.gridx = 0;
		gbc.gridy = 0;
		card1.add(catAName, gbc);

		catBName = new JLabel("Name: ");
		gbc.gridx = 0;
		gbc.gridy = 1;
		card1.add(catBName, gbc);

		catA = new JTextField(10);
		gbc.gridx = 1;
		gbc.gridy = 0;
		card1.add(catA, gbc);

		catB = new JTextField(10);
		gbc.gridx = 1;
		gbc.gridy = 1;
		card1.add(catB, gbc);

		tRatio = new JLabel("Training ratio: ");
		gbc.gridx = 0;
		gbc.gridy = 2;
		card1.add(tRatio, gbc);

		staticVocab = new JLabel("Vocabulary size: ");
		gbc.gridx = 0;
		gbc.gridy = 3;
		card1.add(staticVocab, gbc);

		vocabSize = new JTextField(10);
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

		ratio = new JTextField(10);
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

		ImageIcon loading = new ImageIcon("ajax-loader.gif");
		loadingLabel = new JLabel("loading... ", loading, JLabel.CENTER);
		loadingLabel.setVisible(false);
		gbc.gridx = 2;
		gbc.gridy = 4;
		card1.add(loadingLabel, gbc);

		// Second screen will be made

		JPanel card2 = new JPanel(new GridBagLayout());

		toBeClassified = new JTextField("Folder to Classify: ");
		toBeClassified.setEditable(false);
		toBeClassified.setBorder(null);
		gbc.gridx = 0;
		gbc.gridy = 1;
		card2.add(toBeClassified, gbc);

		accuracy = new JTextField(20);
		accuracy.setEditable(false);
		accuracy.setBorder(null);
		gbc.gridx = 1;
		gbc.gridy = 0;
		gbc.fill = GridBagConstraints.CENTER;
		card2.add(accuracy, gbc);

		folder3 = new JTextField(20);
		gbc.gridx = 1;
		gbc.gridy = 1;
		card2.add(folder3, gbc);

		f3Button = new JButton("Choose folder");
		gbc.gridx = 2;
		gbc.gridy = 1;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		card2.add(f3Button, gbc);

		classify = new JButton("Classify!");
		gbc.gridx = 1;
		gbc.gridy = 2;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		card2.add(classify, gbc);

		back = new JButton("Back");
		gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		card2.add(back, gbc);

		trainingHelp = new JButton("Help training!");
		gbc.gridx = 2;
		gbc.gridy = 2;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		card2.add(trainingHelp, gbc);

		// Third screen will be made

		JPanel card3 = new JPanel(new GridBagLayout());

		gbc.insets = new Insets(4, 4, 4, 4);

		classifiedAs = new JTextField();
		classifiedAs.setEditable(false);
		classifiedAs.setBorder(null);
		gbc.gridx = 1;
		gbc.gridy = 0;
		gbc.fill = GridBagConstraints.CENTER;
		card3.add(classifiedAs, gbc);

		question = new JTextField("What is the correct class?");
		question.setEditable(false);
		question.setBorder(null);
		gbc.gridx = 1;
		gbc.gridy = 1;
		gbc.fill = GridBagConstraints.CENTER;
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
			boolean bool = true;
			correct.setText(catA.getText());
			notCorrect.setText(catB.getText());
			correctClassifier.addType(catA.getText(), folder1.getText());
			correctClassifier.addType(catB.getText(), folder2.getText());
			try {
				correctClassifier.trainingRatio = Double.parseDouble(ratio.getText());
				correctClassifier.setVocabularySize(Integer.parseInt(vocabSize.getText()));
				correctClassifier.selectVocabulary();
			} catch (NumberFormatException e) {
				bool = false;
			}
			if (bool) {
				loadingLabel.setVisible(true);
				DecimalFormat numberFormat = new DecimalFormat("#0.00");
				accuracy.setText("The accuracy is : " + numberFormat.format(correctClassifier.testClassifier()));
				cardLayout.show(cards, "Card 2");
				loadingLabel.setVisible(false);
			} else {
				JOptionPane.showMessageDialog(null, "The training ration or vocabulary size was not correct!", "Error",
						JOptionPane.ERROR_MESSAGE);
			}
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
			if (iterate.hasNext()) {
				nextDoc = iterate.next();
				System.out.println(((Document) iterate.next()).getName());
				classifiedAs.setText("Document \"" + nextDoc.getName() + "\" has not been classified correct ");
				cardLayout.show(cards, "Card 3");
			} else {
				JOptionPane.showMessageDialog(null, "Everything was classified correctly", "Error",
						JOptionPane.ERROR_MESSAGE);
			}

			resultDoc.setText("spam");

		});

		correct.addActionListener(event -> {
			for (main.Type type : correctClassifier.types) {
				if (type.name.equals(correct.getText())) {
					correctClassifier.addDocumentAfterFeedback(type, nextDoc);
				}
			}
			if (iterate.hasNext()) {
				nextDoc = iterate.next();
				classifiedAs.setText("Document \"" + nextDoc.getName() + "\" is classified as: ");

			} else {
				correctClassifier.rebuildClassifier();
				DecimalFormat numberFormat = new DecimalFormat("#0.00");
				accuracy.setText("The accuracy is : " + numberFormat.format(correctClassifier.testClassifier()));
				cardLayout.show(cards, "Card 2");
			}

		});

		notCorrect.addActionListener(event -> {
			for (main.Type type : correctClassifier.types) {
				if (type.name.equals(correct.getText())) {
					correctClassifier.addDocumentAfterFeedback(type, nextDoc);
				}
			}
			if (iterate.hasNext()) {
				nextDoc = iterate.next();
				classifiedAs.setText("Document \"" + nextDoc.getName() + "\" is classified as: ");
			} else {
				correctClassifier.rebuildClassifier();
				DecimalFormat numberFormat = new DecimalFormat("#0.00");
				accuracy.setText("The accuracy is : " + numberFormat.format(correctClassifier.testClassifier()));
				cardLayout.show(cards, "Card 2");

			}
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

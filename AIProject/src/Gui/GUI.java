package Gui;

import java.awt.CardLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import classifiers.correctClassifier;
import main.Document;

public class GUI extends JFrame {

	// JOptionPane.showMessageDialog(null, "The training ration or vocabulary
	// size was not correct!", "Error",
	// JOptionPane.ERROR_MESSAGE);

	private JPanel cards;

	// card 1 variables
	private Category catA;
	private Category catB;
	private JButton nextButton;
	private JLabel loadingLabel;
	private JButton createNew;
	private int numberCat;
	

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

	// other variables
	private Iterator<Document> iterate;
	private Document nextDoc;
	
	private ArrayList<Category> categories= new ArrayList<Category>();

	private CardLayout cardLayout;

	GridBagConstraints gbc = new GridBagConstraints();

	public GUI(correctClassifier correctClassifier) {
		super("Classifier");
		setLayout(new FlowLayout());
		
		JComboBox<String> myCategories = new JComboBox<String>();

		JPanel card1 = new JPanel(new GridBagLayout());
		// gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.insets = new Insets(4, 4, 4, 4);
		numberCat = 2;

		gbc.weightx = 0.25;
		gbc.weighty = 1;

		catA = new Category();
		gbc.gridx = 0;
		gbc.gridy = 0;
		card1.add(catA, gbc);
		categories.add(catA);

		catB = new Category();
		gbc.gridx = 0;
		gbc.gridy = 1;
		card1.add(catB, gbc);
		categories.add(catB);

		createNew = new JButton("add");
		gbc.gridx = 0;
		gbc.gridy = 200;
		card1.add(createNew, gbc);

		Variables variable = new Variables();
		gbc.gridx = 0;
		gbc.gridy = 300;
		card1.add(variable, gbc);

		nextButton = new JButton("next");
		gbc.gridx = 300;
		gbc.gridy = 400;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		card1.add(nextButton, gbc);

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
		
		gbc.gridx = 0;
		gbc.gridy = 2;
		card3.add(myCategories, gbc);

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

		correct = new JButton("Next");
		gbc.gridx = 1;
		gbc.gridy = 2;
		card3.add(correct, gbc);


		cards = new JPanel(new CardLayout());

		cards.add(card1, "Card 1");
		cards.add(card2, "Card 2");
		cards.add(card3, "Card 3");

		cardLayout = (CardLayout) cards.getLayout();
		cardLayout.show(cards, "Card 1");

		getContentPane().add(cards);

		// fc = new JFileChooser();
		// fc.setDialogTitle("Choose folder");
		// fc.setCurrentDirectory(new java.io.File("."));
		// fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		//
		// fc2 = new JFileChooser();
		// fc2.setDialogTitle("Choose folder");
		// fc2.setCurrentDirectory(new java.io.File("."));
		// fc2.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

		fc3 = new JFileChooser();
		fc3.setDialogTitle("Choose folder");
		fc3.setCurrentDirectory(new java.io.File("."));
		fc3.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		
		createNew.addActionListener(event -> {
			Category cat = new Category();
			gbc.gridx = 0;
			gbc.gridy = numberCat;
			card1.add(cat, gbc);
			card1.updateUI();
			categories.add(cat);
			numberCat++;
		});

		nextButton.addActionListener(event -> {
			for(Category category : categories) {
				correctClassifier.addType(category.getName(), category.getPath());
				myCategories.addItem(category.getName());
			}
			correctClassifier.trainingRatio = variable.getTratio();
			correctClassifier.setVocabularySize(variable.getVocabSize());
			correctClassifier.selectVocabulary();
			DecimalFormat numberFormat = new DecimalFormat("#0.00");
			accuracy.setText("The accuracy is : " + numberFormat.format(correctClassifier.testClassifier()));
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
			if (iterate.hasNext()) {
				nextDoc = iterate.next();
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
				if (type.name.equals(myCategories.getSelectedItem())) {
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
}

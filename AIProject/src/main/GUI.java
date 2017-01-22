package main;

import java.awt.CardLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class GUI extends JFrame {
	
	private JPanel cards;
	
	private JTextField folder1;
	private JTextField folder2;
	private JTextField ratio;
	
	private JTextField catA;
	private JTextField catB;
	private JTextField tRatio;
	
	private String catAFolder;
	private String catBFolder;
	private Double trainingRatio;
	
	private JButton f1Button;
	private JButton f2Button;
	private JButton nextButton;
	
	private JFileChooser fc;
	private JFileChooser fc2;
	
	private JTextField folder3;
	private JTextField toBeClassified;
	private JButton f3Button;
	private JFileChooser fc3;
	private String toBeClassifiedPath;
	private JButton classify;
	private JButton back;
	
	GridBagConstraints gbc = new GridBagConstraints();
	
	public GUI() {
		super("Classifier");
		setLayout(new FlowLayout());
		
		JPanel card1 = new JPanel(new GridBagLayout());
		//gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.insets = new Insets(4,4,4,4);
		
		gbc.weightx = 0.25;
		gbc.weighty = 1;
		
		catA = new JTextField("Catagorie A: ");
		catA.setEditable(false);
		catA.setBorder(null);
		gbc.gridx = 0;
		gbc.gridy = 0;
		card1.add(catA, gbc);
		
		catB = new JTextField("Catagorie B: ");
		catB.setEditable(false);
		catB.setBorder(null);
		gbc.gridx = 0;
		gbc.gridy = 1;
		card1.add(catB, gbc);
		
		tRatio = new JTextField("Training ratio: ");
		tRatio.setEditable(false);
		tRatio.setBorder(null);
		gbc.gridx = 0;
		gbc.gridy = 2;
		card1.add(tRatio, gbc);
		
		folder1 = new JTextField(20);
		gbc.gridx = 1;
		gbc.gridy = 0;
		card1.add(folder1, gbc);
		
		folder2 = new JTextField(20);
		gbc.gridx = 1;
		gbc.gridy = 1;
		card1.add(folder2, gbc);
		
		ratio = new JTextField(20);
		gbc.gridx = 1;
		gbc.gridy = 2;
		card1.add(ratio, gbc);
		
		f1Button = new JButton("Choose folder");
		gbc.gridx = 2;
		gbc.gridy = 0;
		card1.add(f1Button, gbc);
		
		f2Button = new JButton("Choose folder");
		gbc.gridx = 2;
		gbc.gridy = 1;
		card1.add(f2Button, gbc);
		
		nextButton = new JButton("next");
		gbc.gridx = 2;
		gbc.gridy = 3;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		card1.add(nextButton, gbc);
		
		JPanel card2 = new JPanel(new GridBagLayout());
		
		toBeClassified = new JTextField("Folder to Classify: ");
		toBeClassified.setEditable(false);
		toBeClassified.setBorder(null);
		gbc.gridx = 0;
		gbc.gridy= 0;
		card2.add(toBeClassified, gbc);
		
		folder3 = new JTextField(20);
		gbc.gridx = 1;
		gbc.gridy = 0;
		card2.add(folder3, gbc);
		
		f3Button = new JButton("Choose folder");
		gbc.gridx = 2;
		gbc.gridy = 0;
		card2.add(f3Button, gbc);
		
		classify = new JButton("Classify!");
		gbc.gridx = 1;
		gbc.gridy = 2;
		//gbc.fill = GridBagConstraints.HORIZONTAL;
		card2.add(classify, gbc);
		
		back = new JButton("Back");
		gbc.gridx = 0;
		gbc.gridy = 2;
		card2.add(back, gbc);
		
		
		
		
		
		cards = new JPanel(new CardLayout());
		
		cards.add(card1, "Card 1");
		cards.add(card2, "Card 2");
		
		CardLayout cardLayout = (CardLayout) cards.getLayout();
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
		
		Handler handler = new Handler();
		f1Button.addActionListener(handler);
		f2Button.addActionListener(handler);
		nextButton.addActionListener(handler);
		f3Button.addActionListener(handler);
		back.addActionListener(handler);
		classify.addActionListener(handler);
		
		
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
	
	private class Handler implements ActionListener {
		
		public void actionPerformed(ActionEvent event) {
			if (event.getSource() == f1Button) {
				int option = fc.showOpenDialog((Component)event.getSource());
				if(option == JFileChooser.APPROVE_OPTION) {
					try {
					folder1.setText(fc.getSelectedFile().getAbsolutePath());
					} catch (Exception e) {
						e.printStackTrace(System.out);
					}
				}
			} else if(event.getSource() == f2Button) {
				int option = fc2.showOpenDialog((Component)event.getSource());
				if (option == JFileChooser.APPROVE_OPTION) {
					try{
						folder2.setText(fc2.getSelectedFile().getAbsolutePath());
					} catch (Exception e) {
						e.printStackTrace(System.out);
					}
				}
			} else if(event.getSource() == nextButton) {
				catAFolder = folder1.getText();
				catBFolder = folder2.getText();
				try {
					trainingRatio = Double.parseDouble(ratio.getText());
				} catch (NumberFormatException e) {
					System.out.println("The number was not correct");
				}
				CardLayout cardLayout = (CardLayout) cards.getLayout();
				cardLayout.show(cards, "Card 2");
				
				System.out.println(catAFolder);
				System.out.println(catBFolder);
				System.out.println(trainingRatio);
			} else if(event.getSource() == f3Button) {
				int option = fc3.showOpenDialog((Component)event.getSource());
				if(option == JFileChooser.APPROVE_OPTION) {
					try {
					folder3.setText(fc3.getSelectedFile().getAbsolutePath());
					} catch (Exception e) {
						e.printStackTrace(System.out);
					}
				}
			} else if(event.getSource() == back) {
				CardLayout cardLayout = (CardLayout) cards.getLayout();
				cardLayout.show(cards, "Card 1");
			} else if(event.getSource() == classify) {
				Results results = new Results();
				results.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				results.setSize(650, 300);
				results.setVisible(true);
			}
		}
		
	}

}

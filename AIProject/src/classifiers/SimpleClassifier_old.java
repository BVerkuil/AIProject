package classifiers;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Stream;

import javax.swing.JFrame;

import Gui.GUI;

public class SimpleClassifier_old {

	public HashMap<String, String> dataSet;
	public HashMap<String, HashMap<String, Double>> featureCountPerType = new HashMap<String, HashMap<String, Double>>();
	public double trainRatio;
	public HashMap<String, Integer> totalFilesPerType = new HashMap<String, Integer>();
	public HashMap<String, Integer> filesIndexedPerType = new HashMap<String, Integer>(); /*Used to keep track how many files are indexed already, use in combination with trainRation to decide whether to train or test a file*/
	//List of words that do not need to go in the featureIndexes
	public ArrayList<String> blackList = new ArrayList<String>(Arrays.asList("in", "a", "the"));

	int totalFilesChecked;
	int correctlyIdentified;

	/**
	 * @param dataSet
	 * @param trainRatio
	 * 
	 * Method that is called to train using this classifier. Two parameters are used. 
	 * First a HashMap containing the type and location (for example <"Ham", "../AIProject/Ham">, <"Spam", "../AIProject/Spam">, etc
	 * Secondly the trainRatio. This means the amount of files that will be used for training, and for testing
	 * For example, a trainRatio of 0.7 means that the first 70% of the files are used to train, and the last 30% to test.
	 * 
	 */
	public void trainClassifier(HashMap<String, String> dataSet, double trainRatio) {
		this.dataSet = dataSet;
		this.trainRatio = trainRatio;
		//Do something for every type
		for (String type : dataSet.keySet()) {
			featureCountPerType.put(type, new HashMap<String, Double>());
			totalFilesPerType.put(type, new File(dataSet.get(type)).listFiles().length);
			filesIndexedPerType.put(type, 0);
			buildIndexes(type, dataSet.get(type));
		}
		normalizeIndexes();
		System.out.println(blackList);
	}

	/**
	 * @param type
	 * @param fileLocation
	 * 
	 * Given a type and fileLocation, go through all files in that folder. For each file
	 */
	public void buildIndexes(String type, String fileLocation) {
		try (Stream<Path> paths = Files.walk(Paths.get(fileLocation))) {
			paths.forEach(filePath -> {
				if (Files.isRegularFile(filePath)) {
					//Code above is a for-each loop for all the files on the fileLocation
					//Code only gets executed for files that the classifier uses to train.
					if ((double) filesIndexedPerType.get(type) / totalFilesPerType.get(type) < trainRatio) {
						analyzeWords(filePath, type);
						filesIndexedPerType.put(type, filesIndexedPerType.get(type) + 1);
					}
				}
			});
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * @param filePath
	 * @param type
	 * 
	 * Given the filePath of a file, and the type of the file, go through all the words(features) in the file and add them to the 
	 * feature list.
	 */
	public void analyzeWords(Path filePath, String type) {
		HashMap<String, Double> currentIndex = featureCountPerType.get(type);
		Scanner scanner;
		try {
			scanner = new Scanner(new File(filePath.toString()));
			while (scanner.hasNext()) {
				String nextWord = scanner.next();
				nextWord = normalizeWord(nextWord);
				if (!nextWord.equals("")) {
					if (!currentIndex.keySet().contains(nextWord)) {
						currentIndex.put(nextWord, 1.0);
					} else {
						currentIndex.put(nextWord, currentIndex.get(nextWord) + 1);
					}
				}
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public String normalizeWord(String word) {
		word = word.replaceAll("[^a-zA-Z]", "").toLowerCase();
		if (!blackList.contains(word)) {
			return word;
		} else {
			return "";
		}
	}

	public void normalizeIndexes() {
		for (String type : totalFilesPerType.keySet()) {
			HashMap<String, Double> currentIndex = featureCountPerType.get(type);
			for (String word : currentIndex.keySet()) {
				// Divide current number by the amount of messages of that type
				currentIndex.put(word, (double) currentIndex.get(word) / totalFilesPerType.get(type));
			}
		}
	}

	/**
	 * @param dataSet
	 * Test the accuracy
	 */
	public double testAccuracy(String type) {
		totalFilesChecked = 0;
		correctlyIdentified = 0;
		//Reset filesIndexedPerType
		for (String typeToReset : filesIndexedPerType.keySet()) {
			filesIndexedPerType.put(typeToReset, 0);
		}
		//Go through all the files that needs to be tested
		if (dataSet.containsKey(type)) {
			String fileLocation = dataSet.get(type);
			try (Stream<Path> paths = Files.walk(Paths.get(fileLocation))) {
				paths.forEach(filePath -> {
					if (Files.isRegularFile(filePath)) {
						//Code above is a for-each loop for all the files on the fileLocation of certain type
						//Code only gets executed for files that the classifier uses to test.
						if ((double) filesIndexedPerType.get(type) / totalFilesPerType.get(type) > trainRatio) {
							if (identifyFileType(new File(filePath.toString())).equals(type)) {
								correctlyIdentified++;
							}
							totalFilesChecked++;
							filesIndexedPerType.put(type, filesIndexedPerType.get(type) + 1);
						} else {
							filesIndexedPerType.put(type, filesIndexedPerType.get(type) + 1);
						}
					}
				});
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return (double) correctlyIdentified / totalFilesChecked;
	}

	public String identifyFileType(File file) {
		//For every word, check which type it fits best, and add the key's value +1. At the end, the type with the highest score is selected.
		HashMap<String, Integer> typePreference = new HashMap<String, Integer>();
		try {
			Scanner scanner = new Scanner(file);
			while (scanner.hasNext()) {
				String word = scanner.next();
				//current feature score
				double currentMax = 0;
				String currentTypeGuess = "";
				for (String type : featureCountPerType.keySet()) {
					if (featureCountPerType.get(type).keySet().contains(word)) {
						if (featureCountPerType.get(type).get(word) > currentMax) {
							currentMax = featureCountPerType.get(type).get(word);
							currentTypeGuess = type;
						}
					}
				}
				if (typePreference.containsKey(currentTypeGuess)) {
					typePreference.put(currentTypeGuess, typePreference.get(currentTypeGuess) + 1);
				} else {
					typePreference.put(currentTypeGuess, 0);
				}
			}
			//Gone through all the words, check which type has the highest integer in typePreference
			if (!typePreference.isEmpty()) {
				double currentMax = 0;
				String currentTypeGuess = "";
				for (String type : typePreference.keySet()) {
					if (typePreference.get(type) > currentMax) {
						currentMax = typePreference.get(type);
						currentTypeGuess = type;
					}
				}
				return currentTypeGuess;
			} else {
				return "none";
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "error";
	}

}

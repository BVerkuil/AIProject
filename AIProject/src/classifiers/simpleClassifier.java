package classifiers;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Stream;

public class simpleClassifier {

	public HashMap<String, String> dataSet;
	public HashMap<String, HashMap<String, Double>> featureCountPerType = new HashMap<String, HashMap<String, Double>>();
	public double trainRatio;
	public HashMap<String, Integer> totalFilesPerType = new HashMap<String, Integer>();
	public HashMap<String, Integer> filesIndexedPerType = new HashMap<String, Integer>(); /*Used to keep track how many files are indexed already, use in combination with trainRation to decide whether to train or test a file*/

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
		testAccuracy(dataSet);

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
	public void testAccuracy(HashMap<String, String> dataSet) {
		// Check accuracy for every type
		for (String type : dataSet.keySet()) {
			filesIndexedPerType.put(type, 0);
			try (Stream<Path> paths = Files.walk(Paths.get(dataSet.get(type)))) {
				paths.forEach(filePath -> {
					if (Files.isRegularFile(filePath)) {
						// Do something with each file if the ttestratio is
						// reached
						if ((double) filesIndexedPerType.get(type) / totalFilesPerType.get(type) > trainRatio) {
							// File is to be tested
							HashMap<String, Integer> typePreference = new HashMap<String, Integer>();
							for (String typeToFill : featureCountPerType.keySet()) {
								typePreference.put(typeToFill, 0);
							}
							try {
								Scanner scanner = new Scanner(new File(filePath.toString()));
								while (scanner.hasNext()) {
									String nextWord = scanner.next();
									// Check which type fits best
									HashMap<String, Double> typeWordScores = new HashMap<String, Double>();
									for (String possibleType : featureCountPerType.keySet()) {
										if (featureCountPerType.get(possibleType).containsKey(nextWord)) {
											typeWordScores.put(possibleType,
													featureCountPerType.get(possibleType).get(nextWord));
										}
									}
								}
								// Check which was best
								String bestType = "";
								double currentBest = 0;
								for (String typeToCheck : typeWordScores.keySet()) {
									if (typeWordScores.get(typeToCheck) > currentBest) {
										currentBest = typeWordScores.get(typeToCheck);
										bestType = typeToCheck;
									}
								}
								if (!bestType.equals("")) {
									typePreference.put(bestType, typePreference.get(bestType) + 1);
								}

							} catch (FileNotFoundException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						} else {
							filesIndexedPerType.put(type, filesIndexedPerType.get(type) + 1);
						}

					}
				});
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

		}
	}

}

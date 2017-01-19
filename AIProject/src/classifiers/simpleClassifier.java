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

	public static simpleClassifier sC = new simpleClassifier();
	public static HashMap<String, Integer> hamIndex = new HashMap<String, Integer>();
	public static HashMap<String, Integer> spamIndex = new HashMap<String, Integer>();
	float amountOfFiles = 0;
	float correctlyClassifiedFiles = 0;

	public static void main(String[] args) {
		sC.readFiles("Ham");
		sC.readFiles("Spam");
		sC.classifyMessages("Spam");
	}

	public void readFiles(String type) {
		try (Stream<Path> paths = Files.walk(Paths.get("../AIProject/" + type))) {
			paths.forEach(filePath -> {
				if (Files.isRegularFile(filePath)) {
					// Do something with the file
					sC.analyzeWords(filePath, type);
				}
			});
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void analyzeWords(Path filePath, String type) {
		HashMap<String, Integer> currentIndex;
		if (type.equals("Ham")) {
			currentIndex = hamIndex;
		} else {
			currentIndex = spamIndex;
		}
		Scanner scanner;
		try {
			scanner = new Scanner(new File(filePath.toString()));
			while (scanner.hasNext()) {
				String nextLine = scanner.next();
				if (!nextLine.equals("")) {
					// Do something with the Ham word
					if (!currentIndex.keySet().contains(nextLine)) {
						currentIndex.put(nextLine, 1);
					} else {
						currentIndex.put(nextLine, currentIndex.get(nextLine) + 1);
					}
				}
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	// Print the top i of most common words
	public void sortWords(HashMap<String, Integer> wordIndex) {
		Object[] a = wordIndex.entrySet().toArray();
		Arrays.sort(a, new Comparator() {
			public int compare(Object o1, Object o2) {
				return ((Map.Entry<String, Integer>) o2).getValue()
						.compareTo(((Map.Entry<String, Integer>) o1).getValue());
			}
		});
		int i = 0;
		for (Object e : a) {
			String word = ((Map.Entry<String, Integer>) e).getKey();
			Integer amount = ((Map.Entry<String, Integer>) e).getValue();
			System.out.println(word + " : " + amount);
		}
	}

	public int classifyMessages(String type) {
		try (Stream<Path> paths = Files.walk(Paths.get("../AIProject/" + type))) {
			paths.forEach(filePath -> {
				if (Files.isRegularFile(filePath)) {
					// Guess if is is SPAM or HAM
					try {
						Scanner scanner = new Scanner(new File(filePath.toString()));
						int amountOfWordsSpam = 0;
						int amountOfWordsHam = 0;
						while (scanner.hasNext()) {
							String next = scanner.next();
							int spamIndicator = 0;
							int hamIndicator = 0;
							if (spamIndex.containsKey(next)) {
								spamIndicator = spamIndex.get(next);
							} if (hamIndex.containsKey(next)) {
								hamIndicator = hamIndex.get(next);
							}
							if (spamIndicator > hamIndicator) {
								amountOfWordsSpam++;
							} if (spamIndicator < hamIndicator) {
								amountOfWordsHam++;
							}
						}
						if (amountOfWordsSpam > amountOfWordsHam && type.equals("Spam")) {
							System.out.println("Correct| Guessed:Spam Was:" + type + " (Words identified as spam: " + amountOfWordsSpam + ". Identified as ham: " + amountOfWordsHam + ". Filename:" + filePath.toString() + ")");
							correctlyClassifiedFiles++;
							amountOfFiles++;
						} else if(amountOfWordsSpam < amountOfWordsHam && type.equals("Ham")) {
							System.out.println("Correct| Guessed:Ham Was:" + type + " (Words identified as spam: " + amountOfWordsSpam + ". Identified as ham: " + amountOfWordsHam + ". Filename:" + filePath.toString() + ")");
							correctlyClassifiedFiles++;
							amountOfFiles++;
						} else if (amountOfWordsSpam > amountOfWordsHam && type.equals("Ham")) {
							System.out.println("Incorrect| Guessed:Spam Was:" + type + " (Words identified as spam: " + amountOfWordsSpam + ". Identified as ham: " + amountOfWordsHam + ". Filename:" + filePath.toString() + ")");
							amountOfFiles++;
						} else if (amountOfWordsSpam < amountOfWordsHam && type.equals("Spam")) {
							System.out.println("Incorrect| Guessed:Ham Was:" + type + " (Words identified as spam: " + amountOfWordsSpam + ". Identified as ham: " + amountOfWordsHam + ". Filename:" + filePath.toString() + ")");
							amountOfFiles++;
						}

					} catch (FileNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			});
			System.out.println("Total accuracy: " + correctlyClassifiedFiles/amountOfFiles*100 );
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 1;
	}

}

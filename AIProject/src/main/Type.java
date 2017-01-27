package main;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Stream;

import classifiers.correctClassifier;

public class Type {

	public List<Document> documents = new ArrayList<Document>();
	public List<Document> documentsNotTrained = new ArrayList<Document>();
	public HashMap<String, Integer> featureMap = new HashMap<String, Integer>();
	public int totalFeatures;
	public String name;
	public String folderLocation;
	public correctClassifier classifer;
	public int numberOfFiles;
	public int currentFileNumber;
	public List<String> allFeatures = new ArrayList<String>();
	public int vocabFeatures;

	public Type(String name, String folderLocation, correctClassifier classifier, double trainingsRatio) {
		numberOfFiles = new File(folderLocation).list().length;
		this.classifer = classifier;
		this.folderLocation = folderLocation;
		this.name = name;
		//Add all Documents at folder location to the "documents" list
		currentFileNumber = 0;
		try (Stream<Path> paths = Files.walk(Paths.get(folderLocation))) {
			paths.forEach(filePath -> {
				if (Files.isRegularFile(filePath) && ((double) currentFileNumber / numberOfFiles < trainingsRatio)) {
					documents.add(new Document(new File(filePath.toString()).getName(), filePath.toString(), this,
							classifier));
				} else if(Files.isRegularFile(filePath)){
					documentsNotTrained.add(new Document(new File(filePath.toString()).getName(), filePath.toString(), this,
							classifier));
				}
				currentFileNumber++;
			});
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void buildFeatureMap() {
		totalFeatures = 0;
		allFeatures.clear();
		featureMap.clear();
		for (Document document : documents) {
			for (String feature : document.features) {
				allFeatures.add(feature);
				totalFeatures++;
				if (!featureMap.keySet().contains(feature)) {
					featureMap.put(feature, 1);
				} else {
					featureMap.put(feature, featureMap.get(feature) + 1);
				}
			}
		}
	}

	@Override
	public String toString() {
		String result = "";
		for (Document document : documents) {
			result = result + document.toString() + "\n";
		}
		return result;
	}
	
	public void addDocument(Document newDoc) {
			documents.add(newDoc);
	}
}
package main;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import classifiers.correctClassifier;

public class Type {

	public List<Document> documents = new ArrayList<Document>();
	public List<String> vocabulary = new ArrayList<String>();
	public String name;
	public String folderLocation;
	public correctClassifier classifer;

	public Type(String name, String folderLocation, correctClassifier classifier) {
		this.classifer = classifier;
		this.folderLocation = folderLocation;
		this.name = name;
		//Add all Documents at folder location to the "documents" list
		try (Stream<Path> paths = Files.walk(Paths.get(folderLocation))) {
			paths.forEach(filePath -> {
				if (Files.isRegularFile(filePath)) {
					documents.add(new Document(new File(filePath.toString()).getName(), filePath.toString(), this, classifier));
				}
			});
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public String toString() {
		String result = "";
		for(Document document: documents) {
			result = result + document.toString() +"\n";
		}
		return result;
	}
}
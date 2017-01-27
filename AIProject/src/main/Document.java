package main;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import classifiers.correctClassifier;

public class Document {
	
	public String name;
	public String location;
	public List<String> features = new ArrayList<String>();
	public File file;
	public Type type;
	public correctClassifier classifier;

	public Document(String name, String location, Type type, correctClassifier classifier) {
		this.classifier = classifier;
		this.type = type;
		file = new File(location);
		this.name = name;
		this.location = location;	
		Scanner scanner;
		try {
			scanner = new Scanner(file);
			while(scanner.hasNext()){
				String feature = scanner.next().replaceAll("[^a-zA-Z]", "").toLowerCase();
				if(!feature.equals("")) {
					features.add(feature);
				}
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public List<String> getFeatures(){
		return features;
	}
	
	public String getName() {
		return name;
	}
	
	@Override
	public String toString() {
		return "Document name: " + name + ". Features: " + features.toString();
	}
	
}

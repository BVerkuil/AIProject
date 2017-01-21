package main;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Document {
	
	public String name;
	public String location;
	public List<String> features = new ArrayList<String>();
	public File file;

	public Document(String name, String location) {
		file = new File(location);
		this.name = name;
		this.location = location;	
		Scanner scanner;
		try {
			scanner = new Scanner(file);
			while(scanner.hasNext()){
				String feature = scanner.next().replaceAll("[^a-zA-Z]", "").toLowerCase();
				if(!features.contains(feature)) {
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
	
}

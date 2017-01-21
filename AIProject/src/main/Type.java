package main;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class Type {

	public List<Document> documents = new ArrayList<Document>();
	public String name;
	public String folderLocation;

	public Type(String name, String folderLocation) {
		this.folderLocation = folderLocation;
		this.name = name;
		//Add all Documents at folder location to the "documents" list
		try (Stream<Path> paths = Files.walk(Paths.get(folderLocation))) {
			paths.forEach(filePath -> {
				if (Files.isRegularFile(filePath)) {
					documents.add(new Document(new File(filePath.toString()).getName(), filePath.toString()));
				}
			});
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
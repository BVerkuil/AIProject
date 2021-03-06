package classifiers;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeMap;
import java.util.stream.Stream;

import main.ChiSquare;
import main.Document;
import main.Type;
import main.Vocabulary;

public class correctClassifier {

	public List<Type> types = new ArrayList<Type>();
	public Set<String> features = new HashSet<String>();
	public double trainingRatio;
	public int totalAmountOfDocuments = 0;
	public List<Document> wrongClassified = new ArrayList<Document>();
	public List<String> blackList = new ArrayList<String>(
			Arrays.asList("by", "an", "you", "it", "from", "have", "if", "the", "a"));
	public Vocabulary vocabulary = new Vocabulary(this);
	public int vocabSize;

	public ChiSquare chiSquare;

	public correctClassifier(double trainingsRatio) {
		this.trainingRatio = trainingsRatio;
	}

	public void addType(String name, String folderLocation) {
		types.add(new Type(name, folderLocation, this, trainingRatio));
		//Add all features of the new type to the list of all features
		for (Type type : types) {
			if (type.name.equals(name)) {
				for (Document document : type.documents) {
					features.addAll(document.features);
				}
			}
			type.buildFeatureMap();
			totalAmountOfDocuments = totalAmountOfDocuments + type.documents.size();
		}
	}

	public void selectVocabulary() {
		vocabulary.features.clear();
		TreeMap<Float, String> chiSquarePerFeature = new TreeMap<Float, String>();
		chiSquare = new ChiSquare(this);
		for (String feature : features) {
			chiSquarePerFeature.put(chiSquare.calculate(feature), feature);
		}
		for (int i = 0; i < vocabSize; i++) {
			boolean foundNext = false;
			while (!foundNext) {
				if (!blackList.contains(chiSquarePerFeature.get(chiSquarePerFeature.lastKey()))) {
					vocabulary.addFeature(chiSquarePerFeature.get(chiSquarePerFeature.lastKey()));
					chiSquarePerFeature.remove(chiSquarePerFeature.lastKey());
					foundNext = true;
				} else {
					chiSquarePerFeature.remove(chiSquarePerFeature.lastKey());
				}
			}
		}
		for (Type type : types) {
			List<String> retained = new ArrayList<String>(type.allFeatures);
			retained.retainAll(vocabulary.features);
			type.vocabFeatures = retained.size();
		}
		vocabulary.calculateValues();
		System.out.println(vocabulary.features);
	}

	public double testClassifier() {
		wrongClassified.clear();
		int total = 0;
		int right = 0;
		//Find Documents to test
		for (Type type : types) {
			List<Document> toRemove = new ArrayList<Document>();
			for (Document document : type.documentsNotTrained) {
				if (this.classifyDocument(document).equals(type)) {
					type.documents.add(document);
					toRemove.add(document);
					right++;
					total++;
				} else {
					total++;
					wrongClassified.add(document);
				}
			}
			//			type.documentsNotTrained.removeAll(toRemove);
		}
		return ((double) right / total);
	}

	public Type classifyDocument(Document document) {
		//Remove words not in vocabulary
		List<String> vocabInDocument = new ArrayList<String>(document.features);
		vocabInDocument.retainAll(vocabulary.features);
		double total = 0;
		double previous = -100000;
		Type guess = types.get(0);
		for (Type type : types) {
			total = 0;
			for (String feature : vocabInDocument) {
				total = total + vocabulary.getValueForType(feature, type);
			}
			total = Math.log(((double) type.numberOfFiles / totalAmountOfDocuments)) + total;
			if (total > previous) {
				previous = total;
				guess = type;
			}
			//			System.out.print("Value for " + type.name + ": "+total + ".  ");
		}
		//		System.out.println("");
		return guess;
	}

	@Override
	public String toString() {
		String result = "";
		for (Type type : types) {
			result = result + "Documents in type: " + type.name + "\n" + "-----------" + "\n";
			result = result + type.toString() + "-----------" + "\n";
		}
		return result;
	}

	public void addDocumentAfterFeedback(Type newType, Document document) {
		for (Type type : types) {
			if (type.documentsNotTrained.contains(document)) {
				type.documentsNotTrained.remove(document);
			}
		}
		newType.documents.add(document);
	}

	public void rebuildClassifier() {
		System.out.println("rebuilding");
		for (Type type : types) {
			type.buildFeatureMap();
		}
		selectVocabulary();
		System.out.println(testClassifier());
	}

	public void setVocabularySize(int size) {
		vocabSize = size;
	}

	public void resetClassifier() {
		types.clear();
		features.clear();
		trainingRatio = 0;
		totalAmountOfDocuments = 0;
		wrongClassified.clear();
		vocabulary = new Vocabulary(this);
		vocabSize = 0;
	}

	public void addDocuments(Type type, String location) {
		try (Stream<Path> paths = Files.walk(Paths.get(location))) {
			paths.forEach(filePath -> {
				type.addDocument(
						new Document(new File(filePath.toString()).getName(), filePath.toString(), type, this));
			});
		} catch (IOException e) {
			e.printStackTrace();
		}
		type.buildFeatureMap();
	}
}

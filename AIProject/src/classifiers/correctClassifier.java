package classifiers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.TreeMap;

import main.ChiSquare;
import main.Document;
import main.Type;

public class correctClassifier {

	public List<Type> types = new ArrayList<Type>();
	public List<String> features = new ArrayList<String>();
	public List<String> vocabulary = new ArrayList<String>();
	public double trainingRatio;
	public int totalAmountOfDocuments = 0;

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
					for (String feature : document.features) {
						if (!features.contains(feature)) {
							features.add(feature);
						}
					}
				}
				type.buildFeatureMap();
				totalAmountOfDocuments = totalAmountOfDocuments + type.documents.size();
			}
		}
	}

	public void selectVocabulary(int featureListSize) {
		TreeMap<Float, String> chiSquarePerFeature = new TreeMap<Float, String>();
		chiSquare = new ChiSquare(this);
		System.out.println(features.size());
		for (String feature : features) {
			chiSquarePerFeature.put(chiSquare.calculate(feature), feature);
		}
		for (int i = 0; i < featureListSize; i++) {
			vocabulary.add(chiSquarePerFeature.get(chiSquarePerFeature.lastKey()));
			chiSquarePerFeature.remove(chiSquarePerFeature.lastKey());
		}
		System.out.println(vocabulary);
	}

	public void testClassifier() {
		int total = 0;
		int right = 0;
		int wrong = 0;
		//Find Documents to test
		for (Type type : types) {
			for (Document document : type.documentsNotTrained) {
				if(this.classifyDocument(document).equals(type)){
					System.out.println("Correct");
					right++;
					total++;
				} else {
					System.out.println("incorrect, guessed: " + this.classifyDocument(document).name + ". Was " + type.name + "name: " + document.name);
					wrong++;
					total++;
				}
			}
		}
		System.out.println((double)right/total);
	}

	public Type classifyDocument(Document document) {
		//Remove words not in vocabulary
		List<String> vocabInDocument = new ArrayList<String>(document.features);
		vocabInDocument.retainAll(vocabulary);
		HashMap<Type, Double> typeChances = new HashMap<Type, Double>();
		for (Type type : types) {
			double k = 1;
			double sum = 0;
			double pC = (double)type.documents.size()/totalAmountOfDocuments;
			double pNotC = 1 - pC;
			double totalWordsInC = 0; 
			for (String feature : vocabInDocument) {
//				double pWordGivenC = (type.featureMap.get(feature) + k);
				double pWordGivenNotC = 0;
				sum = sum + 1;
			}
			double chanceOfType = Math.log((double)type.documents.size()/totalAmountOfDocuments) / Math.log(2);
			typeChances.put(type, chanceOfType + sum);
		}
		System.out.println(typeChances.values());
		Type result = types.get(0);
		double max = -8000;
		for(Type type: typeChances.keySet()){
			if(typeChances.get(type) > max) {
				result = type;
				max = typeChances.get(type);
			}
		}
		return types.get(0);
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

}

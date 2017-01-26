package classifiers;

import java.util.ArrayList;
import java.util.List;
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
	public List<Document> wrongClassified = new ArrayList<Document>();

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

	public double testClassifier() {
		int total = 0;
		int right = 0;
		int wrong = 0;
		//Find Documents to test
		for (Type type : types) {
			for (Document document : type.documentsNotTrained) {
				if (this.classifyDocument(document).equals(type)) {
//					System.out.println("Correct");
					right++;
					total++;
				} else {
//					System.out.println("incorrect, guessed: " + this.classifyDocument(document).name + ". Was "
//							+ type.name + "name: " + document.name);
					wrong++;
					total++;
					wrongClassified.add(document);
				}
//				System.exit(0);
			}
		}
		System.out.println((double) right / total);
		return((double) right / total);
	}

	public Type classifyDocument(Document document) {
		//Remove words not in vocabulary
		List<String> vocabInDocument = new ArrayList<String>(document.features);
		vocabInDocument.retainAll(vocabulary);
//		System.out.println(vocabInDocument);
		Type result = types.get(0);
		double currentmax = -100000;
		for (Type type : types) {
			double sum = 0;
			double base = Math.log((double)type.documents.size()/totalAmountOfDocuments) / Math.log(2);
			for (String feature : vocabInDocument) {
				sum = sum * calculateChance(feature, type);
			}
			if( (base + Math.log(sum)) > currentmax) {
				result = type;
			}
//			System.out.println("Value: "+ (base * sum) + ". Type: " + type.name);
		}
		return result;
	}

	public double calculateChance(String feature, Type type) {
		int featureOccuranceInClass = 0;
		if (type.featureMap.containsKey(feature)) {
			featureOccuranceInClass = type.featureMap.get(feature);
		}
		int k = 1;
		int totalWordsInClass = type.totalFeatures;
		int vocabSize = vocabulary.size();
		double result;
		result = Math.log((double) (featureOccuranceInClass + k) / (totalWordsInClass + (k * vocabSize))) / Math.log(2);
		return result;
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

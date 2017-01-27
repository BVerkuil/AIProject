package main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import classifiers.correctClassifier;

public class Vocabulary {

	public correctClassifier classifier;
	public List<String> features = new ArrayList<String>();
	public HashMap<String, HashMap<Type, Double>> featureValues = new HashMap<String, HashMap<Type, Double>>();

	public Vocabulary(correctClassifier classifier) {
		this.classifier = classifier;
	}

	public void addFeature(String feature) {
		features.add(feature);
	}

	public void calculateValues() {
		for (String feature : features) {
			for (Type type : classifier.types) {
				int featureOccurrenceInClass = 0;
				if (type.featureMap.containsKey(feature)) {
					featureOccurrenceInClass = type.featureMap.get(feature);
				}
				int k = 1;
				int Nc = type.vocabFeatures;
//				System.out.println(Nc);
				int V = classifier.vocabulary.features.size();

				double result = Math.log((double) (featureOccurrenceInClass + k) / (Nc + (k * V)));
				if (!featureValues.keySet().contains(feature)) {
					featureValues.put(feature, new HashMap<Type, Double>());
				}
				featureValues.get(feature).put(type, result);
			}
		}
	}

	public double getValueForType(String feature, Type type) {
		double result = 0;
		result = featureValues.get(feature).get(type);
		return result;
	}

}

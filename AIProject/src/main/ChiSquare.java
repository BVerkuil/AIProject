package main;

import classifiers.correctClassifier;

public class ChiSquare {

	correctClassifier classifier;

	public ChiSquare(correctClassifier classifier) {
		this.classifier = classifier;
	}

	public boolean calculate(Feature feature) {
		
		
		int wInC1 = 0;
		int wNotInC1 = 0;
		int wInC2 = 0;
		int wNotInC2 = 0;
		int totalW;
		int totalNotW;
		int totalC1;
		int totalC2;
		Type C1 = classifier.types.get(0);
		Type C2 = classifier.types.get(1);
		for (Document document : C1.documents) {
			if (document.features.contains(feature)) {
				wInC1++;
			} else {
				wNotInC1++;
			}
		}
		for (Document document : C2.documents) {
			if (document.features.contains(feature)) {
				wInC2++;
			} else {
				wNotInC2++;
			}
		}
		totalW = wInC1 + wInC2;
		totalNotW = wNotInC1 + wNotInC2;
		totalC1 = wInC1+wNotInC1;
		totalC2 = wInC2 + wNotInC2;
		
		
		
		System.out.println();
		return true;
	}

}

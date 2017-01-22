package main;

import classifiers.correctClassifier;

public class Feature {

	public String word;

	public Feature(String word) {
		this.word = word;
	}

	@Override
	public String toString() {
		return word;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Feature) {
			return word.equals(((Feature) obj).word);
		} else {
			return super.equals(obj);
		}
	}
}

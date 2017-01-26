package main;

import classifiers.correctClassifier;

public class ChiSquare {

	correctClassifier classifier;
	public int amountOfTypes;

	public ChiSquare(correctClassifier classifier) {
		this.classifier = classifier;
		amountOfTypes = classifier.types.size();
	}

	public float calculate(String feature) {

		//	________|C0  |C1  |W	
		//	W		|M0,0|M1,0|W0
		//	not W	|M0,1|M1,1|W1
		//	C		|tC0 |tC1 |N

		int[][] table = new int[amountOfTypes][2];

		//Make table
		for (int i = 0; i < amountOfTypes; i++) {
			if (classifier.types.get(i).featureMap.keySet().contains((feature))) {
				table[i][0] = classifier.types.get(i).featureMap.get(feature);
			} else {
				table[i][0] = 0;
			}
			table[i][1] = classifier.types.get(i).documents.size() - table[i][0];
		}

		//Total files
		int N = 0;
		for (int i = 0; i < amountOfTypes; i++) {
			for (int j = 0; j < 2; j++) {
				N = N + table[i][j];
			}
		}

		//Caclculate chiSquare
		float result = 0;
		for (int i = 0; i < table.length; i++) {
			for (int j = 0; j < table[i].length; j++) {
				int Wi = 0;
				for (int k = 0; k < table.length; k++) {
					Wi = Wi + table[k][j];
				}
				int Cj = 0;
				for (int l = 0; l < 2; l++) {
					Cj = Cj + table[i][l];
				}
				//chiSquare formula
				int Mij = table[i][j];
				float Eij = (float) (Wi * Cj) / N;
				float add = (float) (Math.pow(Mij - Eij, 2)) / Eij;
				;
				result = result + add;
			}
		}

		//		if (result > 70) {
		//			System.out.println("ChiSquare = " + result + " feature: " + feature);
		//			for (int i = 0; i < amountOfTypes; i++) {
		//				for (int j = 0; j < 2; j++) {
		//					System.out.println("M" + i + "," + j + "= " + table[i][j]);
		//				}
		//			}
		//			System.out.println("\n");
		//		}

		return result;
	}

}

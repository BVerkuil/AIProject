package classifiers;

import java.util.ArrayList;
import java.util.List;

import main.ChiSquare;
import main.Document;
import main.Feature;
import main.Type;

public class correctClassifier {

	public List<Type> types = new ArrayList<Type>();
	public List<Feature> features = new ArrayList<Feature>();

	public ChiSquare chiSquare = new ChiSquare(this);

	public correctClassifier() {
	}

	public void addType(String name, String folderLocation) {
		types.add(new Type(name, folderLocation, this));
		//Add all features of the new type to the list of all features
		for (Type type : types) {
			if (type.name.equals(name)) {
				for (Document document : type.documents) {
					for (Feature feature : document.features) {
						if (!features.contains(feature)) {
							features.add(feature);
						}
					}
				}
			}
		}
	}

	public void selectFeatures() {
		for (Feature feature : features) {
			chiSquare.calculate(feature);
			System.exit(0);
		}
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

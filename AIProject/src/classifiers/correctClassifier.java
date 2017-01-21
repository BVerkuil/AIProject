package classifiers;

import java.util.ArrayList;
import java.util.List;
import main.Type;

public class correctClassifier {
	
	public List<Type> types = new ArrayList<Type>();
	
	public correctClassifier() {
	}
	
	public void addType(String typeFolder, String typeName) {
		types.add(new Type(typeFolder, typeName));
	}
	
}

package it.amattioli.dominate.properties;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class PropertyDependencies {
	
	private Map<String, Set<String>> dependencies = new HashMap<String, Set<String>>();
	
	public Set<String> getDependencies(String propertyName) {
		Set<String> result = dependencies.get(propertyName);
		if (result == null) {
			result = Collections.emptySet();
		}
		return result;
	}
	
	public void addDependency(String from, String to) {
		Set<String> dep = dependencies.get(from);
		if (dep == null) {
			dep = new HashSet<String>();
			dependencies.put(from, dep);
		}
		dep.add(to);
	}
}

package it.amattioli.dominate.validation;

import java.util.HashMap;
import java.util.Map;

public class DefaultConstraint implements Constraint {
	private String name;
	private Map<String, Object> parameters = new HashMap<String, Object>();
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void addParameter(String name, Object value) {
		parameters.put(name, value);
	}
	
	public Object getParameter(String name) {
		return parameters.get(name);
	}
}

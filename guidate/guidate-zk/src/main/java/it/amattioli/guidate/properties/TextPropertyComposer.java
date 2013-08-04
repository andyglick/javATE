package it.amattioli.guidate.properties;

public class TextPropertyComposer extends InputPropertyComposer {

	public TextPropertyComposer() {
		setPropertyClass(String.class);
	}
	
	public TextPropertyComposer(String propertyName) {
		super(propertyName);
		setPropertyClass(String.class);
	}
	
}

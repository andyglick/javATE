package it.amattioli.guidate.properties;

import it.amattioli.guidate.converters.NullConverter;

public class IntPropertyComposer extends InputPropertyComposer {

	public IntPropertyComposer() {
		setPropertyClass(Integer.class);
		setConverter(new NullConverter());
	}
	
	public IntPropertyComposer(String propertyName) {
		super(propertyName);
		setPropertyClass(Integer.class);
		setConverter(new NullConverter());
	}
	
}

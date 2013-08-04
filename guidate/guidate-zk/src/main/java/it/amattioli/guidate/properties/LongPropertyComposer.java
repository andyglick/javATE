package it.amattioli.guidate.properties;

import it.amattioli.guidate.converters.NullConverter;

public class LongPropertyComposer extends InputPropertyComposer {

	public LongPropertyComposer() {
		setPropertyClass(Long.class);
		setConverter(new NullConverter());
	}
	
	public LongPropertyComposer(String propertyName) {
		super(propertyName);
		setPropertyClass(Long.class);
		setConverter(new NullConverter());
	}
	
}

package it.amattioli.guidate.properties;

import it.amattioli.guidate.converters.NullConverter;

import java.util.Date;


public class DatePropertyComposer extends InputPropertyComposer {

	public DatePropertyComposer() {
		setPropertyClass(Date.class);
		setConverter(new NullConverter());
	}
	
	public DatePropertyComposer(String propertyName) {
		super(propertyName);
		setPropertyClass(Date.class);
		setConverter(new NullConverter());
	}
	
}

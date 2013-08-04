package it.amattioli.guidate.properties;

import it.amattioli.guidate.converters.NullConverter;

import java.math.BigDecimal;

public class DecimalPropertyComposer extends InputPropertyComposer {
	
	public DecimalPropertyComposer() {
		//setPropertyClass(BigDecimal.class);
		//setConverter(new NullConverter());
	}
	
	public DecimalPropertyComposer(String propertyName) {
		super(propertyName);
		//setPropertyClass(BigDecimal.class);
		//setConverter(new NullConverter());
	}
	
}

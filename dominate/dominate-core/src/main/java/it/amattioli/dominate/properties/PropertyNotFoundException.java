package it.amattioli.dominate.properties;

public class PropertyNotFoundException extends RuntimeException {

	public PropertyNotFoundException(String propertyName, Object bean) {
		super("Property "+propertyName+" not found on bean "+bean);
	}
	
}

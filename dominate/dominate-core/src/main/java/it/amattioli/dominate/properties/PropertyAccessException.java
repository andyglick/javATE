package it.amattioli.dominate.properties;

public class PropertyAccessException extends RuntimeException {

	public PropertyAccessException() {
		
	}

	public PropertyAccessException(Object bean, String property, Throwable e) {
		super("Cannot access property "+property+" in bean "+bean, e);
	}

}

package it.amattioli.dominate.properties;

public class UnwritablePropertyException extends PropertyAccessException {

	public UnwritablePropertyException(Object bean, String property, Throwable e) {
		super(bean, property, e);
	}

}

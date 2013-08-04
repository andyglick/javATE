package it.amattioli.dominate.properties;

public class UnreadeablePropertyException extends PropertyAccessException {

	public UnreadeablePropertyException(Object bean, String property, Throwable e) {
		super(bean, property, e);
	}

}

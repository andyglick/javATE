package it.amattioli.dominate.properties;

import static org.apache.commons.lang.StringUtils.capitalize;

import java.lang.reflect.Method;

public class PropertyDescriptor {
	private Method getter;
	private Method setter;
	
	public PropertyDescriptor(Class<?> beanClass, String propertyName) {
		try {
			getter = beanClass.getMethod("get"+capitalize(propertyName));
			setter = beanClass.getMethod("set"+capitalize(propertyName), getter.getReturnType());
		} catch (SecurityException e) {
			throw new RuntimeException(e);
		} catch (NoSuchMethodException e) {
			throw new UnknownPropertyException();
		}
	}
	
	public Class<?> getType() {
		//return String.class;
		return getter.getReturnType();
	}

	public Method getGetter() {
		return getter;
	}

	public Method getSetter() {
		return setter;
	}
}

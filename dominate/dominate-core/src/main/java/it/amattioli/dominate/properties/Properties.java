package it.amattioli.dominate.properties;

import java.lang.reflect.InvocationTargetException;

import org.apache.commons.beanutils.NestedNullException;
import org.apache.commons.beanutils.PropertyUtils;

public class Properties {

	private Properties() {
	}
	
	public static Object get(Object bean, String property) {
		try {
			return PropertyUtils.getProperty(bean, property);
		} catch (IllegalAccessException e) {
			throw new PropertyAccessException(bean, property, e);
		} catch (InvocationTargetException e) {
			throw new PropertyAccessException(bean, property, e);
		} catch (NoSuchMethodException e) {
			throw new UnreadeablePropertyException(bean, property, e);
		} catch (NestedNullException e) {
			return null;
		}
	}
	
	public static void set(Object bean, String property, Object value) {
		try {
			PropertyUtils.setProperty(bean, property, value);
		} catch (IllegalAccessException e) {
			throw new PropertyAccessException(bean, property, e);
		} catch (InvocationTargetException e) {
			throw new PropertyAccessException(bean, property, e);
		} catch (NoSuchMethodException e) {
			throw new UnwritablePropertyException(bean, property, e);
		}
	}
	
}

package it.amattioli.dominate.properties;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;

public class PropertyAvailabilityRetrieverImpl implements PropertyAvailabilityRetriever {
	private static final String AVAILABLE_SUBFIX = "Available";
	private Object propertyBean;
	
	public PropertyAvailabilityRetrieverImpl(Object bean) {
		this.propertyBean = bean;
	}
	
	@Override
	public boolean isPropertyAvailable(String propertyName) {
		if (propertyBean == null) {
			return false;
		}
		if (propertyBean instanceof PropertyAvailabilityRetriever) {
			return ((PropertyAvailabilityRetriever)propertyBean).isPropertyAvailable(propertyName);
		}
		try {
			return (Boolean)availableMethod(propertyName).invoke(propertyBean); 
		} catch(NoSuchMethodException e) {
			try {
				return PropertyUtils.getPropertyDescriptor(propertyBean, propertyName) != null;
			} catch (IllegalAccessException e1) {
				throw new RuntimeException(e1);
			} catch (InvocationTargetException e1) {
				throw new RuntimeException(e1);
			} catch (NoSuchMethodException e1) {
				throw new RuntimeException(e1);
			}
		} catch(Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	public static String availableProperty(String propertyName) {
		return propertyName + AVAILABLE_SUBFIX; 
	}
	
	public Method availableMethod(String propertyName) throws NoSuchMethodException {
		Method isAvailableMethod = propertyBean.getClass().getMethod("is"+StringUtils.capitalize(availableProperty(propertyName)));
		return isAvailableMethod;
	}

}

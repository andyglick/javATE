package it.amattioli.dominate.properties;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;

public class PropertyWritabilityRetrieverImpl implements PropertyWritabilityRetriever {
	private static final String WRITABLE_SUBFIX = "Writable";
	private Object propertyBean;
	
	public PropertyWritabilityRetrieverImpl(Object bean) {
		this.propertyBean = bean;
	}
	
	@Override
	public boolean isPropertyWritable(String propertyName) {
		if (propertyBean == null) {
			return false;
		}
		if (propertyBean instanceof PropertyWritabilityRetriever) {
			return ((PropertyWritabilityRetriever)propertyBean).isPropertyWritable(propertyName);
		}
		try {
			return (Boolean)writableMethod(propertyName).invoke(propertyBean); 
		} catch(NoSuchMethodException e) {
			try {
				return PropertyUtils.getPropertyDescriptor(propertyBean, propertyName).getWriteMethod() != null;
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
	
	public static String writableProperty(String propertyName) {
		return propertyName + WRITABLE_SUBFIX; 
	}
	
	public Method writableMethod(String propertyName) throws NoSuchMethodException {
		Method isWritableMethod = propertyBean.getClass().getMethod("is"+StringUtils.capitalize(writableProperty(propertyName)));
		return isWritableMethod;
	}

}

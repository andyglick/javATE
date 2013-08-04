package it.amattioli.dominate.properties;

import it.amattioli.dominate.proxies.ProxyUtils;

import java.beans.IndexedPropertyDescriptor;
import java.beans.PropertyDescriptor;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import org.apache.commons.beanutils.PropertyUtils;

public class AnnotationsRetrieverImpl implements AnnotationsRetriever {
	private Class<?> beanClass;
	
	public AnnotationsRetrieverImpl(Object bean) {
		if (bean != null) {
			this.beanClass = ProxyUtils.unProxyClass(bean.getClass());
		}
	}
	
	public AnnotationsRetrieverImpl(Class<?> beanClass) {
		this.beanClass = ProxyUtils.unProxyClass(beanClass);
	}
	
	@Override
	public <T extends Annotation> T retrieveAnnotation(String propertyName, Class<T> anotationClass) {
		if (beanClass == null) {
			return null;
		}
		try {	
			Field propertyField = beanClass.getDeclaredField(propertyName);
			T a = propertyField.getAnnotation(anotationClass);
			if (a != null) {
				return a;
			} else {
				throw new NoSuchFieldException();
			}
		} catch (SecurityException e) {
			throw new RuntimeException(e);
		} catch (NoSuchFieldException e) {
			// if not on the field it could be on the accessor method
			PropertyDescriptor propertyDescriptor = findPropertyDescriptor(propertyName);
			if (propertyDescriptor == null) {
				return null;
			}
			Method propertyMethod = getReadMethod(propertyDescriptor);
			T a = null;
			if (propertyMethod != null) {
				a = propertyMethod.getAnnotation(anotationClass);
			}
			return a;
		}
	}

	@Override
	public Annotation[] retrieveAnnotations(String propertyName) {
		if (beanClass == null) {
			return new Annotation[] {};
		}
		try {	
			Field propertyField = beanClass.getDeclaredField(propertyName);
			Annotation[] a = propertyField.getAnnotations();
			if (a != null && a.length > 0) {
				return a;
			} else {
				throw new NoSuchFieldException();
			}
		} catch (SecurityException e) {
			throw new RuntimeException(e);
		} catch (NoSuchFieldException e) {
			// if not on the field it could be on the accessor method
			PropertyDescriptor propertyDescriptor = findPropertyDescriptor(propertyName);
			if (propertyDescriptor == null) {
				return new Annotation[] {};
			}
			Method propertyMethod = getReadMethod(propertyDescriptor);
			Annotation[] a = propertyMethod.getAnnotations();
			return a;
		}
	}

	private Method getReadMethod(PropertyDescriptor propertyDescriptor) {
		if (propertyDescriptor instanceof IndexedPropertyDescriptor) {
			return ((IndexedPropertyDescriptor)propertyDescriptor).getIndexedReadMethod();
		}
		return propertyDescriptor.getReadMethod();
	}

	private PropertyDescriptor findPropertyDescriptor(String propertyName) {
		PropertyDescriptor[] descriptors = PropertyUtils.getPropertyDescriptors(beanClass);
		for (PropertyDescriptor curr : descriptors) {
			if (curr.getName().equals(propertyName)) {
				return curr;
			}
		}
		return null;
	}

}

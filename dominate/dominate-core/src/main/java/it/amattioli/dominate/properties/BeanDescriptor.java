package it.amattioli.dominate.properties;

import java.util.HashMap;
import java.util.Map;

public class BeanDescriptor {
	private static Map<Class<?>, BeanDescriptor> beanDescriptors = new HashMap<Class<?>, BeanDescriptor>();
	
	public static BeanDescriptor describeBean(Class<?> beanClass) {
		BeanDescriptor result = beanDescriptors.get(beanClass);
		if (result == null) {
			result = new BeanDescriptor(beanClass);
			beanDescriptors.put(beanClass, result);
		}
		return result;
	}
	
	private Class<?> beanClass;
	private Map<String, PropertyDescriptor> propertyDescriptors = new HashMap<String, PropertyDescriptor>();
	
	private BeanDescriptor(Class<?> beanClass) {
		this.beanClass = beanClass;
	}

	public PropertyDescriptor describeProperty(String name) {
		if (propertyDescriptors.containsKey(name))  {
			return propertyDescriptors.get(name);
		} else {
			PropertyDescriptor propertyDescriptor = new PropertyDescriptor(beanClass, name);	
			propertyDescriptors.put(name, propertyDescriptor);
			return propertyDescriptor;
		}
	}
	
}

package it.amattioli.dominate.properties;

import java.lang.reflect.InvocationTargetException;

public class PropertyUtils {
	private Object bean;
	private BeanDescriptor beanDescriptor;
	
	public PropertyUtils(Object bean) {
		this.bean = bean;
		beanDescriptor = BeanDescriptor.describeBean(bean.getClass());
	}
	
	public PropertyDescriptor describe(String name) {
		return beanDescriptor.describeProperty(name);
	}
	
	public Object getProperty(String name) {
		
		try {
			return describe(name).getGetter().invoke(bean);
		} catch (SecurityException e) {
			throw new RuntimeException(e);		
		}  catch (IllegalArgumentException e) {
			throw new RuntimeException(e);
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		} catch (InvocationTargetException e) {
			throw new RuntimeException(e);
		}
		
	}
	
	public void setProperty(String name, Object value) {
		try {
			describe(name).getSetter().invoke(bean, value);
		} catch (SecurityException e) {
			throw new RuntimeException(e);
		} catch (IllegalArgumentException e) {
			throw new RuntimeException(e);
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		} catch (InvocationTargetException e) {
			throw new RuntimeException(e);
		}
	}
	
}

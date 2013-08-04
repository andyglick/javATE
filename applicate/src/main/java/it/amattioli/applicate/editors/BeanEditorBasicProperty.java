package it.amattioli.applicate.editors;

import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;

import org.apache.commons.beanutils.DynaProperty;
import org.apache.commons.beanutils.PropertyUtils;

public class BeanEditorBasicProperty implements BeanEditorProperty {
	private Object bean;
	private String name;
	private Class<?> propertyClass;
	
	public BeanEditorBasicProperty(Object bean, PropertyDescriptor descriptor) {
		this.bean = bean;
		this.name = descriptor.getName();
		this.propertyClass = descriptor.getPropertyType();
	}

	/* (non-Javadoc)
	 * @see it.amattioli.applicate.editors.BeanEditorProperty#getName()
	 */
	public String getName() {
		return name;
	}

	/* (non-Javadoc)
	 * @see it.amattioli.applicate.editors.BeanEditorProperty#getDynaProperty()
	 */
	public DynaProperty getDynaProperty() {
		return new DynaProperty(name, propertyClass);
	}

	/* (non-Javadoc)
	 * @see it.amattioli.applicate.editors.BeanEditorProperty#get()
	 */
	public Object get() {
		try {
			return PropertyUtils.getProperty(bean, name);
		} catch (IllegalAccessException e) {
			throw new IllegalArgumentException(e);
		} catch (InvocationTargetException e) {
			throw new RuntimeException(e.getCause());
		} catch (NoSuchMethodException e) {
			throw new IllegalArgumentException(e);
		}
	}
	
	/* (non-Javadoc)
	 * @see it.amattioli.applicate.editors.BeanEditorProperty#getIndexed(int)
	 */
	public Object getIndexed(int idx) {
		try {
			return PropertyUtils.getIndexedProperty(bean, name, idx);
		} catch (IllegalAccessException e) {
			throw new IllegalArgumentException(e);
		} catch (InvocationTargetException e) {
			throw new RuntimeException(e.getCause());
		} catch (NoSuchMethodException e) {
			throw new IllegalArgumentException(e);
		}
	}
	
	/* (non-Javadoc)
	 * @see it.amattioli.applicate.editors.BeanEditorProperty#set(java.lang.Object)
	 */
	public void set(Object value) {
		try {
			PropertyUtils.setProperty(bean, name, value);
		} catch (IllegalAccessException e) {
			throw new IllegalArgumentException(e);
		} catch (InvocationTargetException e) {
			throw new RuntimeException(e.getCause());
		} catch (NoSuchMethodException e) {
			throw new IllegalArgumentException(e);
		}
	}
	
	/* (non-Javadoc)
	 * @see it.amattioli.applicate.editors.BeanEditorProperty#setIndexed(int, java.lang.Object)
	 */
	public void setIndexed(int idx, Object value) {
		try {
			PropertyUtils.setIndexedProperty(bean, name, idx, value);
		} catch (IllegalAccessException e) {
			throw new IllegalArgumentException(e);
		} catch (InvocationTargetException e) {
			throw new RuntimeException(e.getCause());
		} catch (NoSuchMethodException e) {
			throw new IllegalArgumentException(e);
		}
	}
}

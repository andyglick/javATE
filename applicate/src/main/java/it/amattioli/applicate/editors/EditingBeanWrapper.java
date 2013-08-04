package it.amattioli.applicate.editors;

import java.beans.PropertyDescriptor;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BasicDynaClass;
import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.beanutils.DynaClass;
import org.apache.commons.beanutils.DynaProperty;
import org.apache.commons.beanutils.PropertyUtils;

public class EditingBeanWrapper implements DynaBean {
	private static Collection<BeanEditorPropertyFactory> factories = new ArrayList<BeanEditorPropertyFactory>();
	static {
		factories.add(new BeanEditorBasicPropertyFactory());
		factories.add(new BeanEditorIndexedPropertyFactory());
	}
	
	private DynaClass dynaClass;
	private Map<String, BeanEditorProperty> properties = new HashMap<String, BeanEditorProperty>();

	public EditingBeanWrapper(DynaClass c) {
		
	}
	
	public EditingBeanWrapper(Object wrapped) {
		createProperties(wrapped);
		createDynaClass(wrapped);
	}

	private void createProperties(Object wrapped) {
		for (PropertyDescriptor descriptor: PropertyUtils.getPropertyDescriptors(wrapped.getClass())) {
			createProperty(wrapped, descriptor);
		}
	}

	private void createProperty(Object wrapped, PropertyDescriptor descriptor) {
		for (BeanEditorPropertyFactory factory: factories) {
			if (factory.canCreatePropertiesFor(wrapped, descriptor)) {
				for (BeanEditorProperty property: factory.createPropertiesFor(wrapped, descriptor)) {
					properties.put(property.getName(), property);
				}
			}
		}
	}
	
	private void createDynaClass(Object wrapped) {
		List<DynaProperty> dynaProperties = new ArrayList<DynaProperty>();
		for (BeanEditorProperty property: properties.values()) {
			dynaProperties.add(property.getDynaProperty());
		}
		dynaClass = new BasicDynaClass(
				wrapped.getClass().getName()+"Wrapper", 
				EditingBeanWrapper.class, 
				dynaProperties.toArray(new DynaProperty[dynaProperties.size()]));
	}

	@Override
	public boolean contains(String name, String key) {
		throw new IllegalArgumentException();
	}

	@Override
	public Object get(String name, int index) {
		BeanEditorProperty property = properties.get(name);
		if (property == null) {
			throw new IllegalArgumentException();
		}
		return property.getIndexed(index);
	}

	@Override
	public Object get(String name, String key) {
		throw new IllegalArgumentException();
	}

	@Override
	public Object get(String name) {
		BeanEditorProperty property = properties.get(name);
		if (property == null) {
			throw new IllegalArgumentException();
		}
		return property.get();
	}

	@Override
	public DynaClass getDynaClass() {
		return dynaClass;
	}

	@Override
	public void remove(String name, String key) {
		throw new IllegalArgumentException();
	}

	@Override
	public void set(String name, int index, Object value) {
		BeanEditorProperty property = properties.get(name);
		if (property == null) {
			throw new IllegalArgumentException();
		}
		property.setIndexed(index, value);
	}

	@Override
	public void set(String name, Object value) {
		BeanEditorProperty property = properties.get(name);
		if (property == null) {
			throw new IllegalArgumentException();
		}
		property.set(value);
	}

	@Override
	public void set(String name, String key, Object value) {
		throw new IllegalArgumentException();
	}

}

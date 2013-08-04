package it.amattioli.applicate.editors;

import java.beans.IndexedPropertyDescriptor;
import java.beans.PropertyDescriptor;
import java.util.ArrayList;
import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BeanEditorIndexedPropertyFactory implements BeanEditorPropertyFactory {
	private static final Logger logger = LoggerFactory.getLogger(BeanEditorIndexedPropertyFactory.class);

	@Override
	public boolean canCreatePropertiesFor(Object bean, PropertyDescriptor descriptor) {
		return descriptor instanceof IndexedPropertyDescriptor;
	}

	@Override
	public Collection<BeanEditorProperty> createPropertiesFor(Object bean, PropertyDescriptor descriptor) {
		ArrayList<BeanEditorProperty> result = new ArrayList<BeanEditorProperty>();
		result.add(new BeanEditorBasicProperty(bean, descriptor));
		try {
			result.add(new BeanEditorIndexedProperty(bean, (IndexedPropertyDescriptor)descriptor));
		} catch(Exception e) {
			logger.debug("No ListEditor property added", e);
		}
		return result;
	}

}

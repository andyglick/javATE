package it.amattioli.applicate.editors;

import java.beans.IndexedPropertyDescriptor;
import java.beans.PropertyDescriptor;
import java.util.ArrayList;
import java.util.Collection;

public class BeanEditorBasicPropertyFactory implements BeanEditorPropertyFactory {

	@Override
	public boolean canCreatePropertiesFor(Object bean, PropertyDescriptor descriptor) {
		return !(descriptor instanceof IndexedPropertyDescriptor);
	}

	@Override
	public Collection<BeanEditorProperty> createPropertiesFor(Object bean, PropertyDescriptor descriptor) {
		ArrayList<BeanEditorProperty> result = new ArrayList<BeanEditorProperty>();
		result.add(new BeanEditorBasicProperty(bean, descriptor));
		return result;
	}

}

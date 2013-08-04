package it.amattioli.applicate.editors;

import java.beans.PropertyDescriptor;
import java.util.Collection;

public interface BeanEditorPropertyFactory {

	public boolean canCreatePropertiesFor(Object bean, PropertyDescriptor descriptor);
	
	public Collection<BeanEditorProperty> createPropertiesFor(Object bean, PropertyDescriptor descriptor);
	
}

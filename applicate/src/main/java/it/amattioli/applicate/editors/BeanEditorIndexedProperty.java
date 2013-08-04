package it.amattioli.applicate.editors;

import it.amattioli.dominate.properties.IndexedPropertyAdapter;

import java.beans.IndexedPropertyDescriptor;
import java.beans.PropertyDescriptor;

import org.apache.commons.beanutils.DynaProperty;

public class BeanEditorIndexedProperty implements BeanEditorProperty {
	private String name;
	private ListEditor<?> editor;
	
	public BeanEditorIndexedProperty(Object bean, IndexedPropertyDescriptor descriptor) {
		this.name = descriptor.getName() + "Editor";
		editor = new ListEditorImpl(
					new IndexedPropertyAdapter(bean, descriptor.getName()), 
					descriptor.getIndexedPropertyType());
	}
	
	@Override
	public String getName() {
		return name;
		
	}

	@Override
	public DynaProperty getDynaProperty() {
		return new DynaProperty(name, ListEditor.class);
	}

	@Override
	public Object get() {
		return editor;
	}

	@Override
	public Object getIndexed(int idx) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void set(Object value) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setIndexed(int idx, Object value) {
		throw new UnsupportedOperationException();
	}

}

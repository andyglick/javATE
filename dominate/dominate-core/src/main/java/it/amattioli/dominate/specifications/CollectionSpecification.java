package it.amattioli.dominate.specifications;

import java.util.Collection;

import it.amattioli.dominate.Entity;
import it.amattioli.dominate.properties.Properties;

public abstract class CollectionSpecification<T extends Entity<?>> extends ChainedSpecification<T> {
	private Object value;
	private String propertyName;
	
	public static <T extends Entity<?>> CollectionSpecification<T> newInstance(String propertyName) {
		CollectionSpecification<T> result = ChainedSpecification.createChain(CollectionSpecification.class);
		result.setPropertyName(propertyName);
		return result;
	}
	
	public CollectionSpecification() {
		
	}

	public CollectionSpecification(String propertyName) {
		super();
		this.propertyName = propertyName;
	}

	@Override
	public boolean isSatisfiedBy(T object) {
		if (!wasSet()) {
			return isSatisfiedIfNotSet();
		}
		Collection<Object> res = (Collection<Object>)Properties.get(object, getPropertyName());
		return res.contains(value);
	}

	@Override
	public boolean wasSet() {
		return value != null ;
	}
	
	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
		if (getNextInChain() != null) {
			((CollectionSpecification<T>)getNextInChain()).setValue(value);
		}
		fireSpecificationChange();
	}

	public String getPropertyName() {
		return propertyName;
	}

	public void setPropertyName(String propertyName) {
		this.propertyName = propertyName;
		if (getNextInChain() != null) {
			((CollectionSpecification<T>)getNextInChain()).setPropertyName(propertyName);
		}
	}
}

package it.amattioli.dominate.specifications;

import it.amattioli.dominate.Entity;
import it.amattioli.dominate.properties.Properties;

public abstract class ObjectSpecification<T extends Entity<?>> extends ChainedSpecification<T> {
	private String propertyName;
	private Object value;

	public static <T extends Entity<?>> ObjectSpecification<T> newInstance(String propertyName) {
		ObjectSpecification<T> result = ChainedSpecification.createChain(ObjectSpecification.class);
		result.setPropertyName(propertyName);
		return result;
	}

	public ObjectSpecification() {
		
	}
	
	public ObjectSpecification(String propertyName) {
		setPropertyName(propertyName);
	}

	protected String getPropertyName() {
		return propertyName;
	}
	
	protected void setPropertyName(String propertyName) {
		this.propertyName = propertyName;
		if (getNextInChain() != null) {
			((ObjectSpecification<T>)getNextInChain()).setPropertyName(propertyName);
		}
	}
	
	public void setValue(Object value) {
		this.value = value;
		if (getNextInChain() != null) {
			((ObjectSpecification<T>)getNextInChain()).setValue(value);
		}
		fireSpecificationChange();
	}

	public Object getValue() {
		return value;
	}
	
	@Override
	public boolean isSatisfiedBy(T entity) {
		if (!wasSet()) {
			return isSatisfiedIfNotSet();
		}
		Object propertyValue = Properties.get(entity, getPropertyName());
		return getValue().equals(propertyValue);
	}

	public boolean wasSet() {
		return getValue() != null;
	}
}

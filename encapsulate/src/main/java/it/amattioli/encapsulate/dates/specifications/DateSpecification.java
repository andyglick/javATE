package it.amattioli.encapsulate.dates.specifications;

import java.lang.reflect.InvocationTargetException;
import java.util.Date;

import org.apache.commons.beanutils.PropertyUtils;

import it.amattioli.dominate.Entity;
import it.amattioli.dominate.specifications.ChainedSpecification;
import it.amattioli.encapsulate.dates.TimeInterval;

public abstract class DateSpecification<T extends Entity<?>> extends ChainedSpecification<T> {
	private String propertyName;
	private TimeInterval value;
	
	public static <T extends Entity<?>> DateSpecification<T> newInstance(String propertyName) {
		DateSpecification<T> result = ChainedSpecification.createChain(DateSpecification.class);
		result.setPropertyName(propertyName);
		return result;
	}
	
	public DateSpecification() {

	}
	
	public DateSpecification(String propertyName) {
		setPropertyName(propertyName);
	}

	protected String getPropertyName() {
		return propertyName;
	}
	
	protected void setPropertyName(String propertyName) {
		this.propertyName = propertyName;
		if (getNextInChain() != null) {
			((DateSpecification<T>)getNextInChain()).setPropertyName(propertyName);
		}
	}
	
	public void setValue(TimeInterval value) {
		this.value = value;
		if (getNextInChain() != null) {
			((DateSpecification<T>)getNextInChain()).setValue(value);
		}
		fireSpecificationChange();
	}

	public TimeInterval getValue() {
		return value;
	}
	
	@Override
	public boolean isSatisfiedBy(T entity) {
		if (!wasSet()) {
			return isSatisfiedIfNotSet();
		}
		try {
			Date propertyValue = (Date)PropertyUtils.getProperty(entity, getPropertyName());
			return getValue().includes(propertyValue);
		} catch (IllegalAccessException e) {
			throw new RuntimeException();
		} catch (InvocationTargetException e) {
			throw new RuntimeException();
		} catch (NoSuchMethodException e) {
			throw new RuntimeException();
		}
	}
	
	@Override
	public boolean wasSet() {
		return getValue() != null;
	}
}

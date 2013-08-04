package it.amattioli.encapsulate.range.specifications;

import java.lang.reflect.InvocationTargetException;

import org.apache.commons.beanutils.PropertyUtils;

import it.amattioli.dominate.Entity;
import it.amattioli.dominate.specifications.ChainedSpecification;
import it.amattioli.encapsulate.range.Range;

public abstract class ComparableSpecification<T extends Entity<?>, N extends Comparable<? super N>> extends ChainedSpecification<T> {
	private String propertyName;
	private Range<N> value;
	
	public static <T extends Entity<?>, N extends Comparable<? super N>> ComparableSpecification<T,N> newInstance(String propertyName) {
		ComparableSpecification<T,N> result = ChainedSpecification.createChain(ComparableSpecification.class);
		result.setPropertyName(propertyName);
		return result;
	}
	
	public ComparableSpecification() {

	}
	
	public ComparableSpecification(String propertyName) {
		setPropertyName(propertyName);
	}

	protected String getPropertyName() {
		return propertyName;
	}
	
	protected void setPropertyName(String propertyName) {
		this.propertyName = propertyName;
		if (getNextInChain() != null) {
			((ComparableSpecification<T,N>)getNextInChain()).setPropertyName(propertyName);
		}
	}

	public void setValue(Range<N> value) {
		this.value = value;
		if (getNextInChain() != null) {
			((ComparableSpecification<T,N>)getNextInChain()).setValue(value);
		}
		fireSpecificationChange();
	}

	public Range<N> getValue() {
		return value;
	}
	
	@Override
	public boolean isSatisfiedBy(T entity) {
		if (!wasSet()) {
			return isSatisfiedIfNotSet();
		}
		try {
			N propertyValue = (N)PropertyUtils.getProperty(entity, getPropertyName());
			return getValue().includes(propertyValue);
		} catch (IllegalAccessException e) {
			throw new RuntimeException();
		} catch (InvocationTargetException e) {
			throw new RuntimeException();
		} catch (NoSuchMethodException e) {
			throw new RuntimeException();
		}
	}

	public boolean wasSet() {
		return getValue() != null;
	}
}

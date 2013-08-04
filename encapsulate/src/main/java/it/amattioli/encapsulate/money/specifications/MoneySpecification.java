package it.amattioli.encapsulate.money.specifications;

import it.amattioli.dominate.Entity;
import it.amattioli.dominate.specifications.ChainedSpecification;
import it.amattioli.encapsulate.money.Money;
import it.amattioli.encapsulate.range.Range;

import java.lang.reflect.InvocationTargetException;

import org.apache.commons.beanutils.PropertyUtils;

public abstract class MoneySpecification<T extends Entity<?>> extends ChainedSpecification<T> {
	private String propertyName;
	private Range<Money> value;
	
	public static <T extends Entity<?>> MoneySpecification<T> newInstance(String propertyName) {
		MoneySpecification<T> result = ChainedSpecification.createChain(MoneySpecification.class);
		result.setPropertyName(propertyName);
		return result;
	}
	
	public MoneySpecification() {

	}
	
	public MoneySpecification(String propertyName) {
		this.propertyName = propertyName;
	}

	protected String getPropertyName() {
		return propertyName;
	}
	
	protected void setPropertyName(String propertyName) {
		this.propertyName = propertyName;
		if (getNextInChain() != null) {
			((MoneySpecification<T>)getNextInChain()).setPropertyName(propertyName);
		}
	}
	
	public void setValue(Range<Money> value) {
		this.value = value;
		if (getNextInChain() != null) {
			((MoneySpecification<T>)getNextInChain()).setValue(value);
		}
		fireSpecificationChange();
	}

	public Range<Money> getValue() {
		return value;
	}
	
	@Override
	public boolean isSatisfiedBy(T entity) {
		if (!wasSet()) {
			return isSatisfiedIfNotSet();
		}
		try {
			Money propertyValue = (Money)PropertyUtils.getProperty(entity, getPropertyName());
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


package it.amattioli.encapsulate.dates.specifications;

import java.lang.reflect.InvocationTargetException;
import java.util.Date;

import org.apache.commons.beanutils.PropertyUtils;

import it.amattioli.dominate.Entity;
import it.amattioli.dominate.specifications.ChainedSpecification;
import it.amattioli.encapsulate.dates.TimeInterval;

public abstract class TimeIntervalSpecification<T extends Entity<?>> extends ChainedSpecification<T> {
	private String propertyName;
	private Date includedValue;
	private TimeInterval includingInterval;
	
	public static <T extends Entity<?>> TimeIntervalSpecification<T> newInstance(String propertyName) {
		TimeIntervalSpecification<T> result = ChainedSpecification.createChain(TimeIntervalSpecification.class);
		result.setPropertyName(propertyName);
		return result;
	}
	
	public TimeIntervalSpecification() {

	}
	
	public TimeIntervalSpecification(TimeIntervalSpecification<T> chained) {
		super(chained);
	}
	
	public String getPropertyName() {
		return propertyName;
	}
	
	protected void setPropertyName(String propertyName) {
		this.propertyName = propertyName;
	}

	public Date getIncludedValue() {
		return includedValue;
	}

	public void setIncludedValue(Date includedValue) {
		this.includedValue = includedValue;
		this.includingInterval = null;
		if (getNextInChain() != null) {
			((TimeIntervalSpecification<T>)getNextInChain()).setIncludedValue(includedValue);
		}
		fireSpecificationChange();
	}
	
	public TimeInterval getIncludingInterval() {
		return includingInterval;
	}
	
	public void setIncludingInterval(TimeInterval includingInterval) {
		this.includingInterval = includingInterval;
		this.includedValue = null;
		if (getNextInChain() != null) {
			((TimeIntervalSpecification<T>)getNextInChain()).setIncludingInterval(includingInterval);
		}
		fireSpecificationChange();
	}

	@Override
	public boolean isSatisfiedBy(T object) {
		if (!wasSet()) {
			return isSatisfiedIfNotSet();
		}
		TimeInterval propertyValue;
		try {
			propertyValue = (TimeInterval)PropertyUtils.getProperty(object, getPropertyName());
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		} catch (InvocationTargetException e) {
			throw new RuntimeException(e.getCause());
		} catch (NoSuchMethodException e) {
			throw new RuntimeException(e);
		}
		if (getIncludedValue() != null) {
			return propertyValue.includes(getIncludedValue());
		} else {
			return getIncludingInterval().contains(propertyValue);
		}
	}

	@Override
	public boolean wasSet() {
		return includedValue != null || includingInterval != null;
	}
}

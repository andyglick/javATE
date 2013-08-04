package it.amattioli.dominate.specifications;

import it.amattioli.dominate.Entity;
import it.amattioli.dominate.properties.Properties;

public abstract class LongSpecification<T extends Entity<?>> extends ChainedSpecification<T> {
	private TotalOrderComparisonType comparisonType = TotalOrderComparisonType.EQUAL;
	private String propertyName;
	private Long value;
	
	public static <T extends Entity<?>> LongSpecification<T> newInstance(String propertyName) {
		LongSpecification<T> result = ChainedSpecification.createChain(LongSpecification.class);
		result.setPropertyName(propertyName);
		return result;
	}
	
	public LongSpecification() {
		
	}
	
	public LongSpecification(String propertyName) {
		setPropertyName(propertyName);
	}

	protected String getPropertyName() {
		return propertyName;
	}
	
	protected void setPropertyName(String propertyName) {
		this.propertyName = propertyName;
		if (getNextInChain() != null) {
			((LongSpecification<T>)getNextInChain()).setPropertyName(propertyName);
		}
	}

	public void setValue(Long value) {
		this.value = value;
		if (getNextInChain() != null) {
			((LongSpecification<T>)getNextInChain()).setValue(value);
		}
		fireSpecificationChange();
	}

	public Long getValue() {
		return value;
	}

	public TotalOrderComparisonType getComparisonType() {
		return comparisonType;
	}

	public void setComparisonType(TotalOrderComparisonType comparisonType) {
		this.comparisonType = comparisonType;
		if (getNextInChain() != null) {
			((LongSpecification<T>)getNextInChain()).setComparisonType(comparisonType);
		}
	}

	@Override
	public boolean isSatisfiedBy(T entity) {
		if (!wasSet()) {
			return isSatisfiedIfNotSet();
		}
		Long propertyValue = (Long)Properties.get(entity, getPropertyName());
		return comparisonType.isSatisfiedBy(propertyValue, getValue());
	}

	public boolean wasSet() {
		return getValue() != null;
	}

}
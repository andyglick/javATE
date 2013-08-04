package it.amattioli.dominate.specifications;

import java.util.Arrays;
import java.util.Collection;

import it.amattioli.dominate.Entity;
import it.amattioli.dominate.properties.Properties;

public abstract class StringSpecification<T extends Entity<?>> extends ChainedSpecification<T> {
	private String propertyName;
	private String value;
	private ComparisonType comparisonType = ComparisonType.STARTS;
	
	public static <T extends Entity<?>> StringSpecification<T> newInstance(String propertyName) {
		StringSpecification<T> result = ChainedSpecification.createChain(StringSpecification.class);
		result.setPropertyName(propertyName);
		return result;
	}
	
	public StringSpecification() {
		
	}
	
	public StringSpecification(String propertyName) {
		this.propertyName = propertyName;
	}
	
	public StringSpecification(StringSpecification<T> chained) {
		super(chained);
	}

	protected String getPropertyName() {
		return propertyName;
	}
	
	private void setPropertyName(String propertyName) {
		this.propertyName = propertyName;
		if (getNextInChain() != null) {
			((StringSpecification<T>)getNextInChain()).setPropertyName(propertyName);
		}
	}

	public void setValue(String value) {
		this.value = value;
		if (getNextInChain() != null) {
			((StringSpecification<T>)getNextInChain()).setValue(value);
		}
		fireSpecificationChange();
	}

	public String getValue() {
		return value;
	}
	
	public ComparisonType getComparisonType() {
        return comparisonType;
    }

    public void setComparisonType(ComparisonType filterType) {
        this.comparisonType = filterType;
        if (getNextInChain() != null) {
        	((StringSpecification<T>)getNextInChain()).setComparisonType(filterType);
        }
    }

    public void setComparisonType(String filterType) {
        setComparisonType(ComparisonType.valueOf(filterType));
    }

    public Collection<ComparisonType> getComparisonTypeValues() {
        return Arrays.asList(ComparisonType.values());
    }

	@Override
	public boolean isSatisfiedBy(T entity) {
		if (!wasSet()) {
			return isSatisfiedIfNotSet();
		}
		String propertyValue = (String)Properties.get(entity, getPropertyName());
		return getComparisonType().compare(propertyValue, getValue());
	}

	public boolean wasSet() {
		return getValue() != null && !getValue().equals("");
	}

}
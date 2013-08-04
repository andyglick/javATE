package it.amattioli.dominate.specifications;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.Collection;

import it.amattioli.dominate.Entity;
import it.amattioli.dominate.properties.Properties;

public abstract class MultipleEnumSpecification<T extends Entity<?>, U extends Enum<U>> extends ChainedSpecification<T> {
	private String propertyName;
	private Collection<U> value;
	private Class<U> enumClass;
	
	public static <T extends Entity<?>, U extends Enum<U>> MultipleEnumSpecification<T, U> newInstance(String propertyName, Class<U> enumClass) {
		MultipleEnumSpecification<T, U> result = ChainedSpecification.createChain(MultipleEnumSpecification.class);
		result.setPropertyName(propertyName);
		result.setEnumClass(enumClass);
		return result;
	}
	
	public MultipleEnumSpecification() {

	}
	
	public MultipleEnumSpecification(String propertyName, Class<U> enumClass) {
		setPropertyName(propertyName);
		setEnumClass(enumClass);
	}

	protected String getPropertyName() {
		return propertyName;
	}
	
	protected void setPropertyName(String propertyName) {
		this.propertyName = propertyName;
		if (getNextInChain() != null) {
			((MultipleEnumSpecification<T, U>)getNextInChain()).setPropertyName(propertyName);
		}
	}
	
	protected Class<U> getEnumClass() {
		return enumClass;
	}
	
	protected void setEnumClass(Class<U> enumClass) {
		this.enumClass = enumClass;
		if (getNextInChain() != null) {
			((MultipleEnumSpecification<T, U>)getNextInChain()).setEnumClass(enumClass);
		}
	}

	public void setValue(Collection<U> value) {
		this.value = value;
		if (getNextInChain() != null) {
			((MultipleEnumSpecification<T, U>)getNextInChain()).setValue(value);
		}
		fireSpecificationChange();
	}

	public Collection<U> getValue() {
		return value;
	}

	@SuppressWarnings("unchecked")
    public Collection<T> getAdmittedValues() {
        try {
            return Arrays.asList((T[])enumClass.getMethod("values").invoke(null));
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }
	
	@Override
	public boolean isSatisfiedBy(T entity) {
		if (!wasSet()) {
			return isSatisfiedIfNotSet();
		}
		U propertyValue = (U)Properties.get(entity, getPropertyName());
		return getValue().contains(propertyValue);
	}

	public boolean wasSet() {
		return getValue() != null && !getValue().isEmpty();
	}

}
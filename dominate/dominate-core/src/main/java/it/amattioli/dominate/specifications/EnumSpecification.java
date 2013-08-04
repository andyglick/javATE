package it.amattioli.dominate.specifications;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.Collection;

import it.amattioli.dominate.Entity;
import it.amattioli.dominate.properties.Properties;

public abstract class EnumSpecification<T extends Entity<?>, U extends Enum<U>> extends ChainedSpecification<T> {
	private String propertyName;
	private U value;
	private Class<U> enumClass;
	
	public static <T extends Entity<?>, U extends Enum<U>> EnumSpecification<T, U> newInstance(String propertyName, Class<U> enumClass) {
		EnumSpecification<T, U> result = ChainedSpecification.createChain(EnumSpecification.class);
		result.setPropertyName(propertyName);
		result.setEnumClass(enumClass);
		return result;
	}
	
	public EnumSpecification() {
		
	}
	
	public EnumSpecification(String propertyName, Class<U> enumClass) {
		setPropertyName(propertyName);
		setEnumClass(enumClass);
	}
	
	public EnumSpecification(String propertyName, Class<U> enumClass, EnumSpecification<T, U> chained) {
		super(chained);
		setPropertyName(propertyName);
		setEnumClass(enumClass);
	}

	protected String getPropertyName() {
		return propertyName;
	}
	
	protected void setPropertyName(String propertyName) {
		this.propertyName = propertyName;
		if (getNextInChain() != null) {
			((EnumSpecification<T,U>)getNextInChain()).setPropertyName(propertyName);
		}
	}
	
	protected Class<U> getEnumClass() {
		return enumClass;
	}
	
	protected void setEnumClass(Class<U> enumClass) {
		this.enumClass = enumClass;
		if (getNextInChain() != null) {
			((EnumSpecification<T,U>)getNextInChain()).setEnumClass(enumClass);
		}
	}

	public void setValue(U value) {
		this.value = value;
		if (getNextInChain() != null) {
			((EnumSpecification<T,U>)getNextInChain()).setValue(value);
		}
		fireSpecificationChange();
	}

	public U getValue() {
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
		return getValue().equals(propertyValue);
	}

	public boolean wasSet() {
		return getValue() != null;
	}

}
package it.amattioli.dominate.specifications;

import java.io.Serializable;
import java.util.Collection;

import it.amattioli.dominate.Entity;
import it.amattioli.dominate.RepositoryRegistry;
import it.amattioli.dominate.properties.Properties;

public abstract class MultipleEntitySpecification<T extends Entity<?>, I extends Serializable, U extends Entity<I>> extends ChainedSpecification<T> {
	private String propertyName;
	private Collection<U> value;
	private Class<U> entityClass;
	
	public static <T extends Entity<?>, I extends Serializable, U extends Entity<I>> MultipleEntitySpecification<T, I, U> newInstance(String propertyName, Class<U> entityClass) {
		MultipleEntitySpecification<T, I, U> result = ChainedSpecification.createChain(MultipleEntitySpecification.class);
		result.setPropertyName(propertyName);
		result.setEntityClass(entityClass);
		return result;
	}
	
	public MultipleEntitySpecification() {
		
	}
	
	public MultipleEntitySpecification(String propertyName, Class<U> entityClass) {
		setPropertyName(propertyName);
		setEntityClass(entityClass);
	}

	protected String getPropertyName() {
		return propertyName;
	}
	
	protected void setPropertyName(String propertyName) {
		this.propertyName = propertyName;
		if (getNextInChain() != null) {
			((MultipleEntitySpecification<T, I, U>)getNextInChain()).setPropertyName(propertyName);
		}
	}

	protected Class<U> getEntityClass() {
		return entityClass;
	}
	
	protected void setEntityClass(Class<U> entityClass) {
		this.entityClass = entityClass;
		if (getNextInChain() != null) {
			((MultipleEntitySpecification<T, I, U>)getNextInChain()).setEntityClass(entityClass);
		}
	}
	
	public void setValue(Collection<U> value) {
		this.value = value;
		if (getNextInChain() != null) {
			((MultipleEntitySpecification<T, I, U>)getNextInChain()).setValue(value);
		}
		fireSpecificationChange();
	}

	public Collection<U> getValue() {
		return value;
	}
	
	public Collection<U> getAdmittedValues() {
    	return RepositoryRegistry.instance().getRepository(entityClass).list();
	}
	
	@Override
	public boolean isSatisfiedBy(T entity) {
		if (!wasSet()) {
			return isSatisfiedIfNotSet();
		}
		String propertyValue = (String)Properties.get(entity, getPropertyName());
		return getValue().contains(propertyValue);
	}

	public boolean wasSet() {
		return getValue() != null && !getValue().isEmpty();
	}
}

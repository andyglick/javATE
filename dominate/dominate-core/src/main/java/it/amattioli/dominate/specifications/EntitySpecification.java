package it.amattioli.dominate.specifications;

import java.io.Serializable;
import java.util.Collection;

import it.amattioli.dominate.Entity;
import it.amattioli.dominate.RepositoryRegistry;
import it.amattioli.dominate.properties.Properties;

public abstract class EntitySpecification<T extends Entity<?>, I extends Serializable, U extends Entity<I>> extends ChainedSpecification<T> {
	private String propertyName;
	private U value;
	private Class<U> entityClass;
	
	public static <T extends Entity<?>, I extends Serializable, U extends Entity<I>> EntitySpecification<T, I, U> newInstance(String propertyName, Class<U> entityClass) {
		EntitySpecification<T, I, U> result = createChain(EntitySpecification.class);
		result.setPropertyName(propertyName);
		result.setEntityClass(entityClass);
		return result;
	}
	
	public EntitySpecification() {
		
	}
	
	public EntitySpecification(String propertyName, Class<U> entityClass) {
		setPropertyName(propertyName);
		setEntityClass(entityClass);
	}
	
	public EntitySpecification(String propertyName, Class<U> entityClass, EntitySpecification<T, I, U> chained) {
		super(chained);
		setPropertyName(propertyName);
		setEntityClass(entityClass);
	}

	protected String getPropertyName() {
		return propertyName;
	}
	
	protected void setPropertyName(String propertyName) {
		this.propertyName = propertyName;
		if (getNextInChain() != null) {
			((EntitySpecification<T, I, U>)getNextInChain()).setPropertyName(propertyName);
		}
	}
	
	protected Class<U> getEntityClass() {
		return entityClass;
	}
	
	protected void setEntityClass(Class<U> entityClass) {
		this.entityClass = entityClass;
		if (getNextInChain() != null) {
			((EntitySpecification<T, I, U>)getNextInChain()).setEntityClass(entityClass);
		}
	}
	
	public void setValue(U value) {
		this.value = value;
		if (getNextInChain() != null) {
			((EntitySpecification<T, I, U>)getNextInChain()).setValue(value);
		}
		fireSpecificationChange();
	}

	public U getValue() {
		return value;
	}
	
	public void setId(I id) {
		setValue(RepositoryRegistry.instance().getRepository(entityClass).get(id));
	}
	
	public Collection<U> getAdmittedValues() {
    	return RepositoryRegistry.instance().getRepository(entityClass).list();
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

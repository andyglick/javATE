package it.amattioli.dominate.jpa;

import it.amattioli.dominate.Entity;
import it.amattioli.dominate.Repository;
import it.amattioli.dominate.repositories.AbstractRepositoryFactory;

import java.io.Serializable;
import java.util.Collection;

import javax.persistence.metamodel.Metamodel;


/**
 * Creates a JpaRepository for objects of the given class if this class
 * is mapped with JPA.
 * If the given class is not mapped with JPA the {@link #getRepository(Class)}
 * method will return null.<p>
 * 
 * As this class extends {@link AbstractRepositoryFactory} it will first look for a 
 * custom factory method before trying to instantiate a default repository.
 * 
 * @author andrea
 *
 */
public class JpaRepositoryFactory extends AbstractRepositoryFactory {
	
	private boolean isPersisted(Class<?> c) {
		Metamodel metamodel = JpaSessionManager.getEntityManagerFactory().getMetamodel();
		try {
			metamodel.entity(c);
		} catch(IllegalArgumentException e) {
			return false;
		}
		return true;
	}
	
	@Override
	public <I extends Serializable, T extends Entity<I>> Repository<I, T> getRepository(Collection<T> coll) {
		return null;
	}

	protected <I extends Serializable,T extends Entity<I>> Repository<I, T> getDefaultRepository(Class<T> c) {
		if (!isPersisted(c)) {
			return null;
		}
		JpaRepository<I,T> result = new JpaRepository<I, T>(c);
		return result;
	}
	
	public <I extends Serializable,T extends Entity<I>> Repository<I,T> cloneRepository(JpaRepository<I,T> rep) throws CloneNotSupportedException {
		JpaRepository<I,T> result = (JpaRepository<I,T>)rep.clone();
		return result;
	}
	
}

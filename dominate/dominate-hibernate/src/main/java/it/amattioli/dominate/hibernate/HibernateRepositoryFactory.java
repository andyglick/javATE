package it.amattioli.dominate.hibernate;

import it.amattioli.dominate.Entity;
import it.amattioli.dominate.Repository;
import it.amattioli.dominate.memory.CollectionRepository;
import it.amattioli.dominate.repositories.AbstractRepositoryFactory;

import java.io.Serializable;
import java.util.Collection;
import java.util.Map;

import org.hibernate.collection.AbstractPersistentCollection;

/**
 * Creates a ClassHibernateRepository for objects of the given class if this class
 * is mapped with Hibernate.
 * If the given class is not mapped with Hibernate the {@link #getRepository(Class)}
 * method will return null.<p>
 * 
 * As this class extends {@link AbstractRepositoryFactory} it will first look for a 
 * custom factory method before trying to instantiate a default repository.
 * 
 * @author andrea
 *
 */
public class HibernateRepositoryFactory extends AbstractRepositoryFactory {
	
	private boolean isPersisted(Class<?> c) {
		Map<?,?> metadata = HibernateSessionManager.getSessionFactory().getAllClassMetadata();
		return metadata.containsKey(c.getName());
	}
	
	@Override
	public <I extends Serializable, T extends Entity<I>> Repository<I, T> getRepository(Collection<T> coll) {
		if (coll instanceof AbstractPersistentCollection) {
			CollectionHibernateRepository<I, T> result = new CollectionHibernateRepository<I, T>(coll);
			return result;
		} else {
			return new CollectionRepository<I, T>(coll);
		}
	}

	protected <I extends Serializable,T extends Entity<I>> Repository<I, T> getDefaultRepository(Class<T> c) {
		if (!isPersisted(c)) {
			return null;
		}
		AbstractHibernateRepository<I,T> result = new ClassHibernateRepository<I, T>(c);
		return result;
	}
	
	public <I extends Serializable,T extends Entity<I>> Repository<I,T> cloneRepository(AbstractHibernateRepository<I,T> rep) throws CloneNotSupportedException {
		AbstractHibernateRepository<I,T> result = (AbstractHibernateRepository<I,T>)rep.clone();
		return result;
	}
	
}

package it.amattioli.dominate.jcr;

import java.io.Serializable;
import java.util.Collection;

import org.apache.jackrabbit.ocm.exception.IncorrectPersistentClassException;

import it.amattioli.dominate.Entity;
import it.amattioli.dominate.Repository;
import it.amattioli.dominate.repositories.AbstractRepositoryFactory;

public class JcrRepositoryFactory extends AbstractRepositoryFactory {

	private boolean isPersisted(Class<?> c) {
		try {
			return JcrSessionManager.getMapper().getClassDescriptorByClass(c) != null;
		} catch(IncorrectPersistentClassException e) {
			return false;
		}
	}
	
	@Override
	protected <I extends Serializable, T extends Entity<I>> Repository<I, T> getDefaultRepository(Class<T> c) {
		if (!isPersisted(c)) {
			return null;
		}
		return new JcrRepository<I,T>(c);
	}

	@Override
	public <I extends Serializable, T extends Entity<I>> Repository<I, T> getRepository(Collection<T> coll) {
		throw new UnsupportedOperationException();
	}

}

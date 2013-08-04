package it.amattioli.dominate.morphia;

import java.io.Serializable;
import java.util.Collection;

import it.amattioli.dominate.Entity;
import it.amattioli.dominate.Repository;
import it.amattioli.dominate.repositories.AbstractRepositoryFactory;

public class MorphiaRepositoryFactory extends AbstractRepositoryFactory {
	
	private boolean isPersisted(Class<?> c) {
		return MorphiaSessionManager.getMorphia().isMapped(c);
	}

	@Override
	protected <I extends Serializable, T extends Entity<I>> Repository<I, T> getDefaultRepository(Class<T> c) {
		if (!isPersisted(c)) {
			return null;
		}
		return new MorphiaRepository<I, T>(c);
	}

	@Override
	public <I extends Serializable, T extends Entity<I>> Repository<I, T> getRepository(Collection<T> coll) {
		throw new UnsupportedOperationException();
	}

}

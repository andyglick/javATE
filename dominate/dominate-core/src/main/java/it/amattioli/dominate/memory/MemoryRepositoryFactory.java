package it.amattioli.dominate.memory;

import it.amattioli.dominate.Entity;
import it.amattioli.dominate.Repository;
import it.amattioli.dominate.repositories.AbstractRepositoryFactory;

import java.io.Serializable;
import java.util.Collection;

public class MemoryRepositoryFactory extends AbstractRepositoryFactory {
	
	@Override
	public <I extends Serializable, T extends Entity<I>> Repository<I, T> getRepository(Collection<T> coll) {
		return new CollectionRepository<I, T>(coll);
	}

	@Override
	protected <I extends Serializable, T extends Entity<I>> Repository<I, T> getDefaultRepository(Class<T> c) {
		return new MemoryRepository<I, T>();
	}

}
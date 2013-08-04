package it.amattioli.springate;

import java.io.Serializable;
import java.util.Collection;

import it.amattioli.dominate.Entity;
import it.amattioli.dominate.Repository;
import it.amattioli.dominate.memory.MemoryRepository;
import it.amattioli.dominate.repositories.AbstractRepositoryFactory;

public class FakeRepositoryFactory extends AbstractRepositoryFactory {

	@Override
	protected <I extends Serializable, T extends Entity<I>> Repository<I, T> getDefaultRepository(Class<T> c) {
		return null;
	}

	@Override
	public <I extends Serializable, T extends Entity<I>> Repository<I, T> getRepository(Collection<T> coll) {
		return null;
	}

	public Repository<Long, FakeEntity> getFakeEntityRepository() {
		MemoryRepository<Long, FakeEntity> result = new MemoryRepository<Long, FakeEntity>();
		FakeEntity e1 = new FakeEntity();
		e1.setId(1L);
		e1.setDescription("One");
		result.put(e1);
		return result;
	}
	
}

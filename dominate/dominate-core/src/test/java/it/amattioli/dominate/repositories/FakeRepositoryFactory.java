package it.amattioli.dominate.repositories;

import it.amattioli.dominate.Entity;
import it.amattioli.dominate.FakeEntity;
import it.amattioli.dominate.Repository;
import it.amattioli.dominate.memory.MemoryRepository;

import java.io.Serializable;
import java.util.Collection;

public class FakeRepositoryFactory extends AbstractRepositoryFactory {
	private Repository<Long,FakeEntity> expected = new MemoryRepository<Long, FakeEntity>();

	@Override
	public <I extends Serializable, T extends Entity<I>> Repository<I, T> getRepository(Collection<T> coll) {
		return null;
	}
	
	@Override
	protected <I extends Serializable, T extends Entity<I>> Repository<I, T> getDefaultRepository(Class<T> c) {
		return null;
	}
	
	public Repository<Long, FakeEntity> getFakeEntityRepository() {
		return expected;
	}
	
}

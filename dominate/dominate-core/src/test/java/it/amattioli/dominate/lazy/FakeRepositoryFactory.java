package it.amattioli.dominate.lazy;

import it.amattioli.dominate.Repository;
import it.amattioli.dominate.memory.MemoryRepository;
import it.amattioli.dominate.memory.MemoryRepositoryFactory;

public class FakeRepositoryFactory extends MemoryRepositoryFactory {

	public Repository<Long, LazyFakeEntity> getLazyFakeEntityRepository() {
		return new MemoryRepository<Long, LazyFakeEntity>() {
			{
				LazyFakeEntity e1 = new LazyFakeEntity();
				e1.setId(1L);
				e1.setDescription("Description1");
				put(e1);
			}
			private boolean getCalled = false;

			@Override
			public LazyFakeEntity get(Long id) {
				getCalled = true;
				return super.get(id);
			}
			
			public boolean isGetCalled() {
				return getCalled;
			}

		};
	}
	
}

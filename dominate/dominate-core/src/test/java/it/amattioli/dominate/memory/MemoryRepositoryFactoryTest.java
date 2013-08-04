package it.amattioli.dominate.memory;

import java.util.ArrayList;

import it.amattioli.dominate.FakeEntity;
import it.amattioli.dominate.Repository;
import it.amattioli.dominate.RepositoryFactory;
import junit.framework.TestCase;

public class MemoryRepositoryFactoryTest extends TestCase {

	public void testDefaultRepositoryConstruction() {
        RepositoryFactory fac = new MemoryRepositoryFactory();
        Repository<Long, FakeEntity> rep = fac.getRepository(FakeEntity.class);
        assertTrue(rep instanceof MemoryRepository);
	}
	
	public void testCollectionRepositoryConstruction() {
		RepositoryFactory fac = new MemoryRepositoryFactory();
        Repository<Long, FakeEntity> rep = fac.getRepository(new ArrayList());
        assertTrue(rep instanceof CollectionRepository);
	}
	
}

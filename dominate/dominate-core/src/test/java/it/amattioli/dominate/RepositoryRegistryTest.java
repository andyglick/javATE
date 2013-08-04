package it.amattioli.dominate;

import it.amattioli.dominate.memory.CollectionRepository;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import junit.framework.TestCase;

public class RepositoryRegistryTest extends TestCase {

	public void testClassRepository() {
		RepositoryRegistry.instance().setRepositoryFactoryClass(MockRepositoryFactory.class);
		Repository<Long,EntityImpl> rep = RepositoryRegistry.instance().getRepository(EntityImpl.class);
		assertTrue(rep instanceof MockRepository);
	}
	
	public void testCollectionrepository() {
		RepositoryRegistry.instance().setRepositoryFactoryClass(MockRepositoryFactory.class);
		Repository<Long,EntityImpl> rep = RepositoryRegistry.instance().getRepository(new ArrayList());
		assertTrue(rep instanceof CollectionRepository);
	}
	
	public void testNoRepositoryFactoryClass() {
		try {
			RepositoryRegistry.instance().setRepositoryFactoryClass(null);
			Repository<Long,EntityImpl> rep = RepositoryRegistry.instance().getRepository(EntityImpl.class);
			fail();
		} catch(IllegalStateException e) {
			
		}
	}
	
	public static class MockRepositoryFactory implements RepositoryFactory {
		
		@Override
		public <I extends Serializable, T extends Entity<I>> Repository<I, T> getRepository(Class<T> c) {
			Object rep = new MockRepository();
			return (Repository<I,T>)rep;
		}
		
		@Override
		public <I extends Serializable, T extends Entity<I>> Repository<I, T> getRepository(Collection<T> c) {
			return new CollectionRepository<I, T>(c);
		}
		
	}
	
}

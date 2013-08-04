package it.amattioli.dominate.repositories;

import it.amattioli.dominate.EntityImpl;
import it.amattioli.dominate.FakeEntity;

import junit.framework.TestCase;

public class CompositeRepositoryFactoryTest extends TestCase {

	public void testClassRepository() {
		CompositeRepositoryFactory repFactory = new CompositeRepositoryFactory();
		FakeRepositoryFactory factoryComposer = new FakeRepositoryFactory();
		repFactory.addFactory(factoryComposer);
		assertEquals(factoryComposer.getFakeEntityRepository(), repFactory.getRepository(FakeEntity.class));
	}
	
	public void testUnknownRepository() {
		CompositeRepositoryFactory repFactory = new CompositeRepositoryFactory();
		FakeRepositoryFactory factoryComposer = new FakeRepositoryFactory();
		repFactory.addFactory(factoryComposer);
		assertNull(repFactory.getRepository(EntityImpl.class));
	}
	
}

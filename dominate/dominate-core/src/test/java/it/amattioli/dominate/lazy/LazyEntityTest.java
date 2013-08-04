package it.amattioli.dominate.lazy;

import it.amattioli.dominate.RepositoryRegistry;
import junit.framework.TestCase;

public class LazyEntityTest extends TestCase {
	
	@Override
	protected void setUp() throws Exception {
		RepositoryRegistry.instance().setRepositoryFactoryClass(FakeRepositoryFactory.class);
	}
	
	public void testId() {
		LazyFakeEntity e = LazyEntity.newInstance(LazyFakeEntity.class, 1L);
		assertEquals(new Long(1), e.getId());
		assertFalse(((Lazy)e).wasLoaded());
	}

	public void testMethodCall() {
		LazyFakeEntity e = LazyEntity.newInstance(LazyFakeEntity.class, 1L);
		String desc = e.getDescription();
		assertEquals("Description1", desc);
		assertTrue(((Lazy)e).wasLoaded());
	}
	
	public void testNonExistingEntity() {
		LazyFakeEntity e = LazyEntity.newInstance(LazyFakeEntity.class, 10L);
		try {
			String desc = e.getDescription();
			fail("Should throw exception");
		} catch(LazyLoadingException ex) {
			assertEquals("Entity with id '10' does not exists in repository for class it.amattioli.dominate.lazy.LazyFakeEntity", 
					     ex.getMessage());
		}
	}
	
}

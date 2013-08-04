package it.amattioli.dominate.jpa;

import it.amattioli.dominate.resolver.CompositeEntityResolver;
import junit.framework.TestCase;

public class HibernateEntityResolverTest extends TestCase {

	@Override
	protected void setUp() throws Exception {
		JpaSessionManager.setPersistenceUnitName("dominate-jpa");
	}

	public void testMappedEntity() {
		JpaEntityResolver resolver = new JpaEntityResolver();
		assertEquals(FakeEntity.class, resolver.find("FakeEntity"));
	}
	
	public void testNotMappedEntity() {
		JpaEntityResolver resolver = new JpaEntityResolver();
		assertNull(resolver.find("NoEntity"));
	}
	
	public void testIfAutoLoadedInCompositeResolver() {
		CompositeEntityResolver composite = new CompositeEntityResolver();
		composite.loadChildren();
		assertEquals(FakeEntity.class, composite.find("FakeEntity"));
	}
	
	@Override
	protected void tearDown() {
		JpaSessionManager.setPersistenceUnitName(null);
	}
	
}

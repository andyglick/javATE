package it.amattioli.dominate.hibernate;

import it.amattioli.dominate.resolver.CompositeEntityResolver;
import junit.framework.TestCase;

public class HibernateEntityResolverTest extends TestCase {

	@Override
	protected void setUp() throws Exception {
		HibernateSessionManager.setCfgResource("test-hibernate.cfg.xml");
	}

	public void testMappedEntity() {
		HibernateEntityResolver resolver = new HibernateEntityResolver();
		assertEquals(FakeEntity.class, resolver.find("FakeEntity"));
	}
	
	public void testNotMappedEntity() {
		HibernateEntityResolver resolver = new HibernateEntityResolver();
		assertNull(resolver.find("NoEntity"));
	}
	
	public void testIfAutoLoadedInCompositeResolver() {
		CompositeEntityResolver composite = new CompositeEntityResolver();
		composite.loadChildren();
		assertEquals(FakeEntity.class, composite.find("FakeEntity"));
	}
	
	@Override
	protected void tearDown() {
		HibernateSessionManager.setCfgResource(null);
	}
	
}

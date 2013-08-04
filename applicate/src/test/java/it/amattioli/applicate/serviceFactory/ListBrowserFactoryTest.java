package it.amattioli.applicate.serviceFactory;

import it.amattioli.applicate.browsing.ListBrowserImpl;
import it.amattioli.applicate.serviceFactory.ListBrowserFactory;
import it.amattioli.dominate.EntityImpl;
import it.amattioli.dominate.resolver.EntityRegistry;
import junit.framework.TestCase;

public class ListBrowserFactoryTest extends TestCase {
	
	public void testKnowsService() {
		ListBrowserFactory factory = createFactory();	
		assertTrue(factory.knowsService("FakeEntityListBrowser"));
	}

	public void testUnknownService() {
		ListBrowserFactory factory = createFactory();
		assertFalse(factory.knowsService("FakeEntityBrowser"));
	}
	
	public void testUnknownEntity() {
		ListBrowserFactory factory = createFactory();
		assertFalse(factory.knowsService("NoEntityListBrowser"));
	}
	
	public void testServiceConstruction() {
		ListBrowserFactory factory = createFactory();
		
		Object brw = factory.createService("FakeEntityListBrowser");
		
		assertTrue(brw instanceof ListBrowserImpl);
	}
	
	private ListBrowserFactory createFactory() {
		EntityRegistry resolver = new EntityRegistry();
		resolver.register("FakeEntity", FakeEntity.class);
		ListBrowserFactory factory = new ListBrowserFactory(resolver);
		return factory;
	}
	
	public static class FakeEntity extends EntityImpl {
		
	}
	
}

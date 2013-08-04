package it.amattioli.applicate.serviceFactory;

import it.amattioli.applicate.browsing.ObjectBrowserImpl;
import it.amattioli.applicate.serviceFactory.ObjectBrowserFactory;
import it.amattioli.dominate.EntityImpl;
import it.amattioli.dominate.resolver.EntityRegistry;
import junit.framework.TestCase;

public class ObjectBrowserFactoryTest extends TestCase {
	
	public void testKnowsService() {
		ObjectBrowserFactory factory = createFactory();	
		assertTrue(factory.knowsService("FakeEntityObjectBrowser"));
	}

	public void testUnknownService() {
		ObjectBrowserFactory factory = createFactory();
		assertFalse(factory.knowsService("FakeEntityBrowser"));
	}
	
	public void testUnknownEntity() {
		ObjectBrowserFactory factory = createFactory();
		assertFalse(factory.knowsService("NoEntityObjectBrowser"));
	}
	
	public void testServiceConstruction() {
		ObjectBrowserFactory factory = createFactory();
		
		Object brw = factory.createService("FakeEntityObjectBrowser");
		
		assertTrue(brw instanceof ObjectBrowserImpl);
	}
	
	private ObjectBrowserFactory createFactory() {
		EntityRegistry resolver = new EntityRegistry();
		resolver.register("FakeEntity", FakeEntity.class);
		ObjectBrowserFactory factory = new ObjectBrowserFactory(resolver);
		return factory;
	}
	
	public static class FakeEntity extends EntityImpl {
		
	}
	
}

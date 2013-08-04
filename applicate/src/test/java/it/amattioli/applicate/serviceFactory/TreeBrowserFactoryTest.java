package it.amattioli.applicate.serviceFactory;

import it.amattioli.applicate.browsing.DefaultTreeBrowser;
import it.amattioli.dominate.EntityImpl;
import it.amattioli.dominate.resolver.EntityRegistry;
import junit.framework.TestCase;

public class TreeBrowserFactoryTest extends TestCase {
	
	public void testKnowsService() {
		TreeBrowserFactory factory = createFactory();	
		assertTrue(factory.knowsService("FakeEntityTreeBrowser"));
	}

	public void testUnknownService() {
		TreeBrowserFactory factory = createFactory();
		assertFalse(factory.knowsService("FakeEntityBrowser"));
	}
	
	public void testUnknownEntity() {
		TreeBrowserFactory factory = createFactory();
		assertFalse(factory.knowsService("NoEntityTreeBrowser"));
	}
	
	public void testServiceConstruction() {
		TreeBrowserFactory factory = createFactory();
		
		Object brw = factory.createService("FakeEntityTreeBrowser");
		
		assertTrue(brw instanceof DefaultTreeBrowser);
	}
	
	private TreeBrowserFactory createFactory() {
		EntityRegistry resolver = new EntityRegistry();
		resolver.register("FakeEntity", FakeEntity.class);
		TreeBrowserFactory factory = new TreeBrowserFactory(resolver);
		return factory;
	}
	
	public static class FakeEntity extends EntityImpl {
		
	}
	
}

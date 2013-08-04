package it.amattioli.applicate.serviceFactory;

import it.amattioli.applicate.browsing.EntitySelector;
import it.amattioli.applicate.commands.EntityEditorCommand;
import it.amattioli.dominate.EntityImpl;
import it.amattioli.dominate.resolver.EntityRegistry;
import junit.framework.TestCase;

public class EntitySelectorFactoryTest extends TestCase {
	
	public void testKnowsService() {
		EntitySelectorFactory factory = createFactory();	
		assertTrue(factory.knowsService("FakeEntityEntitySelector"));
	}

	public void testUnknownService() {
		EntitySelectorFactory factory = createFactory();
		assertFalse(factory.knowsService("FakeEntitySelector"));
	}
	
	public void testUnknownEntity() {
		EntitySelectorFactory factory = createFactory();
		assertFalse(factory.knowsService("NoEntityEntitySelector"));
	}
	
	public void testServiceConstruction() {
		EntitySelectorFactory factory = createFactory();
		
		Object brw = factory.createService("FakeEntityEntitySelector");
		
		assertTrue(brw instanceof EntitySelector);
	}
	
	private EntitySelectorFactory createFactory() {
		EntityRegistry resolver = new EntityRegistry();
		resolver.register("FakeEntity", FakeEntity.class);
		EntitySelectorFactory factory = new EntitySelectorFactory(resolver);
		return factory;
	}
	
	public static class FakeEntity extends EntityImpl {
		
	}
	
}

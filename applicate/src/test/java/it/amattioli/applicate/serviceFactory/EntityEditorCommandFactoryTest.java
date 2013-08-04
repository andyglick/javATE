package it.amattioli.applicate.serviceFactory;

import it.amattioli.applicate.commands.EntityEditorCommand;
import it.amattioli.dominate.EntityImpl;
import it.amattioli.dominate.resolver.EntityRegistry;
import junit.framework.TestCase;

public class EntityEditorCommandFactoryTest extends TestCase {
	
	public void testKnowsService() {
		EntityEditorCommandFactory factory = createFactory();	
		assertTrue(factory.knowsService("FakeEntityEntityEditorCommand"));
	}

	public void testUnknownService() {
		EntityEditorCommandFactory factory = createFactory();
		assertFalse(factory.knowsService("FakeEntityEditor"));
	}
	
	public void testUnknownEntity() {
		EntityEditorCommandFactory factory = createFactory();
		assertFalse(factory.knowsService("NoEntityEntityEditorCommand"));
	}
	
	public void testServiceConstruction() {
		EntityEditorCommandFactory factory = createFactory();
		
		Object brw = factory.createService("FakeEntityEntityEditorCommand");
		
		assertTrue(brw instanceof EntityEditorCommand);
	}
	
	private EntityEditorCommandFactory createFactory() {
		EntityRegistry resolver = new EntityRegistry();
		resolver.register("FakeEntity", FakeEntity.class);
		EntityEditorCommandFactory factory = new EntityEditorCommandFactory(resolver);
		return factory;
	}
	
	public static class FakeEntity extends EntityImpl {
		
	}
	
}

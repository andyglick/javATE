package it.amattioli.applicate.serviceFactory;

import it.amattioli.applicate.commands.tree.TreeEditorCommand;
import it.amattioli.dominate.EntityImpl;
import it.amattioli.dominate.resolver.EntityRegistry;
import junit.framework.TestCase;

public class TreeEditorCommandFactoryTest extends TestCase {
	
	public void testKnowsService() {
		TreeEditorCommandFactory factory = createFactory();	
		assertTrue(factory.knowsService("FakeEntityTreeEditorCommand"));
	}

	public void testUnknownService() {
		TreeEditorCommandFactory factory = createFactory();
		assertFalse(factory.knowsService("FakeEntityEditor"));
	}
	
	public void testUnknownEntity() {
		TreeEditorCommandFactory factory = createFactory();
		assertFalse(factory.knowsService("NoEntityTreeEditorCommand"));
	}
	
	public void testServiceConstruction() {
		TreeEditorCommandFactory factory = createFactory();
		
		Object brw = factory.createService("FakeEntityTreeEditorCommand");
		
		assertTrue(brw instanceof TreeEditorCommand);
	}
	
	private TreeEditorCommandFactory createFactory() {
		EntityRegistry resolver = new EntityRegistry();
		resolver.register("FakeEntity", FakeEntity.class);
		TreeEditorCommandFactory factory = new TreeEditorCommandFactory(resolver);
		return factory;
	}
	
	public static class FakeEntity extends EntityImpl {
		
	}
	
}

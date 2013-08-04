package it.amattioli.applicate.serviceFactory;

import it.amattioli.applicate.editors.RepositoryEditor;
import it.amattioli.dominate.EntityImpl;
import it.amattioli.dominate.resolver.EntityRegistry;
import junit.framework.TestCase;

public class RepositoryEditorFactoryTest extends TestCase {
	
	public void testKnowsService() {
		RepositoryEditorFactory factory = createFactory();	
		assertTrue(factory.knowsService("FakeEntityRepositoryEditor"));
	}

	public void testUnknownService() {
		RepositoryEditorFactory factory = createFactory();
		assertFalse(factory.knowsService("FakeEntityEditor"));
	}
	
	public void testUnknownEntity() {
		RepositoryEditorFactory factory = createFactory();
		assertFalse(factory.knowsService("NoEntityEntityRepositoryEditor"));
	}
	
	public void testServiceConstruction() {
		RepositoryEditorFactory factory = createFactory();
		
		Object brw = factory.createService("FakeEntityRepositoryEditor");
		
		assertTrue(brw instanceof RepositoryEditor);
	}
	
	private RepositoryEditorFactory createFactory() {
		EntityRegistry resolver = new EntityRegistry();
		resolver.register("FakeEntity", FakeEntity.class);
		RepositoryEditorFactory factory = new RepositoryEditorFactory(resolver);
		return factory;
	}
	
	public static class FakeEntity extends EntityImpl {
		
	}
	
}

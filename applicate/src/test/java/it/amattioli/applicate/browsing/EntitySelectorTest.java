package it.amattioli.applicate.browsing;

import it.amattioli.dominate.RepositoryRegistry;
import it.amattioli.dominate.memory.MemoryRepository;
import it.amattioli.dominate.validation.ValidationResult;
import it.amattioli.dominate.validation.ValidationResult.ResultType;
import junit.framework.TestCase;

public class EntitySelectorTest extends TestCase {
	
	public void testDefaultSearchProperty() {
		EntitySelector<Long, EntityStub> selector = new EntitySelector<Long, EntityStub>(EntityStub.class, new ListBrowserImpl<Long, EntityStub>(createRepository()));
		assertEquals(1,selector.getSearchPropertyNames().size());
		assertTrue(selector.getSearchPropertyNames().contains("description"));
	}

	public void testExactMatch() {
		EntitySelector<Long, EntityStub> selector = new EntitySelector<Long, EntityStub>(EntityStub.class, new ListBrowserImpl<Long, EntityStub>(createRepository()), "property1");
		selector.setSearchValue("ENTITY1");
		assertEquals(EntitySelector.Match.EXACT, selector.getMatch());
		assertEquals("ENTITY1", selector.getSearchValue());
	}
	
	public void testExactMatchMultipleProperties() {
		EntitySelector<Long, EntityStub> selector = new EntitySelector<Long, EntityStub>(EntityStub.class, new ListBrowserImpl<Long, EntityStub>(createRepository()), "property1", "property2");
		selector.setSearchValue("VALUE1");
		assertEquals(EntitySelector.Match.EXACT, selector.getMatch());
		assertEquals("ENTITY1", selector.getSearchValue());
	}
	
	public void testMultipleMatch() {
		ListBrowserImpl<Long, EntityStub> entityBrowser = new ListBrowserImpl<Long, EntityStub>(createRepository());
		EntitySelector<Long, EntityStub> selector = new EntitySelector<Long, EntityStub>(EntityStub.class, entityBrowser, "property1");
		selector.setSearchValue("ENTITY");
		assertEquals(EntitySelector.Match.MULTIPLE, selector.getMatch());
		for (EntityStub curr: entityBrowser.getList()) {
			assertTrue(curr.getProperty1().contains("ENTITY"));
		}
	}
	
	public void testSelection() {
		ListBrowserImpl<Long, EntityStub> entityBrowser = new ListBrowserImpl<Long, EntityStub>(createRepository());
		EntitySelector<Long, EntityStub> selector = new EntitySelector<Long, EntityStub>(EntityStub.class, entityBrowser, "property1");
		selector.setSearchValue("ENTITY");
		EntityStub toBeSelected = entityBrowser.getList().get(0);
		entityBrowser.select(0);
		assertEquals("ENTITY1", selector.getSearchValue());
		assertEquals(toBeSelected, selector.getSelectedObject());
	}
	
	public void testBinding() {
		MemoryRepository<Long, EntityStub> repository = createRepository();
		ListBrowserImpl<Long, EntityStub> entityBrowser = new ListBrowserImpl<Long, EntityStub>(repository);
		EntitySelector<Long, EntityStub> selector = new EntitySelector<Long, EntityStub>(repository, entityBrowser, "property1");
		BoundObject boundObject = new BoundObject();
		selector.bindTo(boundObject, "boundProperty");
		selector.setSelectedObjectId(1L);
		assertEquals(selector.getSelectedObject(), boundObject.getBoundProperty());
	}
	
	public void testValid() {
		MemoryRepository<Long, EntityStub> repository = createRepository();
		ListBrowserImpl<Long, EntityStub> entityBrowser = new ListBrowserImpl<Long, EntityStub>(repository);
		EntitySelector<Long, EntityStub> selector = new EntitySelector<Long, EntityStub>(repository, entityBrowser, "property1");
		BoundObject boundObject = new BoundObject();
		selector.bindTo(boundObject, "boundProperty");
		selector.setSelectedObjectId(1L);
		ValidationResult result = selector.validate();
		assertEquals(ResultType.VALID, result.getType());
	}
	
	public void testNotValid() {
		MemoryRepository<Long, EntityStub> repository = createRepository();
		ListBrowserImpl<Long, EntityStub> entityBrowser = new ListBrowserImpl<Long, EntityStub>(repository);
		EntitySelector<Long, EntityStub> selector = new EntitySelector<Long, EntityStub>(repository, entityBrowser, "property1");
		BoundObject boundObject = new BoundObject();
		selector.bindTo(boundObject, "boundProperty");
		ValidationResult result = selector.validate();
		assertEquals(ResultType.INVALID, result.getType());
	}

	private MemoryRepository<Long, EntityStub> createRepository() {
		MemoryRepository<Long, EntityStub> result = new MemoryRepository<Long, EntityStub>();
		EntityStub e1 = new EntityStub();
		e1.setId(1L);
		e1.setProperty1("ENTITY1");
		e1.setProperty2("VALUE1");
		result.put(e1);
		EntityStub e2 = new EntityStub();
		e2.setId(2L);
		e2.setProperty1("ENTITY2");
		e2.setProperty2("VALUE2");
		result.put(e2);
		return result;
	}
	
}

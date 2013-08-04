package it.amattioli.applicate.commands.tree;

import java.util.List;

import it.amattioli.applicate.browsing.CompositeStub;
import it.amattioli.dominate.Repository;
import it.amattioli.dominate.memory.MemoryRepository;
import junit.framework.TestCase;

public class DefaultTreeEditorTest extends TestCase {
	private Repository<Long, CompositeStub> rep;
	
	@Override
	protected void setUp() throws Exception {
		rep = createComposite();
	}

	public Repository<Long, CompositeStub> createComposite() {
		Repository<Long, CompositeStub> result = new MemoryRepository<Long, CompositeStub>();
		
		CompositeStub c0 = new CompositeStub(0L);
		result.put(c0);
		
		CompositeStub c1 = new CompositeStub(1L);
		c0.addChild(c1);
		result.put(c1);
		
		CompositeStub c11 = new CompositeStub(11L);
		c1.addChild(c11);
		result.put(c11);
		
		CompositeStub c12 = new CompositeStub(12L);
		c1.addChild(c12);
		result.put(c12);
		
		CompositeStub c2 = new CompositeStub(2L);
		c0.addChild(c2);
		result.put(c2);
		
		return result;
	}
	
	public TreeEditor<Long,CompositeStub> createEditor() {
		DefaultTreeEditor<Long,CompositeStub> result = new DefaultTreeEditor<Long,CompositeStub>();
		result.setTreeManager(new DefaultTreeManager<CompositeStub>(rep.get(0L)));
		return result;
	}
	
	public void testChildren() {
		TreeEditor<Long,CompositeStub> e = createEditor();
		List<CompositeStub> children = e.getChildrenOf(rep.get(1L));
		assertEquals(2, children.size());
		assertTrue(children.contains(rep.get(11L)));
		assertTrue(children.contains(rep.get(12L)));
	}
	
	public void testAddChild() {
		TreeEditor<Long,CompositeStub> e = createEditor();
		e.select(rep.get(1L));
		CompositeStub added = e.addChild();
		assertTrue(e.getChildrenOf(rep.get(1L)).contains(added));
	}
	
	public void testRemove() {
		TreeEditor<Long,CompositeStub> e = createEditor();
		e.select(rep.get(11L));
		CompositeStub removed = e.remove();
		assertFalse(e.getChildrenOf(rep.get(1L)).contains(removed));
	}
}

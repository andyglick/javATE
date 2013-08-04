package it.amattioli.applicate.browsing;

import it.amattioli.applicate.commands.AbstractCommand;
import it.amattioli.applicate.commands.Command;
import it.amattioli.applicate.commands.CommandEvent;
import it.amattioli.applicate.commands.CommandResult;
import it.amattioli.applicate.selection.MockSelectionListener;
import it.amattioli.dominate.Repository;
import it.amattioli.dominate.memory.MemoryRepository;
import it.amattioli.dominate.specifications.LongSpecification;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import junit.framework.TestCase;

public class TreeBrowserTest extends TestCase {
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
	
	public DefaultTreeBrowser<Long, CompositeStub> createTree() {
		DefaultTreeBrowser<Long, CompositeStub> result = 
			new DefaultTreeBrowser<Long, CompositeStub>(rep, 0L, "children", "parent");
		return result;
	}

	public void testRoot() {
		DefaultTreeBrowser<Long, CompositeStub> tree = createTree();
		assertEquals(new Long(0), tree.getRoot().getId());
	}
	
	public void testChildren() {
		DefaultTreeBrowser<Long, CompositeStub> tree = createTree();
		List<CompositeStub> children = tree.getChildrenOf(rep.get(1L));
		assertEquals(2, children.size());
		assertTrue(children.contains(rep.get(11L)));
		assertTrue(children.contains(rep.get(12L)));
	}
	
	public void testChildrenOfNull() {
		DefaultTreeBrowser<Long, CompositeStub> tree = createTree();
		List<CompositeStub> children = tree.getChildrenOf(null);
		assertTrue(children.isEmpty());
	}
	
	public void testParent() {
		DefaultTreeBrowser<Long, CompositeStub> tree = createTree();
		CompositeStub parent = tree.getParentOf(rep.get(1L));
		assertEquals(rep.get(0L), parent);
	}
	
	public void testParentOfNull() {
		DefaultTreeBrowser<Long, CompositeStub> tree = createTree();
		CompositeStub parent = tree.getParentOf(null);
		assertNull(parent);
	}
	
	public void testPath() {
		DefaultTreeBrowser<Long, CompositeStub> tree = createTree();
		assertTrue(Arrays.equals(new int[] {0,0}, tree.getPathOf(rep.get(11L)).asIntArray()));
	}
	
	public void testTarget() {
		DefaultTreeBrowser<Long, CompositeStub> tree = createTree();
		assertEquals(rep.get(11L), tree.getTargetOf(new TreePath(0,0)));
	}
	
	public void testSelectByPath() {
		DefaultTreeBrowser<Long, CompositeStub> tree = createTree();
		tree.select(new TreePath(0,1));
		assertEquals(rep.get(12L), tree.getSelectedObject());
	}
	
	public void testSelectByObject() {
		DefaultTreeBrowser<Long, CompositeStub> tree = createTree();
		tree.select(rep.get(12L));
		assertEquals(rep.get(12L), tree.getSelectedObject());
	}
	
	public void testNextOfNull() {
		DefaultTreeBrowser<Long, CompositeStub> tree = createTree();
		tree.next();
		assertEquals(rep.get(1L), tree.getSelectedObject());
	}
	
	public void testNextWithFellow() {
		DefaultTreeBrowser<Long, CompositeStub> tree = createTree();
		tree.select(rep.get(11L));
		tree.next();
		assertEquals(rep.get(12L), tree.getSelectedObject());
	}
	
	public void testNextWithChildren() {
		DefaultTreeBrowser<Long, CompositeStub> tree = createTree();
		tree.select(rep.get(1L));
		tree.next();
		assertEquals(rep.get(11L), tree.getSelectedObject());
	}
	
	public void testNextLastChildren() {
		DefaultTreeBrowser<Long, CompositeStub> tree = createTree();
		tree.select(rep.get(12L));
		tree.next();
		assertEquals(rep.get(2L), tree.getSelectedObject());
	}
	
	public void testNextOfLast() {
		DefaultTreeBrowser<Long, CompositeStub> tree = createTree();
		tree.select(rep.get(2L));
		tree.next();
		assertEquals(rep.get(2L), tree.getSelectedObject());
	}
	
	public void testNextWithSpecification() {
		DefaultTreeBrowser<Long, CompositeStub> tree = createTree();
		LongSpecification<CompositeStub> spec = LongSpecification.newInstance("id");
		spec.setValue(12L);
		tree.setSpecification(spec);
		tree.next();
		assertEquals(rep.get(12L), tree.getSelectedObject());
	}
	
	public void testNextWithSpecificationNotification() {
		DefaultTreeBrowser<Long, CompositeStub> tree = createTree();
		LongSpecification<CompositeStub> spec = LongSpecification.newInstance("id");
		spec.setValue(12L);
		tree.setSpecification(spec);
		MockSelectionListener listener = new MockSelectionListener();
		tree.addSelectionListener(listener);
		tree.next();
		assertEquals(1, listener.getNotificationsNumber());
	}
	
	public void testPreviousWithChildren() {
		DefaultTreeBrowser<Long, CompositeStub> tree = createTree();
		tree.select(rep.get(2L));
		tree.previous();
		assertEquals(rep.get(12L), tree.getSelectedObject());
	}
	
	public void testPreviousWithFellow() {
		DefaultTreeBrowser<Long, CompositeStub> tree = createTree();
		tree.select(rep.get(12L));
		tree.previous();
		assertEquals(rep.get(11L), tree.getSelectedObject());
	}
	
	public void testPreviousFirstChildren() {
		DefaultTreeBrowser<Long, CompositeStub> tree = createTree();
		tree.select(rep.get(11L));
		tree.previous();
		assertEquals(rep.get(1L), tree.getSelectedObject());
	}
	
	public void testPreviousWithSpecification() {
		DefaultTreeBrowser<Long, CompositeStub> tree = createTree();
		LongSpecification<CompositeStub> spec = LongSpecification.newInstance("id");
		spec.setValue(11L);
		tree.setSpecification(spec);
		tree.select(rep.get(2L));
		tree.previous();
		assertEquals(rep.get(11L), tree.getSelectedObject());
	}
	
	public void testPreviousWithSpecificationNotification() {
		DefaultTreeBrowser<Long, CompositeStub> tree = createTree();
		tree.select(rep.get(2L));
		LongSpecification<CompositeStub> spec = LongSpecification.newInstance("id");
		spec.setValue(11L);
		tree.setSpecification(spec);
		MockSelectionListener listener = new MockSelectionListener();
		tree.addSelectionListener(listener);
		tree.previous();
		assertEquals(1, listener.getNotificationsNumber());
	}
	
	public void testContentChangeNotificationOnCommandEvent() {
		DefaultTreeBrowser<Long, CompositeStub> tree = createTree();
		MockContentChangeListener listener = new MockContentChangeListener();
		tree.addContentChangeListener(listener);
		Command source = new AbstractCommand() {};
		tree.commandDone(new CommandEvent(source, CommandResult.SUCCESSFUL));
		assertTrue(listener.isNotified());
	}
	
	public void testContentChangeNotificationOnRootChange() {
		DefaultTreeBrowser<Long, CompositeStub> tree = createTree();
		MockContentChangeListener listener = new MockContentChangeListener();
		tree.addContentChangeListener(listener);
		tree.setRootId(1L);
		assertTrue(listener.isNotified());
	}
	
	public void testSelectables() {
		DefaultTreeBrowser<Long, CompositeStub> tree = createTree();
		LongSpecification<CompositeStub> spec = LongSpecification.newInstance("id");
		spec.setValue(12L);
		tree.setSpecification(spec);
		Collection<CompositeStub> result = tree.getSelectables();
		for (CompositeStub curr: result) {
			assertTrue(spec.isSatisfiedBy(curr));
		}
	}
	
	public void testSelectSatisfied() {
		DefaultTreeBrowser<Long, CompositeStub> tree = createTree();
		LongSpecification<CompositeStub> spec = LongSpecification.newInstance("id");
		spec.setValue(12L);
		tree.setSpecification(spec);
		tree.selectSatisfied();
		CompositeStub selectedObject = tree.getSelectedObject();
		assertTrue(spec.isSatisfiedBy(selectedObject));
	}
	
	public void testNoSelectionAfterSpecificationSet() {
		DefaultTreeBrowser<Long, CompositeStub> tree = createTree();
		tree.select(new TreePath(0,1));
		LongSpecification<CompositeStub> spec = LongSpecification.newInstance("id");
		tree.setSpecification(spec);
		assertNull(tree.getSelectedObject());
	}
	
	public void testNoSelectionAfterSpecificationChange() {
		DefaultTreeBrowser<Long, CompositeStub> tree = createTree();
		LongSpecification<CompositeStub> spec = LongSpecification.newInstance("id");
		tree.setSpecification(spec);
		tree.select(new TreePath(0,1));
		spec.setValue(123L);
		assertNull(tree.getSelectedObject());
	}
}

package it.amattioli.applicate.browsing;

import it.amattioli.applicate.commands.CommandEvent;
import it.amattioli.applicate.commands.CommandResult;
import it.amattioli.applicate.commands.MockCommandListener;
import it.amattioli.applicate.commands.NullCommand;
import it.amattioli.dominate.Entity;
import it.amattioli.dominate.EntityImpl;

import java.util.List;

import org.apache.commons.collections.Predicate;
import org.apache.commons.lang.StringUtils;

import junit.framework.TestCase;

import it.amattioli.dominate.Repository;
import it.amattioli.dominate.memory.MemoryRepository;
import it.amattioli.applicate.properties.MockPropertyChangeListener;
import it.amattioli.dominate.specifications.LongSpecification;
import it.amattioli.dominate.specifications.beans.BeanSpecification;

public class ListBrowserTest extends TestCase {

    protected MockRepository buildRepository(int num) {
    	MockRepository rep = new MockRepository(num);
        return rep;
    }

    protected ListBrowser<Long, Entity<Long>> buildBrowser(Repository<Long, Entity<Long>> rep) {
        return new ListBrowserImpl<Long, Entity<Long>>(rep);
    }
    
    public void testDefaultSpecification() {
    	ListBrowser<Long, EntityImpl> brw = new ListBrowserImpl<Long, EntityImpl>(EntityImpl.class);
    	assertTrue(brw.getSpecification() instanceof BeanSpecification);
    }

    public void testList() {
    	MockRepository rep = buildRepository(5);
    	ListBrowser<Long, Entity<Long>> brw = buildBrowser(rep);
        List<Entity<Long>> list = brw.getList();
        assertEquals(rep.list(), list);
        assertEquals(2, rep.getListCalls());
        assertEquals(0, rep.getListSpecificationCalls());
    }

    public void testSelect() {
        Repository<Long, Entity<Long>> rep = buildRepository(5);
        ListBrowser<Long, Entity<Long>> brw = new ListBrowserImpl<Long, Entity<Long>>(rep);
        brw.select(0);
        assertEquals(new Integer(0), brw.getSelectedIndex());
        assertEquals(brw.getList().get(0), brw.getSelectedObject());
    }
    
    public void testWrongSelectionIndex() {
    	Repository<Long, Entity<Long>> rep = buildRepository(2);
        ListBrowser<Long, Entity<Long>> brw = new ListBrowserImpl<Long, Entity<Long>>(rep);
        try {
        	brw.select(5);
        	fail();
        } catch(ArrayIndexOutOfBoundsException e) {
        	
        }
    }

    public void testSelectByEntity() {
        Repository<Long, Entity<Long>> rep = buildRepository(5);
        ListBrowserImpl<Long, Entity<Long>> brw = new ListBrowserImpl<Long, Entity<Long>>(rep);
        Entity<Long> toBeSelected = rep.get(0L);
		brw.select(toBeSelected);
        assertEquals(toBeSelected, brw.getSelectedObject());
    }
    
    public void testNext() {
        Repository<Long, Entity<Long>> rep = buildRepository(5);
        ListBrowser<Long, Entity<Long>> brw = new ListBrowserImpl<Long, Entity<Long>>(rep);
        brw.select(0);
        brw.next();
        assertEquals(new Integer(1), brw.getSelectedIndex());
        assertEquals(brw.getList().get(1), brw.getSelectedObject());
    }

    public void testHasNext() {
        Repository<Long, Entity<Long>> rep = buildRepository(5);
        ListBrowser<Long, Entity<Long>> brw = new ListBrowserImpl<Long, Entity<Long>>(rep);
        brw.select(0);
        assertTrue(brw.getHasNext());
        brw.select(4);
        assertFalse(brw.getHasNext());
    }
    
    public void testNextUntilLast() {
        Repository<Long, Entity<Long>> rep = buildRepository(5);
        ListBrowser<Long, Entity<Long>> brw = new ListBrowserImpl<Long, Entity<Long>>(rep);
        brw.select(0);
        while (brw.getHasNext()) {
            brw.next();
        }
        assertEquals(new Integer(4), brw.getSelectedIndex());
        assertEquals(brw.getList().get(4), brw.getSelectedObject());
    }

    public void testPrevious() {
        Repository<Long, Entity<Long>> rep = buildRepository(5);
        ListBrowser<Long, Entity<Long>> brw = new ListBrowserImpl<Long, Entity<Long>>(rep);
        brw.select(2);
        brw.previous();
        assertEquals(new Integer(1), brw.getSelectedIndex());
        assertEquals(brw.getList().get(1), brw.getSelectedObject());
    }
    
    public void testHasPrevious() {
        Repository<Long, Entity<Long>> rep = buildRepository(5);
        ListBrowser<Long, Entity<Long>> brw = new ListBrowserImpl<Long, Entity<Long>>(rep);
        brw.select(0);
        assertFalse(brw.getHasPrevious());
        brw.select(4);
        assertTrue(brw.getHasPrevious());
    }
    
    public void testPreviousUntilFirst() {
        Repository<Long, Entity<Long>> rep = buildRepository(5);
        ListBrowser<Long, Entity<Long>> brw = new ListBrowserImpl<Long, Entity<Long>>(rep);
        brw.select(4);
        while (brw.getHasPrevious()) {
            brw.previous();
        }
        assertEquals(new Integer(0), brw.getSelectedIndex());
        assertEquals(brw.getList().get(0), brw.getSelectedObject());
    }
    
    public void testAscendingOrder() {
    	Repository<Long, Entity<Long>> rep = buildRepository(5);
        ListBrowser<Long, Entity<Long>> brw = new ListBrowserImpl<Long, Entity<Long>>(rep);
        brw.setOrder("id");
        Long prevId = null;
        for (Entity<Long> curr : brw.getList()) {
        	assertTrue(prevId == null || prevId < curr.getId());
        	prevId = curr.getId();
        }
    }
    
    public void testDescendingOrder() {
    	Repository<Long, Entity<Long>> rep = buildRepository(5);
        ListBrowser<Long, Entity<Long>> brw = new ListBrowserImpl<Long, Entity<Long>>(rep);
        brw.setOrder("id", true);
        Long prevId = null;
        for (Entity<Long> curr : brw.getList()) {
        	assertTrue(prevId == null || prevId > curr.getId());
        	prevId = curr.getId();
        }
    }
    
    public void testMultiOrdering() {
    	Repository<Long, FakeEntity> rep = new MemoryRepository<Long, FakeEntity>();
		FakeEntity e1 = new FakeEntity();
		e1.setId(1L);
		e1.setDescription("two");
		e1.setConstrainted("b");
		rep.put(e1);
		FakeEntity e2 = new FakeEntity();
		e2.setId(2L);
		e2.setDescription("two");
		e2.setConstrainted("a");
		rep.put(e2);
		FakeEntity e3 = new FakeEntity();
		e3.setId(3L);
		e3.setDescription("three");
		e3.setConstrainted("c");
		rep.put(e3);
		ListBrowser<Long, FakeEntity> brw = new ListBrowserImpl<Long, FakeEntity>(rep);
		brw.setOrder("description");
		brw.addOrder("constrainted");
		List<FakeEntity> list = brw.getList();
		String prevDescription = null;
		String prevConstrainted = null;
		for (FakeEntity curr: list) {
			if (StringUtils.equals(prevDescription, curr.getDescription())) {
				assertTrue(prevConstrainted == null || prevConstrainted.compareTo(curr.getConstrainted()) < 0);
			} else {
				assertTrue(prevDescription == null || prevDescription.compareTo(curr.getDescription()) < 0);
			}
			prevDescription = curr.getDescription();
			prevConstrainted = curr.getConstrainted();
		}
    }
    
    public void testMultiOrderingDescending() {
    	Repository<Long, FakeEntity> rep = new MemoryRepository<Long, FakeEntity>();
		FakeEntity e1 = new FakeEntity();
		e1.setId(1L);
		e1.setDescription("two");
		e1.setConstrainted("a");
		rep.put(e1);
		FakeEntity e2 = new FakeEntity();
		e2.setId(2L);
		e2.setDescription("two");
		e2.setConstrainted("b");
		rep.put(e2);
		FakeEntity e3 = new FakeEntity();
		e3.setId(3L);
		e3.setDescription("three");
		e3.setConstrainted("c");
		rep.put(e3);
		ListBrowser<Long, FakeEntity> brw = new ListBrowserImpl<Long, FakeEntity>(rep);
		brw.setOrder("description");
		brw.addOrder("constrainted");
		brw.addOrder("constrainted");
		List<FakeEntity> list = brw.getList();
		String prevDescription = null;
		String prevConstrainted = null;
		for (FakeEntity curr: list) {
			if (StringUtils.equals(prevDescription, curr.getDescription())) {
				assertTrue(prevConstrainted == null || prevConstrainted.compareTo(curr.getConstrainted()) > 0);
			} else {
				assertTrue(prevDescription == null || prevDescription.compareTo(curr.getDescription()) < 0);
			}
			prevDescription = curr.getDescription();
			prevConstrainted = curr.getConstrainted();
		}
    }
    
    public void testChangeOrder() {
    	Repository<Long, Entity<Long>> rep = buildRepository(5);
        ListBrowser<Long, Entity<Long>> brw = new ListBrowserImpl<Long, Entity<Long>>(rep);
        brw.setOrder("id");
        brw.getList();
        brw.setOrder("id");
        Long prevId = null;
        for (Entity<Long> curr : brw.getList()) {
        	assertTrue(prevId == null || prevId > curr.getId());
        	prevId = curr.getId();
        }
    }

    public void testSelectionAfterOrdering() {
    	Repository<Long, Entity<Long>> rep = buildRepository(5);
        ListBrowser<Long, Entity<Long>> brw = new ListBrowserImpl<Long, Entity<Long>>(rep);
        brw.select(4);
        Entity<Long> sel = brw.getSelectedObject();
        brw.setOrder("id", true);
        assertEquals(sel, brw.getSelectedObject());
    }
        
    public void testSpecification() {
    	MockRepository rep = buildRepository(5);
        ListBrowser<Long, Entity<Long>> brw = new ListBrowserImpl<Long, Entity<Long>>(rep);
        LongSpecification<Entity<Long>> spec = LongSpecification.newInstance("id");
        spec.setValue(2L);
        brw.setSpecification(spec);
        assertEquals(1, brw.getSelectables().size());
        assertEquals(0, rep.getListCalls());
        assertEquals(1, rep.getListSpecificationCalls());
    }
    
    public void testSelectSatisfied() {
    	MockRepository rep = buildRepository(5);
        ListBrowser<Long, Entity<Long>> brw = new ListBrowserImpl<Long, Entity<Long>>(rep);
        LongSpecification<Entity<Long>> spec = LongSpecification.newInstance("id");
        spec.setValue(2L);
        brw.setSpecification(spec);
        brw.selectSatisfied();
        assertEquals(new Long(2), brw.getSelectedObject().getId());
    }

    public void testObjectBrowser() {
    	Repository<Long, Entity<Long>> rep = buildRepository(5);
        ListBrowser<Long, Entity<Long>> brw = new ListBrowserImpl<Long, Entity<Long>>(rep);
        brw.select(0);
        ObjectBrowser<Long, Entity<Long>> obrw = brw.getSelectedObjectBrowser();
        assertEquals(brw.getSelectedObject().getId(), obrw.getHold().getId());
        brw.select(1);
        assertEquals(brw.getSelectedObject().getId(), obrw.getHold().getId());
    }
    
    public void testCommandEvent() {
    	Repository<Long, Entity<Long>> rep = buildRepository(5);
        ListBrowserImpl<Long, Entity<Long>> brw = new ListBrowserImpl<Long, Entity<Long>>(rep);
        MockCommandListener cmdListener = new MockCommandListener();
        brw.addCommandListener(cmdListener);
        brw.select(0);
        CommandEvent evt = new CommandEvent(new NullCommand(), CommandResult.SUCCESSFUL);
        brw.commandDone(evt);
        assertEquals(1, cmdListener.getNumCommandDoneReceived());
    }
    
    public void testInvalidSelectionAfterCommand() {
    	Repository<Long, Entity<Long>> rep = buildRepository(1);
    	ListBrowserImpl<Long, Entity<Long>> brw = new ListBrowserImpl<Long, Entity<Long>>(rep);
    	brw.select(0);
    	rep.remove(0L);
    	CommandEvent evt = new CommandEvent(new NullCommand(), CommandResult.SUCCESSFUL);
        brw.commandDone(evt);
        assertNull(brw.getSelectedIndex());
    }
    
    public void testPropertyChangeOnCommandDone() {
    	Repository<Long, Entity<Long>> rep = buildRepository(5);
        ListBrowserImpl<Long, Entity<Long>> brw = new ListBrowserImpl<Long, Entity<Long>>(rep);
        MockPropertyChangeListener chgListener = new MockPropertyChangeListener("list");
        brw.addPropertyChangeListener(chgListener);
        brw.select(0);
        CommandEvent evt = new CommandEvent(new NullCommand(), CommandResult.SUCCESSFUL);
        brw.commandDone(evt);
        assertEquals(1, chgListener.getNumPropertyChangeNotified());
    }
    
    public void testContentChangeOnCommandDone() {
    	Repository<Long, Entity<Long>> rep = buildRepository(5);
        ListBrowserImpl<Long, Entity<Long>> brw = new ListBrowserImpl<Long, Entity<Long>>(rep);
        MockContentChangeListener chgListener = new MockContentChangeListener();
        brw.addContentChangeListener(chgListener);
        brw.select(0);
        CommandEvent evt = new CommandEvent(new NullCommand(), CommandResult.SUCCESSFUL);
        brw.commandDone(evt);
        assertTrue(chgListener.isNotified());
    }
    
    public void testCommandEventPropagation() {
    	Repository<Long, Entity<Long>> rep = buildRepository(5);
        ListBrowserImpl<Long, Entity<Long>> brw = new ListBrowserImpl<Long, Entity<Long>>(rep);
        MockCommandListener lbrwCmdListener = new MockCommandListener();
        brw.addCommandListener(lbrwCmdListener);
        brw.select(0);
        ObjectBrowserImpl<Long, Entity<Long>> obrw = (ObjectBrowserImpl)brw.getSelectedObjectBrowser();
        MockCommandListener obrwCmdListener = new MockCommandListener();
        obrw.addCommandListener(obrwCmdListener);
        CommandEvent evt = new CommandEvent(new NullCommand(), CommandResult.SUCCESSFUL);
        brw.commandDone(evt);
        assertEquals(1, lbrwCmdListener.getNumCommandDoneReceived());
        assertEquals(1, obrwCmdListener.getNumCommandDoneReceived());
    }

}

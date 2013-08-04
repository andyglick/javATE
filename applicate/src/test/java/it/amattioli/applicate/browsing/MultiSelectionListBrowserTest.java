package it.amattioli.applicate.browsing;

import java.util.List;

import it.amattioli.dominate.Entity;
import it.amattioli.dominate.Repository;
import junit.framework.TestCase;

public class MultiSelectionListBrowserTest extends TestCase {

	protected MockRepository buildRepository(int num) {
    	MockRepository rep = new MockRepository(num);
        return rep;
    }

    protected ListBrowser<Long, Entity<Long>> buildBrowser(Repository<Long, Entity<Long>> rep) {
        ListBrowserImpl<Long, Entity<Long>> result = new ListBrowserImpl<Long, Entity<Long>>(rep);
        result.setMultipleSelection(true);
		return result;
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
        ListBrowser<Long, Entity<Long>> brw = buildBrowser(rep);
        brw.select(0);
        assertEquals(new Integer(0), brw.getSelectedIndex());
        assertEquals(brw.getList().get(0), brw.getSelectedObject());
    }
    
    public void testWrongSelectionIndex() {
    	Repository<Long, Entity<Long>> rep = buildRepository(2);
    	ListBrowser<Long, Entity<Long>> brw = buildBrowser(rep);
        try {
        	brw.select(5);
        	fail();
        } catch(ArrayIndexOutOfBoundsException e) {
        	
        }
    }
    
    public void testSelectByEntity() {
        Repository<Long, Entity<Long>> rep = buildRepository(5);
        ListBrowser<Long, Entity<Long>> brw = buildBrowser(rep);
        Entity<Long> toBeSelected = rep.get(0L);
		brw.select(toBeSelected);
        assertEquals(toBeSelected, brw.getSelectedObject());
    }
}

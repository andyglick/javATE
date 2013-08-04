package it.amattioli.applicate.browsing;

import it.amattioli.dominate.Entity;
import it.amattioli.dominate.EntityImpl;
import it.amattioli.dominate.Repository;
import it.amattioli.dominate.memory.MemoryRepository;
import it.amattioli.dominate.specifications.LongSpecification;
import it.amattioli.dominate.specifications.TotalOrderComparisonType;

import java.util.List;

import junit.framework.TestCase;

public class PagedListBrowserTest extends TestCase {

	protected Repository<Long, Entity<Long>> buildRepository(int num) {
        Repository<Long, Entity<Long>> rep = new MemoryRepository<Long, Entity<Long>>();
        for (int i = 0; i < num; i++) {
            Entity<Long> e = new EntityImpl();
            e.setId(new Long(i));
            rep.put(e);
        }
        return rep;
    }

    protected PagedListBrowser<Long, Entity<Long>> buildBrowser(Repository<Long, Entity<Long>> rep) {
        PagedListBrowserImpl<Long, Entity<Long>> result = new PagedListBrowserImpl<Long, Entity<Long>>(rep);
        result.setPageSize(15);
		return result;
    }

    public void testList() {
    	Repository<Long, Entity<Long>> rep = buildRepository(25);
    	PagedListBrowser<Long, Entity<Long>> brw = buildBrowser(rep);
        List<Entity<Long>> list = brw.getList();
        assertEquals(brw.getPageSize(), list.size());
        assertEquals(new Long(0L), list.get(0).getId());
        assertEquals(new Long(14L), list.get(list.size()-1).getId());
    }
    
    public void testNextPage() {
    	Repository<Long, Entity<Long>> rep = buildRepository(25);
    	PagedListBrowser<Long, Entity<Long>> brw = buildBrowser(rep);
        List<Entity<Long>> list = brw.getList();
        brw.nextPage();
        list = brw.getList();
        assertEquals(10, list.size());
        assertEquals(new Long(15L), list.get(0).getId());
        assertEquals(new Long(24L), list.get(list.size()-1).getId());
    }
    
    public void testPageNumberAfterSpecification() {
    	Repository<Long, Entity<Long>> rep = buildRepository(25);
    	PagedListBrowser<Long, Entity<Long>> brw = buildBrowser(rep);
        List<Entity<Long>> list = brw.getList();
        brw.nextPage();
        assertEquals(new Long(2), brw.getCurrentPageNumber());
        LongSpecification<Entity<Long>> spec = LongSpecification.newInstance("id");
        spec.setComparisonType(TotalOrderComparisonType.LOWER_EQ);
        spec.setValue(5L);
        brw.setSpecification(spec);
        assertEquals(new Long(1), brw.getCurrentPageNumber());
    }

}

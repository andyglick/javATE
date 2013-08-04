package it.amattioli.dominate.memory;

import junit.framework.TestCase;
import it.amattioli.dominate.Entity;
import it.amattioli.dominate.EntityImpl;
import it.amattioli.dominate.FakeEntity;
import it.amattioli.dominate.Repository;
import it.amattioli.dominate.memory.MemoryRepository;
import it.amattioli.dominate.specifications.LongSpecification;
import it.amattioli.dominate.specifications.TotalOrderComparisonType;

public class MemoryRepositoryTest extends TestCase {
	
	public void testPutAndGet() {
		Repository<Long, EntityImpl> rep = new MemoryRepository<Long, EntityImpl>();
		EntityImpl e1 = new EntityImpl();
		e1.setId(1L);
		rep.put(e1);
		assertEquals(e1, rep.get(1L));
	}
	
	public void testRemove() {
		Repository<Long, EntityImpl> rep = new MemoryRepository<Long, EntityImpl>();
		EntityImpl e1 = new EntityImpl();
		e1.setId(1L);
		rep.put(e1);
		assertTrue(rep.isRemoveAllowed());
		rep.remove(e1);
		assertNull(rep.get(1L));
	}
	
	public void testList() {
		Repository<Long, EntityImpl> rep = new MemoryRepository<Long, EntityImpl>();
		EntityImpl e1 = new EntityImpl();
		e1.setId(1L);
		rep.put(e1);
		EntityImpl e2 = new EntityImpl();
		e2.setId(2L);
		rep.put(e2);
		assertEquals(2, rep.list().size());
		assertTrue(rep.list().contains(e1));
		assertTrue(rep.list().contains(e2));
	}
	
	public void testOrderProperty() {
		Repository<Long, FakeEntity> rep = new MemoryRepository<Long, FakeEntity>();
		rep.setOrder("description", false);
		assertEquals("description", rep.getOrderProperty());
		assertEquals(false, rep.isReverseOrder());
	}
	
	public void testOrderedList() {
		Repository<Long, FakeEntity> rep = new MemoryRepository<Long, FakeEntity>();
		FakeEntity e1 = new FakeEntity();
		e1.setId(1L);
		e1.setDescription("two");
		rep.put(e1);
		FakeEntity e2 = new FakeEntity();
		e2.setId(2L);
		e2.setDescription("one");
		rep.put(e2);
		FakeEntity e3 = new FakeEntity();
		e3.setId(3L);
		e3.setDescription("three");
		rep.put(e3);
		rep.setOrder("description", false);
		FakeEntity prec = null;
		for (FakeEntity curr: rep.list()) {
			if (prec != null) {
				assertTrue(prec.getDescription().compareTo(curr.getDescription()) < 0);
			}
			prec = curr;
		}
	}
	
	public void testMultiOrderedList() {
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
		rep.setOrder("description", false);
		rep.addOrder("constrainted", false);
		FakeEntity prec = null;
		for (FakeEntity curr: rep.list()) {
			if (prec != null) {
				if (prec.getDescription().compareTo(curr.getDescription()) == 0) {
					assertTrue(prec.getConstrainted().compareTo(curr.getConstrainted()) < 0);
				} else {
					assertTrue(prec.getDescription().compareTo(curr.getDescription()) < 0);
				}
			}
			prec = curr;
		}
	}
	
	public void testReverseOrderedList() {
		Repository<Long, FakeEntity> rep = new MemoryRepository<Long, FakeEntity>();
		FakeEntity e1 = new FakeEntity();
		e1.setId(1L);
		e1.setDescription("two");
		rep.put(e1);
		FakeEntity e2 = new FakeEntity();
		e2.setId(2L);
		e2.setDescription("one");
		rep.put(e2);
		FakeEntity e3 = new FakeEntity();
		e3.setId(3L);
		e3.setDescription("three");
		rep.put(e3);
		rep.setOrder("description", true);
		FakeEntity prec = null;
		for (FakeEntity curr: rep.list()) {
			if (prec != null) {
				assertTrue(prec.getDescription().compareTo(curr.getDescription()) > 0);
			}
			prec = curr;
		}
	}
	
	public void testGetByPropertyValue() {
		Repository<Long, FakeEntity> rep = new MemoryRepository<Long, FakeEntity>();
		FakeEntity e1 = new FakeEntity();
		e1.setId(1L);
		e1.setDescription("one");
		rep.put(e1);
		FakeEntity e2 = new FakeEntity();
		e2.setId(2L);
		e2.setDescription("two");
		rep.put(e2);
		FakeEntity e3 = new FakeEntity();
		e3.setId(3L);
		e3.setDescription("three");
		rep.put(e3);
		assertEquals(e2, rep.getByPropertyValue("description", "two"));
		assertNull(rep.getByPropertyValue("description", "four"));
	}
	
	public void testLimitedList() {
		Repository<Long, Entity<Long>> rep = new MemoryRepository<Long, Entity<Long>>();
        for (int i = 0; i < 10; i++) {
            Entity<Long> e = new EntityImpl();
            e.setId(new Long(i));
            rep.put(e);
        }
        rep.setFirst(4);
        rep.setLast(6);
        assertEquals(3, rep.list().size());
	}
	
	public void testLimitedListWithSpec() {
		Repository<Long, Entity<Long>> rep = new MemoryRepository<Long, Entity<Long>>();
        for (int i = 0; i < 20; i++) {
            Entity<Long> e = new EntityImpl();
            e.setId(new Long(i));
            rep.put(e);
        }
        rep.setFirst(4);
        rep.setLast(6);
        LongSpecification<Entity<Long>> spec = LongSpecification.newInstance("id");
        spec.setComparisonType(TotalOrderComparisonType.GREATER_EQ);
        spec.setValue(10L);
        assertEquals(3, rep.list(spec).size());
	}
	
	public void testWrongLastLimit() {
		Repository<Long, Entity<Long>> rep = new MemoryRepository<Long, Entity<Long>>();
        for (int i = 0; i < 10; i++) {
            Entity<Long> e = new EntityImpl();
            e.setId(new Long(i));
            rep.put(e);
        }
        rep.setFirst(4);
        rep.setLast(16);
        assertEquals(6, rep.list().size());
	}
	
	public void testWrongLastLimitWithSpec() {
		Repository<Long, Entity<Long>> rep = new MemoryRepository<Long, Entity<Long>>();
        for (int i = 0; i < 20; i++) {
            Entity<Long> e = new EntityImpl();
            e.setId(new Long(i));
            rep.put(e);
        }
        rep.setFirst(4);
        rep.setLast(16);
        LongSpecification<Entity<Long>> spec = LongSpecification.newInstance("id");
        spec.setComparisonType(TotalOrderComparisonType.GREATER_EQ);
        spec.setValue(10L);
        assertEquals(6, rep.list(spec).size());
	}
	
	public void testWrongFirstLimit() {
		Repository<Long, Entity<Long>> rep = new MemoryRepository<Long, Entity<Long>>();
        for (int i = 0; i < 10; i++) {
            Entity<Long> e = new EntityImpl();
            e.setId(new Long(i));
            rep.put(e);
        }
        rep.setFirst(14);
        rep.setLast(8);
        assertEquals(0, rep.list().size());
	}
	
	public void testWrongFirstLimitWithSpec() {
		Repository<Long, Entity<Long>> rep = new MemoryRepository<Long, Entity<Long>>();
        for (int i = 0; i < 20; i++) {
            Entity<Long> e = new EntityImpl();
            e.setId(new Long(i));
            rep.put(e);
        }
        rep.setFirst(14);
        rep.setLast(8);
        LongSpecification<Entity<Long>> spec = LongSpecification.newInstance("id");
        spec.setComparisonType(TotalOrderComparisonType.GREATER_EQ);
        spec.setValue(10L);
        assertEquals(0, rep.list(spec).size());
	}
	
	public void testWrongBothLimits() {
		Repository<Long, Entity<Long>> rep = new MemoryRepository<Long, Entity<Long>>();
        for (int i = 0; i < 10; i++) {
            Entity<Long> e = new EntityImpl();
            e.setId(new Long(i));
            rep.put(e);
        }
        rep.setFirst(14);
        rep.setLast(18);
        assertEquals(0, rep.list().size());
	}
	
	public void testInvertedLimits() {
		Repository<Long, Entity<Long>> rep = new MemoryRepository<Long, Entity<Long>>();
        for (int i = 0; i < 10; i++) {
            Entity<Long> e = new EntityImpl();
            e.setId(new Long(i));
            rep.put(e);
        }
        rep.setFirst(8);
        rep.setLast(2);
        assertEquals(0, rep.list().size());
	}
	
	public void testInvertedLimitsWithSpec() {
		Repository<Long, Entity<Long>> rep = new MemoryRepository<Long, Entity<Long>>();
        for (int i = 0; i < 20; i++) {
            Entity<Long> e = new EntityImpl();
            e.setId(new Long(i));
            rep.put(e);
        }
        rep.setFirst(8);
        rep.setLast(2);
        LongSpecification<Entity<Long>> spec = LongSpecification.newInstance("id");
        spec.setComparisonType(TotalOrderComparisonType.GREATER_EQ);
        spec.setValue(10L);
        assertEquals(0, rep.list(spec).size());
	}
	
	public void testRemoveLastOrder() {
		MemoryRepository<Long, Entity<Long>> rep = new MemoryRepository<Long, Entity<Long>>();
		rep.setOrder("property1", false);
		rep.addOrder("property2", false);
		rep.removeLastOrder();
		assertEquals(1,rep.getOrders().size());
		assertEquals("property1", rep.getOrders().get(0).getProperty());
		assertEquals("property1", rep.getOrderProperty());
	}
}

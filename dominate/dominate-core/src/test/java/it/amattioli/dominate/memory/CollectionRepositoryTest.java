package it.amattioli.dominate.memory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import it.amattioli.dominate.Entity;
import it.amattioli.dominate.EntityImpl;
import it.amattioli.dominate.Repository;
import it.amattioli.dominate.specifications.ComparisonType;
import it.amattioli.dominate.specifications.MyEntity;
import it.amattioli.dominate.specifications.StringSpecification;
import junit.framework.TestCase;

public class CollectionRepositoryTest extends TestCase {

	public void testGet() {
		ArrayList<MyEntity> coll = new ArrayList<MyEntity>();
		MyEntity e1 = new MyEntity();
		e1.setId(1L);
		coll.add(e1);
		MyEntity e2 = new MyEntity();
		e2.setId(2L);
		coll.add(e2);
		CollectionRepository<Long, MyEntity> rep = new CollectionRepository<Long, MyEntity>(coll);
		assertEquals(e1, rep.get(1L));
	}
	
	public void testGetNull() {
		ArrayList<MyEntity> coll = new ArrayList<MyEntity>();
		MyEntity e1 = new MyEntity();
		e1.setId(1L);
		coll.add(e1);
		MyEntity e2 = new MyEntity();
		e2.setId(2L);
		coll.add(e2);
		CollectionRepository<Long, MyEntity> rep = new CollectionRepository<Long, MyEntity>(coll);
		assertNull(rep.get(null));
	}
	
	public void testGetByPropertyValue() {
		ArrayList<MyEntity> coll = new ArrayList<MyEntity>();
		MyEntity e1 = new MyEntity();
		e1.setId(1L);
		e1.setDescription("one");
		coll.add(e1);
		MyEntity e2 = new MyEntity();
		e2.setId(2L);
		e2.setDescription("two");
		coll.add(e2);
		CollectionRepository<Long, MyEntity> rep = new CollectionRepository<Long, MyEntity>(coll);
		assertEquals(e1, rep.getByPropertyValue("description", "one"));
	}
	
	public void testList() {
		ArrayList<MyEntity> coll = new ArrayList<MyEntity>();
		MyEntity e1 = new MyEntity();
		e1.setId(1L);
		coll.add(e1);
		MyEntity e2 = new MyEntity();
		e2.setId(2L);
		coll.add(e2);
		CollectionRepository<Long, MyEntity> rep = new CollectionRepository<Long, MyEntity>(coll);
		assertEquals(2, rep.list().size());
		assertTrue(rep.list().contains(e1));
		assertTrue(rep.list().contains(e2));
	}
	
	public void testOrderedList() {
		ArrayList<MyEntity> coll = new ArrayList<MyEntity>();
		MyEntity e1 = new MyEntity();
		e1.setId(1L);
		e1.setDescription("two");
		coll.add(e1);
		MyEntity e2 = new MyEntity();
		e2.setId(2L);
		e2.setDescription("one");
		coll.add(e2);
		MyEntity e3 = new MyEntity();
		e3.setId(3L);
		e3.setDescription("three");
		coll.add(e3);
		CollectionRepository<Long, MyEntity> rep = new CollectionRepository<Long, MyEntity>(coll);
		rep.setOrder("description", false);
		MyEntity prec = null;
		for (MyEntity curr: rep.list()) {
			if (prec == null) {
				prec = curr;
			} else {
				assertTrue(prec.getDescription().compareTo(curr.getDescription()) < 0);
			}
		}
	}
	
	public void testReverseOrderedList() {
		ArrayList<MyEntity> coll = new ArrayList<MyEntity>();
		MyEntity e1 = new MyEntity();
		e1.setId(1L);
		e1.setDescription("two");
		coll.add(e1);
		MyEntity e2 = new MyEntity();
		e2.setId(2L);
		e2.setDescription("one");
		coll.add(e2);
		MyEntity e3 = new MyEntity();
		e3.setId(3L);
		e3.setDescription("three");
		coll.add(e3);
		CollectionRepository<Long, MyEntity> rep = new CollectionRepository<Long, MyEntity>(coll);
		rep.setOrder("description", true);
		MyEntity prec = null;
		for (MyEntity curr: rep.list()) {
			if (prec == null) {
				prec = curr;
			} else {
				assertTrue(prec.getDescription().compareTo(curr.getDescription()) > 0);
			}
		}
	}
	
	public void testListWithSpecification() {
		ArrayList<MyEntity> coll = new ArrayList<MyEntity>();
		MyEntity e1 = new MyEntity();
		e1.setId(1L);
		e1.setDescription("Descr1");
		coll.add(e1);
		MyEntity e2 = new MyEntity();
		e2.setId(2L);
		e2.setDescription("Descr2");
		coll.add(e2);
		CollectionRepository<Long, MyEntity> rep = new CollectionRepository<Long, MyEntity>(coll);
		StringSpecification<MyEntity> spec = StringSpecification.newInstance("description");
		spec.setComparisonType(ComparisonType.EXACT);
		spec.setValue("Descr1");
		Collection<MyEntity> result = rep.list(spec);
		assertTrue(result.contains(e1));
		assertFalse(result.contains(e2));
	}
	
	public void testOrderedListWithSpecification() {
		ArrayList<MyEntity> coll = new ArrayList<MyEntity>();
		MyEntity e1 = new MyEntity();
		e1.setId(1L);
		e1.setDescription("two descr");
		coll.add(e1);
		MyEntity e2 = new MyEntity();
		e2.setId(2L);
		e2.setDescription("one descr");
		coll.add(e2);
		MyEntity e3 = new MyEntity();
		e3.setId(3L);
		e3.setDescription("three");
		coll.add(e3);
		CollectionRepository<Long, MyEntity> rep = new CollectionRepository<Long, MyEntity>(coll);
		rep.setOrder("description", true);
		StringSpecification<MyEntity> spec = StringSpecification.newInstance("description");
		spec.setComparisonType(ComparisonType.CONTAINS);
		spec.setValue("descr");
		MyEntity prec = null;
		List<MyEntity> result = rep.list(spec);
		assertEquals(2, result.size());
		for (MyEntity curr: result) {
			if (prec == null) {
				prec = curr;
			} else {
				assertTrue(prec.getDescription().compareTo(curr.getDescription()) > 0);
			}
		}
	}
	
	public void testLimitedList() {
		Repository<Long, Entity<Long>> rep = new CollectionRepository<Long, Entity<Long>>(new ArrayList<Entity<Long>>());
        for (int i = 0; i < 10; i++) {
            Entity<Long> e = new EntityImpl();
            e.setId(new Long(i));
            rep.put(e);
        }
        rep.setFirst(4);
        rep.setLast(6);
        assertEquals(3, rep.list().size());
	}
	
	public void testRemoveLastOrder() {
		CollectionRepository<Long, Entity<Long>> rep = new CollectionRepository<Long, Entity<Long>>(new ArrayList<Entity<Long>>());
		rep.setOrder("property1", false);
		rep.addOrder("property2", false);
		rep.removeLastOrder();
		assertEquals(1,rep.getOrders().size());
		assertEquals("property1", rep.getOrders().get(0).getProperty());
		assertEquals("property1", rep.getOrderProperty());
	}
}

package it.amattioli.dominate.hibernate;

import java.util.List;

import it.amattioli.dominate.specifications.ComparisonType;
import it.amattioli.dominate.sessions.SessionMode;
import it.amattioli.dominate.hibernate.specifications.HqlStringSpecification;

import org.hibernate.Transaction;

import junit.framework.TestCase;

public class CollectionHibernateRepositoryTest extends TestCase {

	@Override
	protected void setUp() throws Exception {
		HibernateTestInit.initWithAssoc();
	}
	
	public void testGet() {
		ClassHibernateRepository<Long, FakeEntity> frep = new ClassHibernateRepository<Long, FakeEntity>(FakeEntity.class);
		FakeEntity e = frep.getByPropertyValue("description", "Description 1");
		CollectionHibernateRepository<Long, AssociatedEntity> rep = new CollectionHibernateRepository<Long, AssociatedEntity>(e.getAssoc());
		AssociatedEntity a = rep.get(1L);
		assertEquals("Assoc 1", a.getAssocDescription());
	}
	
	public void testGetByPropertyValue() {
		ClassHibernateRepository<Long, FakeEntity> frep = new ClassHibernateRepository<Long, FakeEntity>(FakeEntity.class);
		FakeEntity e = frep.getByPropertyValue("description", "Description 1");
		CollectionHibernateRepository<Long, AssociatedEntity> rep = new CollectionHibernateRepository<Long, AssociatedEntity>(e.getAssoc());
		AssociatedEntity a = rep.getByPropertyValue("assocDescription", "Assoc 2");
		assertEquals("Assoc 2", a.getAssocDescription());
	}
	
	public void testList() {
		ClassHibernateRepository<Long, FakeEntity> frep = new ClassHibernateRepository<Long, FakeEntity>(FakeEntity.class);
		FakeEntity e = frep.getByPropertyValue("description", "Description 1");
		CollectionHibernateRepository<Long, AssociatedEntity> rep = new CollectionHibernateRepository<Long, AssociatedEntity>(e.getAssoc());
		assertEquals(3, rep.list().size());
	}
	
	public void testListWithSpecification() {
		ClassHibernateRepository<Long, FakeEntity> frep = new ClassHibernateRepository<Long, FakeEntity>(FakeEntity.class);
		FakeEntity e = frep.getByPropertyValue("description", "Description 1");
		CollectionHibernateRepository<Long, AssociatedEntity> rep = new CollectionHibernateRepository<Long, AssociatedEntity>(e.getAssoc());
		HqlStringSpecification<AssociatedEntity> spec = new HqlStringSpecification<AssociatedEntity>("assocDescription");
		spec.setComparisonType(ComparisonType.CONTAINS);
		spec.setValue("2");
		List<AssociatedEntity> result = rep.list(spec);
		assertEquals(1, result.size());
		assertEquals("Assoc 2", result.get(0).getAssocDescription());
	}
	
	public void testOrderedList() {
		ClassHibernateRepository<Long, FakeEntity> frep = new ClassHibernateRepository<Long, FakeEntity>(FakeEntity.class);
		FakeEntity e = frep.getByPropertyValue("description", "Description 1");
		CollectionHibernateRepository<Long, AssociatedEntity> rep = new CollectionHibernateRepository<Long, AssociatedEntity>(e.getAssoc());
		rep.setOrder("assocDescription", false);
		AssociatedEntity prec = null;
		for (AssociatedEntity curr: rep.list()) {
			if (prec == null) {
				prec = curr;
			} else {
				assertTrue(prec.getAssocDescription().compareTo(curr.getAssocDescription()) < 0);
			}
		}
	}
	
	public void testReverseOrderedList() {
		ClassHibernateRepository<Long, FakeEntity> frep = new ClassHibernateRepository<Long, FakeEntity>(FakeEntity.class);
		FakeEntity e = frep.getByPropertyValue("description", "Description 1");
		CollectionHibernateRepository<Long, AssociatedEntity> rep = new CollectionHibernateRepository<Long, AssociatedEntity>(e.getAssoc());
		rep.setOrder("assocDescription", true);
		AssociatedEntity prec = null;
		for (AssociatedEntity curr: rep.list()) {
			if (prec == null) {
				prec = curr;
			} else {
				assertTrue(prec.getAssocDescription().compareTo(curr.getAssocDescription()) > 0);
			}
		}
	}
	
	public void testLimitedList() {
		ClassHibernateRepository<Long, FakeEntity> frep = new ClassHibernateRepository<Long, FakeEntity>(FakeEntity.class);
		FakeEntity e = frep.getByPropertyValue("description", "Description 1");
		CollectionHibernateRepository<Long, AssociatedEntity> rep = new CollectionHibernateRepository<Long, AssociatedEntity>(e.getAssoc());
		rep.setFirst(1);
        rep.setLast(2);
        assertEquals(2, rep.list().size());
	}
	
	public void testPut() {
		HibernateSessionManager smgr = new HibernateSessionManager(SessionMode.THREAD_LOCAL);
		Transaction tx = smgr.getSession().beginTransaction();
		ClassHibernateRepository<Long, FakeEntity> frep = new ClassHibernateRepository<Long, FakeEntity>(FakeEntity.class);
		FakeEntity e = frep.getByPropertyValue("description", "Description 1");
		CollectionHibernateRepository<Long, AssociatedEntity> rep = new CollectionHibernateRepository<Long, AssociatedEntity>(e.getAssoc());
		AssociatedEntity a = new AssociatedEntity();
		a.setId(10L);
		rep.put(a);
		assertTrue(rep.list().contains(a));
	}
	
	public void testRemoveLastOrder() {
		ClassHibernateRepository<Long, FakeEntity> frep = new ClassHibernateRepository<Long, FakeEntity>(FakeEntity.class);
		FakeEntity e = frep.getByPropertyValue("description", "Description 1");
		CollectionHibernateRepository<Long, AssociatedEntity> rep = new CollectionHibernateRepository<Long, AssociatedEntity>(e.getAssoc());
		rep.setOrder("property1", false);
		rep.addOrder("property2", false);
		rep.removeLastOrder();
		assertEquals(1,rep.getOrders().size());
		assertEquals("property1", rep.getOrders().get(0).getProperty());
		assertEquals("property1", rep.getOrderProperty());
	}
	
	@Override
	protected void tearDown() throws Exception {
		HibernateSessionManager.disconnectAll();
		HibernateSessionManager.setCfgResource(null);
	}
	
}

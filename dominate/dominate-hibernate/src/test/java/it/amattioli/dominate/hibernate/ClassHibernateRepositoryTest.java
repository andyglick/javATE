package it.amattioli.dominate.hibernate;

import java.util.List;

import org.hibernate.Transaction;

import it.amattioli.dominate.specifications.ComparisonType;
import it.amattioli.dominate.sessions.SessionMode;
import it.amattioli.dominate.hibernate.specifications.CriteriaStringSpecification;
import it.amattioli.dominate.hibernate.specifications.HqlStringSpecification;
import junit.framework.TestCase;

public class ClassHibernateRepositoryTest extends TestCase {

	@Override
	protected void setUp() throws Exception {
		HibernateTestInit.init();
	}
	
	public void testGet() {
		ClassHibernateRepository<Long, FakeEntity> rep = new ClassHibernateRepository<Long, FakeEntity>(FakeEntity.class);
		FakeEntity e = rep.get(1L);
		assertEquals("Description 1", e.getDescription());
	}
	
//	public void testGetNull() {
//		ClassHibernateRepository<Long, FakeEntity> rep = new ClassHibernateRepository<Long, FakeEntity>(FakeEntity.class);
//		assertNull(rep.get(null));
//		HibernateSessionManager.disconnectAll();
//	}
	
	public void testGetByPropertyValue() {
		ClassHibernateRepository<Long, FakeEntity> rep = new ClassHibernateRepository<Long, FakeEntity>(FakeEntity.class);
		FakeEntity e = rep.getByPropertyValue("description", "Description 1");
		assertEquals("Description 1", e.getDescription());
	}
	
	public void testList() {
		ClassHibernateRepository<Long, FakeEntity> rep = new ClassHibernateRepository<Long, FakeEntity>(FakeEntity.class);
		List<FakeEntity> result = rep.list();
		assertEquals(4, result.size());
	}
	
	public void testOrderedList() {
		ClassHibernateRepository<Long, FakeEntity> rep = new ClassHibernateRepository<Long, FakeEntity>(FakeEntity.class);
		rep.setOrder("description", false);
		FakeEntity prec = null;
		for (FakeEntity curr: rep.list()) {
			if (prec == null) {
				prec = curr;
			} else {
				assertTrue(prec.getDescription().compareTo(curr.getDescription()) < 0);
			}
		}
	}
	
	public void testReverseOrderedList() {
		ClassHibernateRepository<Long, FakeEntity> rep = new ClassHibernateRepository<Long, FakeEntity>(FakeEntity.class);
		rep.setOrder("description", true);
		FakeEntity prec = null;
		for (FakeEntity curr: rep.list()) {
			if (prec == null) {
				prec = curr;
			} else {
				assertTrue(prec.getDescription().compareTo(curr.getDescription()) > 0);
			}
		}
	}
	
	public void testListWithCriteriaSpecification() {
		ClassHibernateRepository<Long, FakeEntity> rep = new ClassHibernateRepository<Long, FakeEntity>(FakeEntity.class);
		CriteriaStringSpecification<FakeEntity> spec = new CriteriaStringSpecification<FakeEntity>("description");
		spec.setComparisonType(ComparisonType.CONTAINS);
		spec.setValue("2");
		List<FakeEntity> result = rep.list(spec);
		assertEquals(1, result.size());
		assertEquals("Description 2", result.get(0).getDescription());
	}
	
	public void testOrderedListWithCriteriaSpecification() {
		ClassHibernateRepository<Long, FakeEntity> rep = new ClassHibernateRepository<Long, FakeEntity>(FakeEntity.class);
		CriteriaStringSpecification<FakeEntity> spec = new CriteriaStringSpecification<FakeEntity>("description");
		rep.setOrder("description", false);
		spec.setComparisonType(ComparisonType.STARTS);
		spec.setValue("Description");
		FakeEntity prec = null;
		for (FakeEntity curr: rep.list(spec)) {
			if (prec == null) {
				prec = curr;
			} else {
				assertTrue(prec.getDescription().compareTo(curr.getDescription()) < 0);
			}
		}
	}
	
	public void testListWithHqlSpecification() {
		ClassHibernateRepository<Long, FakeEntity> rep = new ClassHibernateRepository<Long, FakeEntity>(FakeEntity.class);
		HqlStringSpecification<FakeEntity> spec = new HqlStringSpecification<FakeEntity>("description");
		spec.setComparisonType(ComparisonType.CONTAINS);
		spec.setValue("2");
		List<FakeEntity> result = rep.list(spec);
		assertEquals(1, result.size());
		assertEquals("Description 2", result.get(0).getDescription());
	}
	
	public void testOrderedListWithHqlSpecification() {
		ClassHibernateRepository<Long, FakeEntity> rep = new ClassHibernateRepository<Long, FakeEntity>(FakeEntity.class);
		rep.setOrder("description", false);
		HqlStringSpecification<FakeEntity> spec = new HqlStringSpecification<FakeEntity>("description");
		spec.setComparisonType(ComparisonType.STARTS);
		spec.setValue("Description");
		FakeEntity prec = null;
		for (FakeEntity curr: rep.list(spec)) {
			if (prec == null) {
				prec = curr;
			} else {
				assertTrue(prec.getDescription().compareTo(curr.getDescription()) < 0);
			}
		}
	}

	public void testLimitedList() {
		ClassHibernateRepository<Long, FakeEntity> rep = new ClassHibernateRepository<Long, FakeEntity>(FakeEntity.class);
		rep.setFirst(1);
        rep.setLast(2);
        assertEquals(2, rep.list().size());
	}
	
	public void testPut() {
		HibernateSessionManager smgr = new HibernateSessionManager(SessionMode.THREAD_LOCAL);
		Transaction tx = smgr.getSession().beginTransaction();
		ClassHibernateRepository<Long, FakeEntity> rep = new ClassHibernateRepository<Long, FakeEntity>(FakeEntity.class);
		FakeEntity e = new FakeEntity();
		e.setId(10L);
		rep.put(e);
		assertTrue(rep.list().contains(e));
	}
	
	public void testRemoveOrder() {
		ClassHibernateRepository<Long, FakeEntity> rep = new ClassHibernateRepository<Long, FakeEntity>(FakeEntity.class);
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

package it.amattioli.dominate.jpa;

import it.amattioli.dominate.jpa.specifications.JpaqlStringSpecification;
import it.amattioli.dominate.sessions.SessionMode;
import it.amattioli.dominate.specifications.ComparisonType;

import java.util.List;

import javax.persistence.EntityTransaction;

import junit.framework.TestCase;

public class JpaRepositoryTest extends TestCase {
	@Override
	protected void setUp() throws Exception {
		JpaTestInit.init();
	}
	
	public void testGet() {
		JpaRepository<Long, FakeEntity> rep = new JpaRepository<Long, FakeEntity>(FakeEntity.class);
		FakeEntity e = rep.get(1L);
		assertEquals("Description 1", e.getDescription());
	}
	
//	public void testGetNull() {
//		ClassHibernateRepository<Long, FakeEntity> rep = new ClassHibernateRepository<Long, FakeEntity>(FakeEntity.class);
//		assertNull(rep.get(null));
//		HibernateSessionManager.disconnectAll();
//	}
	
	public void testGetByPropertyValue() {
		JpaRepository<Long, FakeEntity> rep = new JpaRepository<Long, FakeEntity>(FakeEntity.class);
		FakeEntity e = rep.getByPropertyValue("description", "Description 1");
		assertEquals("Description 1", e.getDescription());
	}
	
	public void testList() {
		JpaRepository<Long, FakeEntity> rep = new JpaRepository<Long, FakeEntity>(FakeEntity.class);
		List<FakeEntity> result = rep.list();
		assertEquals(4, result.size());
	}
	
	public void testOrderedList() {
		JpaRepository<Long, FakeEntity> rep = new JpaRepository<Long, FakeEntity>(FakeEntity.class);
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
		JpaRepository<Long, FakeEntity> rep = new JpaRepository<Long, FakeEntity>(FakeEntity.class);
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
	
	public void testListWithJpaqlSpecification() {
		JpaRepository<Long, FakeEntity> rep = new JpaRepository<Long, FakeEntity>(FakeEntity.class);
		JpaqlStringSpecification<FakeEntity> spec = new JpaqlStringSpecification<FakeEntity>("description");
		spec.setComparisonType(ComparisonType.CONTAINS);
		spec.setValue("2");
		List<FakeEntity> result = rep.list(spec);
		assertEquals(1, result.size());
		assertEquals("Description 2", result.get(0).getDescription());
	}
	
	public void testOrderedListWithJpaqlSpecification() {
		JpaRepository<Long, FakeEntity> rep = new JpaRepository<Long, FakeEntity>(FakeEntity.class);
		rep.setOrder("description", false);
		JpaqlStringSpecification<FakeEntity> spec = new JpaqlStringSpecification<FakeEntity>("description");
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
		JpaRepository<Long, FakeEntity> rep = new JpaRepository<Long, FakeEntity>(FakeEntity.class);
		rep.setFirst(1);
        rep.setLast(2);
        assertEquals(2, rep.list().size());
	}
	
	public void testPut() {
		JpaSessionManager smgr = new JpaSessionManager(SessionMode.THREAD_LOCAL);
		EntityTransaction tx = smgr.getSession().getTransaction();
		tx.begin();
		JpaRepository<Long, FakeEntity> rep = new JpaRepository<Long, FakeEntity>(FakeEntity.class);
		FakeEntity e = new FakeEntity();
		e.setId(10L);
		rep.put(e);
		assertTrue(rep.list().contains(e));
	}
	
	public void testRemoveOrder() {
		JpaRepository<Long, FakeEntity> rep = new JpaRepository<Long, FakeEntity>(FakeEntity.class);
		rep.setOrder("property1", false);
		rep.addOrder("property2", false);
		rep.removeLastOrder();
		assertEquals(1,rep.getOrders().size());
		assertEquals("property1", rep.getOrders().get(0).getProperty());
		assertEquals("property1", rep.getOrderProperty());
	}
	
	@Override
	protected void tearDown() throws Exception {
		JpaSessionManager.disconnectAll();
		JpaSessionManager.setPersistenceUnitName(null);
	}
}

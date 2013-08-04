package it.amattioli.encapsulate.dates.specifications;

import java.util.Calendar;
import java.util.List;

import it.amattioli.dominate.Repository;
import it.amattioli.dominate.hibernate.ClassHibernateRepository;
import it.amattioli.dominate.hibernate.HibernateSessionManager;
import it.amattioli.encapsulate.dates.Day;
import it.amattioli.encapsulate.dates.GenericTimeInterval;
import it.amattioli.encapsulate.dates.hibernate.FakeDateEntity;
import it.amattioli.encapsulate.dates.hibernate.HibernateTestInit;
import junit.framework.TestCase;

public class HqlDateSpecificationTest extends TestCase {

	@Override
	protected void setUp() throws Exception {
		HibernateTestInit.init();
	}
	
	public void testLowerBound() {
		Repository<Long, FakeDateEntity> rep = new ClassHibernateRepository<Long, FakeDateEntity>(FakeDateEntity.class);
		HqlDateSpecification<FakeDateEntity> spec = new HqlDateSpecification<FakeDateEntity>("date");
		Day lowerBound = new Day(01, Calendar.MARCH, 2010);
		spec.setValue(GenericTimeInterval.lowBoundedInterval(lowerBound.getInitTime()));
		List<FakeDateEntity> result = rep.list(spec);
		assertFalse(result.isEmpty());
		for (FakeDateEntity curr: result) {
			assertTrue(!curr.getDate().before(lowerBound.getInitTime()));
		}
	}
	
	public void testHigherBound() {
		Repository<Long, FakeDateEntity> rep = new ClassHibernateRepository<Long, FakeDateEntity>(FakeDateEntity.class);
		HqlDateSpecification<FakeDateEntity> spec = new HqlDateSpecification<FakeDateEntity>("date");
		Day higherBound = new Day(01, Calendar.MARCH, 2010);
		spec.setValue(GenericTimeInterval.highBoundedInterval(higherBound.getInitTime()));
		List<FakeDateEntity> result = rep.list(spec);
		assertFalse(result.isEmpty());
		for (FakeDateEntity curr: result) {
			assertTrue(curr.getDate().before(higherBound.getInitTime()));
		}
	}
	
	public void testDoubleBound() {
		Repository<Long, FakeDateEntity> rep = new ClassHibernateRepository<Long, FakeDateEntity>(FakeDateEntity.class);
		HqlDateSpecification<FakeDateEntity> spec = new HqlDateSpecification<FakeDateEntity>("date");
		Day lowerBound = new Day(01, Calendar.MARCH, 2010);
		Day higherBound = new Day(01, Calendar.JUNE, 2010);
		spec.setValue(new GenericTimeInterval(lowerBound,higherBound));
		List<FakeDateEntity> result = rep.list(spec);
		assertFalse(result.isEmpty());
		for (FakeDateEntity curr: result) {
			assertTrue(!curr.getDate().before(lowerBound.getInitTime()) && curr.getDate().before(higherBound.getInitTime()));
		}
	}
	
	@Override
	protected void tearDown() throws Exception {
		HibernateSessionManager.disconnectAll();
	}

}

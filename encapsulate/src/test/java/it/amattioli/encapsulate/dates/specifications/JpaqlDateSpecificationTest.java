package it.amattioli.encapsulate.dates.specifications;

import java.util.Calendar;
import java.util.List;

import it.amattioli.dominate.Repository;
import it.amattioli.dominate.jpa.JpaRepository;
import it.amattioli.dominate.jpa.JpaSessionManager;
import it.amattioli.encapsulate.dates.Day;
import it.amattioli.encapsulate.dates.GenericTimeInterval;
import it.amattioli.encapsulate.dates.jpa.FakeJpaDateEntity;
import it.amattioli.encapsulate.dates.jpa.JpaTestInit;
import junit.framework.TestCase;

public class JpaqlDateSpecificationTest extends TestCase {

	@Override
	protected void setUp() throws Exception {
		JpaTestInit.init();
	}

	public void testLowerBound() {
		Repository<Long, FakeJpaDateEntity> rep = new JpaRepository<Long, FakeJpaDateEntity>(FakeJpaDateEntity.class);
		JpaqlDateSpecification<FakeJpaDateEntity> spec = new JpaqlDateSpecification<FakeJpaDateEntity>("date");
		Day lowerBound = new Day(01, Calendar.MARCH, 2010);
		spec.setValue(GenericTimeInterval.lowBoundedInterval(lowerBound.getInitTime()));
		List<FakeJpaDateEntity> result = rep.list(spec);
		assertFalse(result.isEmpty());
		for (FakeJpaDateEntity curr: result) {
			assertTrue(!curr.getDate().before(lowerBound.getInitTime()));
		}
	}
	
	public void testHigherBound() {
		Repository<Long, FakeJpaDateEntity> rep = new JpaRepository<Long, FakeJpaDateEntity>(FakeJpaDateEntity.class);
		JpaqlDateSpecification<FakeJpaDateEntity> spec = new JpaqlDateSpecification<FakeJpaDateEntity>("date");
		Day higherBound = new Day(01, Calendar.MARCH, 2010);
		spec.setValue(GenericTimeInterval.highBoundedInterval(higherBound.getInitTime()));
		List<FakeJpaDateEntity> result = rep.list(spec);
		assertFalse(result.isEmpty());
		for (FakeJpaDateEntity curr: result) {
			assertTrue(curr.getDate().before(higherBound.getInitTime()));
		}
	}
	
	public void testDoubleBound() {
		Repository<Long, FakeJpaDateEntity> rep = new JpaRepository<Long, FakeJpaDateEntity>(FakeJpaDateEntity.class);
		JpaqlDateSpecification<FakeJpaDateEntity> spec = new JpaqlDateSpecification<FakeJpaDateEntity>("date");
		Day lowerBound = new Day(01, Calendar.MARCH, 2010);
		Day higherBound = new Day(01, Calendar.JUNE, 2010);
		spec.setValue(new GenericTimeInterval(lowerBound,higherBound));
		List<FakeJpaDateEntity> result = rep.list(spec);
		assertFalse(result.isEmpty());
		for (FakeJpaDateEntity curr: result) {
			assertTrue(!curr.getDate().before(lowerBound.getInitTime()) && curr.getDate().before(higherBound.getInitTime()));
		}
	}
	
	@Override
	protected void tearDown() throws Exception {
		JpaSessionManager.disconnectAll();
	}

}

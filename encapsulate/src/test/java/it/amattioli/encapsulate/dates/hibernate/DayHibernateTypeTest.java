package it.amattioli.encapsulate.dates.hibernate;

import java.util.Calendar;

import junit.framework.TestCase;
import it.amattioli.dominate.Repository;
import it.amattioli.dominate.hibernate.ClassHibernateRepository;
import it.amattioli.dominate.hibernate.HibernateSessionManager;
import it.amattioli.encapsulate.dates.Day;

public class DayHibernateTypeTest extends TestCase {

	@Override
	protected void setUp() throws Exception {
		HibernateTestInit.init();
	}
	
	public void testLoad() {
		Repository<Long, FakeDateEntity> rep = new ClassHibernateRepository<Long, FakeDateEntity>(FakeDateEntity.class);
		FakeDateEntity e = rep.get(1L);
		assertEquals(new Day(1,Calendar.JANUARY,2010),e.getDay());
	}
	
	@Override
	protected void tearDown() throws Exception {
		HibernateSessionManager.disconnectAll();
	}
	
}

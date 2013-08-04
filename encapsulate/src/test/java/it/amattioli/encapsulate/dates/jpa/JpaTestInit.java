package it.amattioli.encapsulate.dates.jpa;

import java.util.Calendar;

import javax.persistence.EntityTransaction;

import it.amattioli.dominate.jpa.JpaSessionManager;
import it.amattioli.dominate.sessions.SessionMode;
import it.amattioli.encapsulate.dates.Day;
import it.amattioli.encapsulate.dates.Month;

public class JpaTestInit {

	public static void init() {
		JpaSessionManager.setPersistenceUnitName("encapsulate-jpa");
		JpaSessionManager smgr = new JpaSessionManager(SessionMode.THREAD_LOCAL);
		EntityTransaction tx = smgr.getSession().getTransaction();
		tx.begin();
		smgr.getSession().createQuery("delete from FakeDateEntity").executeUpdate();
		FakeJpaDateEntity e1 = new FakeJpaDateEntity();
		e1.setId(1L);
		e1.setDay(new Day(1,Calendar.JANUARY,2010));
		e1.setDate(e1.getDay().getInitTime());
		e1.setInterval(new Month(Calendar.JANUARY, 2010));
		smgr.getSession().persist(e1);
		FakeJpaDateEntity e2 = new FakeJpaDateEntity();
		e2.setId(2L);
		e2.setDay(new Day(1,Calendar.MARCH,2010));
		e2.setDate(e2.getDay().getInitTime());
		smgr.getSession().persist(e2);
		FakeJpaDateEntity e3 = new FakeJpaDateEntity();
		e3.setId(3L);
		e3.setDay(new Day(1,Calendar.MAY,2010));
		e3.setDate(e3.getDay().getInitTime());
		smgr.getSession().persist(e3);
		FakeJpaDateEntity e4 = new FakeJpaDateEntity();
		e4.setId(4L);
		e4.setDay(new Day(1,Calendar.JULY,2010));
		e4.setDate(e4.getDay().getInitTime());
		smgr.getSession().persist(e4);
		tx.commit();
		JpaSessionManager.disconnectAll();
	}

}

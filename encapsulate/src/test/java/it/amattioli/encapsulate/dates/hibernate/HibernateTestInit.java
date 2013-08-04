package it.amattioli.encapsulate.dates.hibernate;

import java.util.Calendar;

import it.amattioli.dominate.hibernate.HibernateSessionManager;
import it.amattioli.dominate.sessions.SessionMode;
import it.amattioli.encapsulate.dates.Day;
import it.amattioli.encapsulate.dates.Month;

import org.hibernate.Transaction;

public class HibernateTestInit {

	public static void init() {
		HibernateSessionManager.setCfgResource("test-hibernate.cfg.xml");
		HibernateSessionManager smgr = new HibernateSessionManager(SessionMode.THREAD_LOCAL);
		Transaction tx = smgr.getSession().beginTransaction();
		smgr.getSession().createQuery("delete from FakeDateEntity").executeUpdate();
		FakeDateEntity e1 = new FakeDateEntity();
		e1.setId(1L);
		e1.setDay(new Day(1,Calendar.JANUARY,2010));
		e1.setDate(e1.getDay().getInitTime());
		e1.setInterval(new Month(Calendar.JANUARY, 2010));
		smgr.getSession().save(e1);
		FakeDateEntity e2 = new FakeDateEntity();
		e2.setId(2L);
		e2.setDay(new Day(1,Calendar.MARCH,2010));
		e2.setDate(e2.getDay().getInitTime());
		smgr.getSession().save(e2);
		FakeDateEntity e3 = new FakeDateEntity();
		e3.setId(3L);
		e3.setDay(new Day(1,Calendar.MAY,2010));
		e3.setDate(e3.getDay().getInitTime());
		smgr.getSession().save(e3);
		FakeDateEntity e4 = new FakeDateEntity();
		e4.setId(4L);
		e4.setDay(new Day(1,Calendar.JULY,2010));
		e4.setDate(e4.getDay().getInitTime());
		smgr.getSession().save(e4);
		tx.commit();
		HibernateSessionManager.disconnectAll();
	}

}

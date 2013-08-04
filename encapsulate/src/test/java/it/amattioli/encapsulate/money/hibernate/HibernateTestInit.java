package it.amattioli.encapsulate.money.hibernate;

import java.util.Currency;

import it.amattioli.dominate.hibernate.HibernateSessionManager;
import it.amattioli.dominate.sessions.SessionMode;
import it.amattioli.encapsulate.money.FakeMoneyEntity;
import it.amattioli.encapsulate.money.Money;

import org.hibernate.Transaction;

public class HibernateTestInit {

	public static void init() {
		HibernateSessionManager.setCfgResource("test-hibernate.cfg.xml");
		HibernateSessionManager smgr = new HibernateSessionManager(SessionMode.THREAD_LOCAL);
		Transaction tx = smgr.getSession().beginTransaction();
		smgr.getSession().createQuery("delete from FakeMoneyEntity").executeUpdate();
		FakeMoneyEntity e1 = new FakeMoneyEntity();
		e1.setId(1L);
		e1.setMoney(new Money(0L,Currency.getInstance("EUR")));
		smgr.getSession().save(e1);
		FakeMoneyEntity e2 = new FakeMoneyEntity();
		e2.setId(2L);
		e2.setMoney(new Money(10L,Currency.getInstance("EUR")));
		smgr.getSession().save(e2);
		FakeMoneyEntity e3 = new FakeMoneyEntity();
		e3.setId(3L);
		e3.setMoney(new Money(100L,Currency.getInstance("EUR")));
		smgr.getSession().save(e3);
		FakeMoneyEntity e4 = new FakeMoneyEntity();
		e4.setId(4L);
		e4.setMoney(new Money(1000L,Currency.getInstance("EUR")));
		smgr.getSession().save(e4);
		tx.commit();
		HibernateSessionManager.disconnectAll();
	}

}

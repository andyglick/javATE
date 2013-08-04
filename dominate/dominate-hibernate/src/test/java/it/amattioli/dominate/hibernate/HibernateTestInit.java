package it.amattioli.dominate.hibernate;

import it.amattioli.dominate.sessions.SessionMode;

import org.hibernate.Transaction;

public class HibernateTestInit {

	public static void init() {
		HibernateSessionManager.setCfgResource("test-hibernate.cfg.xml");
		HibernateSessionManager smgr = new HibernateSessionManager(SessionMode.THREAD_LOCAL);
		Transaction tx = smgr.getSession().beginTransaction();
		smgr.getSession().createQuery("delete from AssociatedEntity").executeUpdate();
		smgr.getSession().createQuery("delete from FakeEntity").executeUpdate();
		FakeEntity e1 = new FakeEntity();
		e1.setId(1L);
		e1.setDescription("Description 1");
		e1.setEnumerated(FakeEnum.VALUE1);
		smgr.getSession().save(e1);
		FakeEntity e2 = new FakeEntity();
		e2.setId(2L);
		e2.setDescription("Description 3");
		smgr.getSession().save(e2);
		FakeEntity e3 = new FakeEntity();
		e3.setId(3L);
		e3.setDescription("Description 2");
		smgr.getSession().save(e3);
		FakeEntity e4 = new FakeEntity();
		e4.setId(4L);
		e4.setDescription("My Description");
		smgr.getSession().save(e4);
		tx.commit();
		HibernateSessionManager.disconnectAll();
	}
	
	public static void initWithAssoc() {
		HibernateSessionManager.setCfgResource("test-hibernate.cfg.xml");
		HibernateSessionManager smgr = new HibernateSessionManager(SessionMode.THREAD_LOCAL);
		Transaction tx = smgr.getSession().beginTransaction();
		smgr.getSession().createQuery("delete from AssociatedEntity").executeUpdate();
		smgr.getSession().createQuery("delete from FakeEntity").executeUpdate();
		FakeEntity e1 = new FakeEntity();
		e1.setId(1L);
		e1.setDescription("Description 1");
		AssociatedEntity a1 = new AssociatedEntity();
		a1.setId(1L);
		a1.setAssocDescription("Assoc 1");
		e1.addAssoc(a1);
		AssociatedEntity a2 = new AssociatedEntity();
		a2.setId(2L);
		a2.setAssocDescription("Assoc 3");
		e1.addAssoc(a2);
		AssociatedEntity a3 = new AssociatedEntity();
		a3.setId(3L);
		a3.setAssocDescription("Assoc 2");
		e1.addAssoc(a3);
		smgr.getSession().save(e1);
		tx.commit();
		HibernateSessionManager.disconnectAll();
	}
	
}

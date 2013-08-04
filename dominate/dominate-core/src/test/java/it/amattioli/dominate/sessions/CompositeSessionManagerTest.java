package it.amattioli.dominate.sessions;

//import it.amattioli.dominate.hibernate.HibernateSessionManager;
//
//import org.hibernate.Session;

import junit.framework.TestCase;

public class CompositeSessionManagerTest extends TestCase {

	@Override
	protected void setUp() throws Exception {
//		HibernateSessionManager.setCfgResource("test-hibernate.cfg.xml");
	}

	public void testGetSession() {
//		CompositeSessionManager mgr = new CompositeSessionManager(SessionMode.THREAD_LOCAL);
//		Session s = mgr.getSession(Session.class);
//		assertNotNull(s);
	}
	
	public void testSessionMode() {
//		CompositeSessionManager mgr = new CompositeSessionManager(SessionMode.THREAD_LOCAL);
//		assertEquals(SessionMode.THREAD_LOCAL, mgr.getSessionMode());
//		assertTrue(mgr.hasSessionMode(SessionMode.THREAD_LOCAL));
	}
	
	public void testAlternativeSessionManager() {
//		CompositeSessionManager mgr = new CompositeSessionManager(SessionMode.THREAD_LOCAL);
//		SessionStub s = mgr.getSession(SessionStub.class);
//		assertNotNull(s);
	}
	
}

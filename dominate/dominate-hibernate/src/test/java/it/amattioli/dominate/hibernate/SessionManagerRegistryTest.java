package it.amattioli.dominate.hibernate;

import it.amattioli.dominate.sessions.SessionMode;
import junit.framework.TestCase;

public class SessionManagerRegistryTest extends TestCase {

	public void testNoPreDefinedSessionManager() {
		SessionManagerRegistry registry = new SessionManagerRegistry();
		HibernateSessionManager currentSessionManager = registry.currentSessionManager();
		assertEquals(SessionMode.THREAD_LOCAL,currentSessionManager.getSessionMode());
	}
	
	public void testPreDefinedSessionManager() {
		SessionManagerRegistry registry = new SessionManagerRegistry();
		HibernateSessionManager sMgr = new HibernateSessionManager(SessionMode.LONG_RUNNING);
		
		registry.useSessionManager(sMgr);
		assertSame(sMgr, registry.currentSessionManager());
		
		registry.releaseSessionManager();
		assertNotSame(sMgr, registry.currentSessionManager());
	}
	
}

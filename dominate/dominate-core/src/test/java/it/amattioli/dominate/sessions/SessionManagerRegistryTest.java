package it.amattioli.dominate.sessions;

import it.amattioli.dominate.sessions.SessionMode;
import junit.framework.TestCase;

public class SessionManagerRegistryTest extends TestCase {

	public void testNoPreDefinedSessionManager() {
		SessionManagerRegistry registry = new SessionManagerRegistry();
		SessionManager currentSessionManager = registry.currentSessionManager();
		assertEquals(SessionMode.THREAD_LOCAL,currentSessionManager.getSessionMode());
	}
	
	public void testPreDefinedSessionManager() {
		SessionManagerRegistry registry = new SessionManagerRegistry();
		SessionManager sMgr = new CompositeSessionManager(SessionMode.LONG_RUNNING);
		
		registry.useSessionManager(sMgr);
		assertSame(sMgr, registry.currentSessionManager());
		
		registry.releaseSessionManager();
		assertNotSame(sMgr, registry.currentSessionManager());
	}
	
}

package it.amattioli.applicate.context;

import static org.mockito.Mockito.*;

import junit.framework.TestCase;
import it.amattioli.dominate.sessions.SessionManager;

public class LongRunningContextTest extends TestCase {
	
	public void testEnhancement() {
		MockService service = new MockService();
		service = LongRunningContext.newLongRunningService(service);
		assertTrue(service instanceof ContextEnhanced);
	}

	public void testSimpleService() {
		MockService service = new MockService();
		SessionManager sMgr = mock(SessionManager.class);
		service = LongRunningContext.newLongRunningService(service, sMgr);
		assertTrue(service.isSessionManager(sMgr));
	}
	
	public void testServiceWithDependency() {
		MockService service = new MockService();
		MockService dependency = new MockService();
		service.setDependency(dependency);
		SessionManager sMgr = mock(SessionManager.class);
		service = LongRunningContext.newLongRunningService(service, sMgr);
		assertTrue(service.isSessionManager(sMgr));
		assertTrue(service.getDependency().isSessionManager(sMgr));
	}
	
	public void testSetDependency() {
		MockService service = new MockService();
		SessionManager sMgr = mock(SessionManager.class);
		service = LongRunningContext.newLongRunningService(service, sMgr);
		assertTrue(service.isSessionManager(sMgr));
		MockService dependency = new MockService();
		service.setDependency(dependency);
		assertTrue(service.getDependency().isSessionManager(sMgr));
	}
	
	public void testSubDependency() {
		MockService service = new MockService();
		MockService dependency = new MockService();
		MockService subDependency = new MockService();
		dependency.setDependency(subDependency);
		service.setDependency(dependency);
		SessionManager sMgr = mock(SessionManager.class);
		service = LongRunningContext.newLongRunningService(service, sMgr);
		assertTrue(service.isSessionManager(sMgr));
		assertTrue(service.getDependency().isSessionManager(sMgr));
		assertTrue(service.getDependency().getDependency().isSessionManager(sMgr));
	}
}

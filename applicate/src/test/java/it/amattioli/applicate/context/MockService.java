package it.amattioli.applicate.context;

import it.amattioli.dominate.sessions.SessionManager;
import it.amattioli.dominate.sessions.SessionManagerRegistry;

import org.apache.commons.lang.ObjectUtils;

public class MockService {
	private MockService dependency;
	
	public boolean isSessionManager(SessionManager sMgr) {
		SessionManagerRegistry registry = new SessionManagerRegistry();
		return ObjectUtils.equals(sMgr, registry.currentSessionManager());
	}

	public MockService getDependency() {
		return dependency;
	}

	@SameContext
	public void setDependency(MockService dependency) {
		this.dependency = dependency;
	}
}

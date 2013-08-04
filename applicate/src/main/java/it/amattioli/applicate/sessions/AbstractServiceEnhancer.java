package it.amattioli.applicate.sessions;

public abstract class AbstractServiceEnhancer implements ServiceEnhancer {
	private ApplicateSession session;
	
	@Override
	public void setSession(ApplicateSession session) {
		this.session = session;
	}
	
	public ApplicateSession getSession() {
		return session;
	}

}

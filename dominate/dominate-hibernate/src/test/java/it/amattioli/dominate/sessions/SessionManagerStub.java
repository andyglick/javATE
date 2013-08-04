package it.amattioli.dominate.sessions;

import it.amattioli.dominate.sessions.SessionManager;
import it.amattioli.dominate.sessions.SessionMode;

import org.apache.commons.collections.Closure;

public class SessionManagerStub implements SessionManager {
	private SessionMode mode;
	
	public SessionManagerStub() {
		
	}
	
	public SessionManagerStub(SessionMode mode) {
		setSessionMode(mode);
	}
	
	@Override
	public void setSessionMode(SessionMode sessionMode) {
		this.mode = sessionMode;
	}

	@Override
	public <S> S getSession(Class<S> sessionClass) {
		if (SessionStub.class.equals(sessionClass)) {
			return (S)new SessionStub();
		}
		return null;
	}

	@Override
	public SessionMode getSessionMode() {
		return mode;
	}

	@Override
	public boolean hasSessionMode(SessionMode sMode) {
		return mode.equals(sMode);
	}

	@Override
	public boolean isReleased() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void release() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void reset() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Closure transactionalClosure(Closure toBeDone) {
		// TODO Auto-generated method stub
		return null;
	}

}

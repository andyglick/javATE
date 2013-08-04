package it.amattioli.dominate.sessions;

import java.util.ServiceLoader;

import org.apache.commons.collections.Closure;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This {@link SessionManager} implementation is able to delegate its behavior
 * to other session managers so sessions of various types can be retrieved.
 * 
 * @author andrea
 *
 */
public class CompositeSessionManager implements SessionManager {
	private static final Logger logger = LoggerFactory.getLogger(CompositeSessionManager.class);
	
	private SessionMode mode;
	private Iterable<SessionManager> managers = ServiceLoader.load(SessionManager.class);
	
	public CompositeSessionManager(SessionMode mode) {
		setSessionMode(mode);
	}
	
	@Override
	public void setSessionMode(SessionMode sessionMode) {
		this.mode = sessionMode;
		for (SessionManager curr: managers) {
			curr.setSessionMode(sessionMode);
		}
	}

	@Override
	public <S> S getSession(Class<S> sessionClass) {
		for (SessionManager manager: managers) {
			S session = manager.getSession(sessionClass);
			if (session != null) {
				return session;
			}
		}
		throw new IllegalArgumentException();
	}

	@Override
	public SessionMode getSessionMode() {
		return mode;
	}

	@Override
	public boolean hasSessionMode(SessionMode sMode) {
		return this.mode.equals(sMode);
	}

	@Override
	public void release() {
		for (SessionManager curr: managers) {
			curr.release();
		}
	}
	
	@Override
	public boolean isReleased() {
		return managers.iterator().next().isReleased();
	}

	@Override
	public void reset() {
		for (SessionManager curr: managers) {
			curr.reset();
		}
	}

	@Override
	public Closure transactionalClosure(Closure toBeDone) {
		Closure result = toBeDone;
		for (SessionManager curr: managers) {
			result = curr.transactionalClosure(result);
		}
		return result;
	}

}

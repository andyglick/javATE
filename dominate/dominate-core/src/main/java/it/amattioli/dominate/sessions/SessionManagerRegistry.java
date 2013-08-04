package it.amattioli.dominate.sessions;

import java.util.Stack;

/**
 * This singleton can be used to manage a stack of {@link SessionManager}
 * for the current thread. This allow us to have a different session for
 * each context of a thread<p>
 * 
 * Every time we enter a context that needs its own session we can call
 * {@link #useSessionManager(SessionManager)} passing the {@link SessionManager}
 * containing the needed session.
 * After this, every time we will call {@link #currentSessionManager()}, this
 * {@link SessionManager} will be returned. To exit the context we have
 * to call {@link #releaseSessionManager()} to return to the previous situation.
 * 
 * If we call {@link #currentSessionManager()} and {@link #useSessionManager(SessionManager)}
 * has never been called, a new {@link SessionManager} will be created
 * with thread local life-cycle.
 * 
 * @author andrea
 *
 */
public class SessionManagerRegistry {
	private static ThreadLocal<Stack<SessionManager>> threadSessionManagers = new ThreadLocal<Stack<SessionManager>>();
	
	private SessionManager newSessionManager() {
		return new CompositeSessionManager(SessionMode.THREAD_LOCAL);
	}
	
	private Stack<SessionManager> managersStack() {
		if (threadSessionManagers.get() == null) {
			threadSessionManagers.set(new Stack<SessionManager>());
		}
		return threadSessionManagers.get();
	}
	
	public boolean containsSessionManager() {
		return managersStack().size() >= 1;
	}
	
	public SessionManager currentSessionManager() {
		if (!containsSessionManager()) {
			managersStack().push(newSessionManager());
		}
		return managersStack().peek();
	}
	
	public void useSessionManager(SessionManager newMgr) {
		managersStack().push(newMgr);
	}
	
	public void releaseSessionManager() {
		if (managersStack().size() >= 1) {
			managersStack().pop();
		}
	}
}

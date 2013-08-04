package it.amattioli.dominate.hibernate;

import it.amattioli.dominate.sessions.SessionMode;

import java.util.Stack;

/**
 * This singleton can be used to manage a stack of {@link HibernateSessionManager}
 * for the current thread. This allow us to have a different Hibernate session for
 * each context of a thread<p>
 * 
 * Every time we enter a context that needs its own Hibernate session we can call
 * {@link #useSessionManager(HibernateSessionManager)} passing the {@link HibernateSessionManager}
 * containing the needed session.
 * After this, every time we will call {@link #currentSessionManager()}, this
 * {@link HibernateSessionManager} will be returned. To exit the context we have
 * to call {@link #releaseSessionManager()} to return to the previous situation.
 * 
 * If we call {@link #currentSessionManager()} and {@link #useSessionManager(HibernateSessionManager)}
 * has never been called, a new {@link HibernateSessionManager} will be created
 * with thread local life-cycle.
 * 
 * @author andrea
 *
 */
@Deprecated
public class SessionManagerRegistry {
	private static ThreadLocal<Stack<HibernateSessionManager>> threadSessionManagers = new ThreadLocal<Stack<HibernateSessionManager>>();

	private HibernateSessionManager newSessionManager() {
		return new HibernateSessionManager(SessionMode.THREAD_LOCAL);
	}
	
	private Stack<HibernateSessionManager> managersStack() {
		if (threadSessionManagers.get() == null) {
			threadSessionManagers.set(new Stack<HibernateSessionManager>());
		}
		return threadSessionManagers.get();
	}
	
	public boolean containsSessionManager() {
		return managersStack().size() >= 1;
	}
	
	public HibernateSessionManager currentSessionManager() {
		if (!containsSessionManager()) {
			managersStack().push(newSessionManager());
		}
		return managersStack().peek();
	}
	
	public void useSessionManager(HibernateSessionManager newMgr) {
		managersStack().push(newMgr);
	}
	
	public void releaseSessionManager() {
		if (managersStack().size() >= 1) {
			managersStack().pop();
		}
	}
}

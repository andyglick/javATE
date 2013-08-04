package it.amattioli.dominate.sessions;

import org.apache.commons.collections.Closure;

/**
 * With a SessionManager instance you a repository is able to retrieve sessions
 * to interact with an external repository system like a RDBMS.<p>
 * 
 * A SessionManager can exist with two different {@link SessionMode}:
 * <ul>
 *   <li>THREAD_LOCAL</li>
 *   <li>LONG_RUNNING</li>
 * </ul>
 * In thread local mode the manager will retain a session for the duration of a
 * single thread. When the thred finishes the session is discarded.<p>
 * In long running mode the session will be alive until the session manager is
 * released using the {@link #release()} or {@link #reset()} method.
 * 
 * @author andrea
 *
 */
public interface SessionManager {

	/**
	 * Retrieves the {@link SessionMode} associated with this SessionManager
	 * 
	 * @return the {@link SessionMode} associated with this SessionManager
	 */
	public SessionMode getSessionMode();
	
	public void setSessionMode(SessionMode sessionMode);
	
	/**
	 * Check if this SessionManager has a certain {@link SessionMode}
	 * 
	 * @param sMode the {@link SessionMode} to check
	 * @return true if this SessionManager has a certain {@link SessionMode}
	 */
	public boolean hasSessionMode(SessionMode sMode);
	
	/**
	 * Retrieves a session associated with this SessionManager.
	 * If this SessionManager has an associated session whose class is the same as the
	 * given class it will be returned, otherwise null will be returned.
	 * 
	 * @param <S>
	 * @param sessionClass the class of the needed session
	 * @return the session associated with this SessionManager whose class is the same as the given
	 *         class if it exists, null otherwise
	 * @throws IllegalStateException if this SessionManager has been released calling the
	 *         {@link #release()} method
	 */
	public <S> S getSession(Class<S> sessionClass);
	
	/**
	 * Release this SessionManager and close all the associated sessions. After this, when the
	 * {@link #getSession(Class)} method is called an {@link IllegalStateException} will be thrown
	 */
	public void release();
	
	/**
	 * Check if this SessionManager has been released.
	 * 
	 * @return true if this SessionManager has been released, false otherwise
	 * 
	 * @see {@link #release()}
	 */
	public boolean isReleased();
	
	/**
	 * Close all the associated sessions. After this, when the {@link #getSession(Class)} method 
	 * is called a new session will be opened.
	 */
	public void reset();

	/**
	 * Decorate a closure so that it will be executed in a transactional context.
	 * A transaction will be open before the closure is executed and the same
	 * transaction will be committed after the execution.
	 * 
	 * @param toBeDone the closure to be decorated
	 * @return the decorated closure
	 */
	public Closure transactionalClosure(Closure toBeDone);
}

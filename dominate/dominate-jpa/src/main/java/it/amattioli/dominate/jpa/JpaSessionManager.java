package it.amattioli.dominate.jpa;

import it.amattioli.dominate.sessions.ConcurrencyException;
import it.amattioli.dominate.sessions.SessionManager;
import it.amattioli.dominate.sessions.SessionMode;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.OptimisticLockException;
import javax.persistence.Persistence;

import org.apache.commons.collections.Closure;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Can be used to retrieve a JPA entity manager with a defined life-cycle.
 * <p>Two different life-cycles are possible: long running and thread local</p>
 * <p>Sessions with a thread local life cycle are created and destroyed in the
 * current thread, wile long-running sessions, at the end of the current thread,
 * disconnect their database connection and can be reconnected in a new thread.</p> 
 * 
 * @author a.mattioli
 *
 */
public class JpaSessionManager implements SessionManager {
	private static final Logger logger = LoggerFactory.getLogger(JpaSessionManager.class);
    private static EntityManagerFactory entityManagerFactory;
    private static String persistenceUnitName = "dominate-jpa";
    private static final ThreadLocal<Map<SessionMode, Collection<EntityManager>>> SESSIONS
        = new ThreadLocal<Map<SessionMode, Collection<EntityManager>>>();

    private EntityManager session;
    private SessionMode sessionMode;
    private boolean released = false;

    /**
     * The name of the persistence unit that will be used for EntityManager configuration.
     * If a previous value was set that will be discarded and the new
     * one will be used to create e new EntityManagerFactory.
     *
     * @param persistenceUnitName the name of the persistence unit that will be used for 
     *                            EntityManager configuration
     */
    public static synchronized void setPersistenceUnitName(final String persistenceUnitName) {
        if (entityManagerFactory != null) {
            entityManagerFactory.close();
            entityManagerFactory = null;
        }
        JpaSessionManager.persistenceUnitName = persistenceUnitName;
    }

	/**
     * Retrieves the EntityManagerFactory that will be used to create
     * new entity managers.
     *
     * @return the EntityManagerFactory
     */
    public static synchronized EntityManagerFactory getEntityManagerFactory() {
        if (entityManagerFactory == null) {
            entityManagerFactory = Persistence.createEntityManagerFactory(persistenceUnitName);
        }
        return entityManagerFactory;
    }

    private static Collection<EntityManager> getSessions(final SessionMode mode) {
        if (SESSIONS.get() == null) {
            SESSIONS.set(new HashMap<SessionMode, Collection<EntityManager>>());
            SESSIONS.get().put(SessionMode.THREAD_LOCAL, new HashSet<EntityManager>());
            SESSIONS.get().put(SessionMode.LONG_RUNNING, new HashSet<EntityManager>());
        }
        return SESSIONS.get().get(mode);
    }

    /**
     * Terminates all the sessions used in the current thread.
     * On thread local sessions the {@link Session#close()} method will be called and
     * they cannot be used again.
     */
    public static void disconnectAll() {
//    	for (EntityManager session : getSessions(SessionMode.LONG_RUNNING)) {
//            if (session.isOpen() && session.isConnected()) {
//                session.disconnect();
//                logger.debug("Disconnected long running session in thread " + Thread.currentThread());
//            } else {
//            	logger.warn("A session was closed or disconnected but not removed");
//            }
//        }
        getSessions(SessionMode.LONG_RUNNING).clear();
        for (EntityManager session : getSessions(SessionMode.THREAD_LOCAL)) {
            if (session.isOpen()) {
                session.close();
                logger.debug("Closed local session in thread " + Thread.currentThread());
            } else {
            	logger.warn("A session was closed but not removed");
            }
        }
        getSessions(SessionMode.THREAD_LOCAL).clear();
    }

    public JpaSessionManager() {
        
    }
    
    /**
     * Creates a new session managers
     *
     * @param sessionMode the life cycle of the managed session
     */
    public JpaSessionManager(SessionMode sessionMode) {
        setSessionMode(sessionMode);
    }
    
    @Override
	public void setSessionMode(SessionMode sessionMode) {
    	if (sessionMode == null) {
            throw new NullPointerException("Cannot create an HibernateSessionManager with a null sessionMode");
        }
        this.sessionMode = sessionMode;
	}

	public SessionMode getSessionMode() {
    	return sessionMode;
    }
    
    public boolean hasSessionMode(SessionMode sMode) {
    	return this.sessionMode.equals(sMode);
    }

    private void registerSession(final EntityManager session) {
    	logger.debug("Registering Session in thread " + Thread.currentThread());
        getSessions(sessionMode).add(session);
    }

    private boolean isRegistered(final EntityManager session) {
        return getSessions(sessionMode).contains(session);
    }

    private void checkReleased() {
        if (released) {
            throw new IllegalStateException("Session Manager has been released");
        }
    }

    @Override
	public boolean isReleased() {
		return released;
	}

	@Override
	public <S> S getSession(Class<S> sessionClass) {
		if (EntityManager.class.equals(sessionClass)) {
			return (S)getSession();
		}
		return null;
	}

	/**
     * Retrieves the session managed by this session manager.
     *
     * @return the managed Hibernate session
     */
    public EntityManager getSession() {
        checkReleased();
        if (session == null || !session.isOpen()) {
            if (sessionMode == SessionMode.LONG_RUNNING) {
                session = getEntityManagerFactory().createEntityManager();
            } else {
                Collection<EntityManager> tlSessions = getSessions(sessionMode);
                if (tlSessions.isEmpty()) {
                    session = getEntityManagerFactory().createEntityManager();
                } else {
                    session = tlSessions.iterator().next();
                    if (!session.isOpen()) {
                        getSessions(sessionMode).remove(session);
                        session = getEntityManagerFactory().createEntityManager();
                    }
                }
            }
        }
        if (!isRegistered(session)) {
            registerSession(session);
        }
        return session;
    }

    /**
     * Informs this manager that it will not be used any more so it can 
     * definitely close the session it manages
     */
    public void release() {
    	logger.debug("Releasing session in thread " + Thread.currentThread());
        checkReleased();
        if (session != null) {
            session.close();
            getSessions(sessionMode).remove(session);
        }
        released = true;
    }
    
    /**
     * Close the current session and prepare to open a new one when the {@link #getSession()}
     * method is called.
     */
    public void reset() {
    	if (!isReleased()) {
    		release();
    	}
    	session = null;
    	released = false;
    }

	@Override
	public Closure transactionalClosure(final Closure toBeDone) {
		return new Closure() {
			
			@Override
			public void execute(Object input) {
				logger.debug("Beginning Transaction");
				EntityManager session = getSession(EntityManager.class);
				EntityTransaction tx = session.getTransaction();
		        try {
		        	tx.begin();
		        	toBeDone.execute(null);
		            logger.debug("Flushing transaction");
		            session.flush();
		            logger.debug("Committing transaction");
		            tx.commit();
		            logger.debug("Transaction committed");
		        } catch (OptimisticLockException e) {
		        	tx.rollback();
		            logger.debug("Transaction Rolled-back",e);
		            throw new ConcurrencyException(e);
		        } catch (RuntimeException e) {
		            tx.rollback();
		            logger.debug("Transaction Rolled-back");
		            throw e;
		        }
			}
		};
		
	}

}

package it.amattioli.dominate.hibernate;

import it.amattioli.dominate.sessions.ConcurrencyException;
import it.amattioli.dominate.sessions.ConnectionException;
import it.amattioli.dominate.sessions.SessionManager;
import it.amattioli.dominate.sessions.SessionMode;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import org.apache.commons.collections.Closure;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.StaleObjectStateException;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.exception.JDBCConnectionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Can be used to retrieve a Hibernate session with a defined life-cycle.
 * <p>Two different life-cycles are possible: long running and thread local</p>
 * <p>Sessions with a thread local life cycle are created and destroyed in the
 * current thread, wile long-running sessions, at the end of the current thread,
 * disconnect their database connection and can be reconnected in a new thread.</p> 
 * 
 * @author a.mattioli
 *
 */
public class HibernateSessionManager implements SessionManager {
	private static final Logger logger = LoggerFactory.getLogger(HibernateSessionManager.class);
    private static SessionFactory sessionFactory;
    private static Configuration cfg;
    private static String cfgResource;
    private static final ThreadLocal<Map<SessionMode, Collection<Session>>> SESSIONS
        = new ThreadLocal<Map<SessionMode, Collection<Session>>>();

    private Session session;
    private SessionMode sessionMode;
    private boolean released = false;

    /**
     * The Configuration object that will be used for Hibernate configuration.
     * If a previous configuration was set that will be discarded and the new
     * one will be used to create e new SessionFactory.
     *
     * @param newCfg the Configuration object that will be used for Hibernate 
     *               configuration
     */
    public static synchronized void setConfiguration(final Configuration newCfg) {
        if (sessionFactory != null) {
            sessionFactory.close();
            sessionFactory = null;
        }
        HibernateSessionManager.cfg = newCfg;
    }

    /**
     * Retrieves the Configuration object that will be used for Hibernate 
     * configuration. If a Configuration object was set using 
     * {@link #setConfiguration(Configuration)} that object will be returned,
     * otherwise a new default Configuration object will be created.
     *
     * @return the Configuration object that will be used for Hibernate configuration
     */
    public static synchronized Configuration getConfiguration() {
        if (cfg == null) {
        	if (cfgResource == null) {
        		cfg = new Configuration().configure();
        	} else {
        		cfg = new Configuration().configure(cfgResource);
        	}
        }
        return cfg;
    }

	public static String getCfgResource() {
		return cfgResource;
	}

	/**
	 * Set the name of the resource xml file from which the Hibernate configuration
	 * should be read.
	 * If a different resource is set any previously created configuration will be
	 * discarded and the new one will be used to create a new SessionFactory.
	 * 
	 * @param cfgResource
	 */
	public static void setCfgResource(String cfgResource) {
		if (!StringUtils.equals(HibernateSessionManager.cfgResource, cfgResource)) {
			HibernateSessionManager.cfgResource = cfgResource;
			if (sessionFactory != null) {
				sessionFactory.close();
				sessionFactory = null;
			}
			cfg = null;
		}
	}

	/**
     * Retrieves the Hibernate SessionFactory that will be used to create
     * new sessions.
     *
     * @return the Hibernate SessionFactory
     */
    public static synchronized SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            sessionFactory = getConfiguration().buildSessionFactory();
        }
        return sessionFactory;
    }

    private static Collection<Session> getSessions(final SessionMode mode) {
        if (SESSIONS.get() == null) {
            SESSIONS.set(new HashMap<SessionMode, Collection<Session>>());
            SESSIONS.get().put(SessionMode.THREAD_LOCAL, new HashSet<Session>());
            SESSIONS.get().put(SessionMode.LONG_RUNNING, new HashSet<Session>());
        }
        return SESSIONS.get().get(mode);
    }

    /**
     * Terminates all the sessions used in the current thread.
     * On long running sessions the {@link Session#disconnect()} method
     * will be called, so they can be re-opened, while on thread local
     * sessions the {@link Session#close()} method will be called and
     * they cannot be used again.
     */
    public static void disconnectAll() {
    	for (Session session : getSessions(SessionMode.LONG_RUNNING)) {
            if (session.isOpen() && session.isConnected()) {
                session.disconnect();
                logger.debug("Disconnected long running session in thread " + Thread.currentThread());
            } else {
            	logger.warn("A session was closed or disconnected but not removed");
            }
        }
        getSessions(SessionMode.LONG_RUNNING).clear();
        for (Session session : getSessions(SessionMode.THREAD_LOCAL)) {
            if (session.isOpen()) {
                session.close();
                logger.debug("Closed local session in thread " + Thread.currentThread());
            } else {
            	logger.warn("A session was closed but not removed");
            }
        }
        getSessions(SessionMode.THREAD_LOCAL).clear();
    }

    public HibernateSessionManager() {
        
    }
    
    /**
     * Creates a new session managers
     *
     * @param sessionMode the life cycle of the managed session
     */
    public HibernateSessionManager(SessionMode sessionMode) {
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

    private void registerSession(final Session session) {
    	logger.debug("Registering Session in thread " + Thread.currentThread());
        getSessions(sessionMode).add(session);
    }

    private boolean isRegistered(final Session session) {
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
		if (Session.class.equals(sessionClass)) {
			return (S)getSession();
		}
		return null;
	}

	/**
     * Retrieves the session managed by this session manager.
     *
     * @return the managed Hibernate session
     */
    public Session getSession() {
        checkReleased();
        if (session == null || !session.isOpen()) {
            if (sessionMode == SessionMode.LONG_RUNNING) {
                session = getSessionFactory().openSession();
            } else {
                Collection<Session> tlSessions = getSessions(sessionMode);
                if (tlSessions.isEmpty()) {
                    session = getSessionFactory().openSession();
                } else {
                    session = tlSessions.iterator().next();
                    if (!session.isOpen()) {
                        getSessions(sessionMode).remove(session);
                        session = getSessionFactory().openSession();
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
		        Session session = getSession(Session.class);
				Transaction tx = session.beginTransaction();
		        try {
		        	toBeDone.execute(null);
		            logger.debug("Flushing transaction");
		            session.flush();
		            logger.debug("Committing transaction");
		            tx.commit();
		            logger.debug("Transaction committed");
		        } catch (StaleObjectStateException e) {
		        	tx.rollback();
		            logger.debug("Transaction Rolled-back",e);
		            throw new ConcurrencyException(e);
		        } catch (JDBCConnectionException e) {
		        	logger.debug("Database connection problems, transaction cannot be committed nor rolled back");
		            throw new ConnectionException(e);
		        } catch (RuntimeException e) {
		            tx.rollback();
		            logger.debug("Transaction Rolled-back");
		            throw e;
		        }
			}
		};
		
	}

}

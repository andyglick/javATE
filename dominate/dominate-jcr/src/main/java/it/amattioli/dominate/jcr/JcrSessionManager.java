package it.amattioli.dominate.jcr;

import it.amattioli.dominate.sessions.SessionManager;
import it.amattioli.dominate.sessions.SessionMode;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import javax.jcr.Credentials;
import javax.jcr.Repository;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.jcr.SimpleCredentials;

import org.apache.commons.collections.Closure;
import org.apache.jackrabbit.ocm.manager.ObjectContentManager;
import org.apache.jackrabbit.ocm.manager.impl.ObjectContentManagerImpl;
import org.apache.jackrabbit.ocm.mapper.Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JcrSessionManager implements SessionManager {
	private static final Logger logger = LoggerFactory.getLogger(JcrSessionManager.class);
	private static Repository jcrRepository;
	private static Credentials jcrCredentials;
	private static Mapper mapper;
	private static final ThreadLocal<Map<SessionMode, Collection<ObjectContentManager>>> SESSIONS
    	= new ThreadLocal<Map<SessionMode, Collection<ObjectContentManager>>>();
	
	private ObjectContentManager session;
    private SessionMode sessionMode;
    private boolean released = false;
    
    public static Repository getJcrRepository() {
    	return jcrRepository;
    }
    
    public static void setJcrRepository(Repository jcrRepository) {
    	JcrSessionManager.jcrRepository = jcrRepository; 
    }
    
    public static Credentials getJcrCredentials() {
    	if (jcrCredentials == null) {
    		jcrCredentials = new SimpleCredentials("username", "password".toCharArray());
    	}
    	return jcrCredentials;
    }
    
    public static void setCredentials(Credentials jcrCredentials) {
    	JcrSessionManager.jcrCredentials = jcrCredentials;
    }
    
    public static Mapper getMapper() {
		return mapper;
	}

	public static void setMapper(Mapper mapper) {
		JcrSessionManager.mapper = mapper;
	}

	private static Collection<ObjectContentManager> getSessions(final SessionMode mode) {
        if (SESSIONS.get() == null) {
            SESSIONS.set(new HashMap<SessionMode, Collection<ObjectContentManager>>());
            SESSIONS.get().put(SessionMode.THREAD_LOCAL, new HashSet<ObjectContentManager>());
            SESSIONS.get().put(SessionMode.LONG_RUNNING, new HashSet<ObjectContentManager>());
        }
        return SESSIONS.get().get(mode);
    }
	
	public JcrSessionManager() {
		
	}
    
	/**
     * Creates a new session manager
     *
     * @param sessionMode the life cycle of the managed session
     */
    public JcrSessionManager(SessionMode sessionMode) {
        setSessionMode(sessionMode);
    }
    
    @Override
	public void setSessionMode(SessionMode sessionMode) {
    	if (sessionMode == null) {
            throw new NullPointerException("Cannot create a JcrSessionManager with a null sessionMode");
        }
        this.sessionMode = sessionMode;
	}

	public SessionMode getSessionMode() {
    	return sessionMode;
    }
    
    public boolean hasSessionMode(SessionMode sMode) {
    	return this.sessionMode.equals(sMode);
    }

    private void registerSession(final ObjectContentManager session) {
    	logger.debug("Registering Jcr Session in thread " + Thread.currentThread());
        getSessions(sessionMode).add(session);
    }

    private boolean isRegistered(final ObjectContentManager session) {
        return getSessions(sessionMode).contains(session);
    }

    private void checkReleased() {
        if (released) {
            throw new IllegalStateException("Jcr Session Manager has been released");
        }
    }

    @Override
	public boolean isReleased() {
		return released;
	}

	@Override
	public <S> S getSession(Class<S> sessionClass) {
		if (ObjectContentManager.class.equals(sessionClass)) {
			return (S)getSession();
		}
		return null;
	}

	/**
     * Retrieves the session managed by this session manager.
     *
     * @return the managed Hibernate session
     */
    public ObjectContentManager getSession() {
        checkReleased();
        if (session == null || !session.getSession().isLive()) {
            if (sessionMode == SessionMode.LONG_RUNNING) {
                session = login();
            } else {
                Collection<ObjectContentManager> tlSessions = getSessions(sessionMode);
                if (tlSessions.isEmpty()) {
                    session = login();
                } else {
                    session = tlSessions.iterator().next();
                    if (!session.getSession().isLive()) {
                        getSessions(sessionMode).remove(session);
                        session = login();
                    }
                }
            }
        }
        if (!isRegistered(session)) {
            registerSession(session);
        }
        return session;
    }
    
    private ObjectContentManager login() {
    	try {
    		Session session = getJcrRepository().login(getJcrCredentials());
    		return new ObjectContentManagerImpl(session, mapper);
    	} catch(RepositoryException e) {
    		throw new RuntimeException(e);
    	}
    }

    /**
     * Informs this manager that it will not be used any more so it can 
     * definitely close the session it manages
     */
    public void release() {
    	logger.debug("Releasing session in thread " + Thread.currentThread());
        checkReleased();
        if (session != null) {
            session.logout();
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
				logger.debug("Starting Jcr Unit of Work");
				toBeDone.execute(input);
				logger.debug("Before Saving Jcr Unit of Work");
				getSession().save();
				logger.debug("Saved Jcr Unit of Work");
			}
			
		};
	}
}

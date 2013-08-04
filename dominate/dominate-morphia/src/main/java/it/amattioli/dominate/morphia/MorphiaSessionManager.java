package it.amattioli.dominate.morphia;

import java.net.UnknownHostException;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import org.apache.commons.collections.Closure;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.code.morphia.Datastore;
import com.google.code.morphia.Morphia;
import com.mongodb.Mongo;

import it.amattioli.dominate.sessions.SessionManager;
import it.amattioli.dominate.sessions.SessionMode;

public class MorphiaSessionManager implements SessionManager {
	private static final Logger logger = LoggerFactory.getLogger(MorphiaSessionManager.class);
    private static final ThreadLocal<Map<SessionMode, Collection<Datastore>>> SESSIONS
        = new ThreadLocal<Map<SessionMode, Collection<Datastore>>>();

    private static Morphia morphia = new Morphia();
    private static Mongo mongo;
    private static String database;
    
    private Datastore session;
    private SessionMode sessionMode;
    private boolean released = false;

    private static Collection<Datastore> getSessions(final SessionMode mode) {
        if (SESSIONS.get() == null) {
            SESSIONS.set(new HashMap<SessionMode, Collection<Datastore>>());
            SESSIONS.get().put(SessionMode.THREAD_LOCAL, new HashSet<Datastore>());
            SESSIONS.get().put(SessionMode.LONG_RUNNING, new HashSet<Datastore>());
        }
        return SESSIONS.get().get(mode);
    }
    
    public static Morphia getMorphia() {
    	return morphia;
    }
    
    public static void setMorphia(Morphia morphia) {
    	MorphiaSessionManager.morphia = morphia;
    }
    
    public static Mongo getMongo() {
    	return mongo;
    }
    
    public static void setMongo(Mongo mongo) {
    	MorphiaSessionManager.mongo = mongo;
    }

    public static String getDatabase() {
		return database;
	}

	public static void setDatabase(String database) {
		MorphiaSessionManager.database = database;
	}

	/**
     * Terminates all the sessions used in the current thread.
     * On long running sessions the {@link Session#disconnect()} method
     * will be called, so they can be re-opened, while on thread local
     * sessions the {@link Session#close()} method will be called and
     * they cannot be used again.
     */
    public static void disconnectAll() {
    	// Nothing to do
    }

    private static synchronized Datastore openSession() {
    	if (mongo == null) {
    		try {
    			mongo = new Mongo();
    		} catch(UnknownHostException e) {
    			throw new RuntimeException(e);
    		}
    	}
    	return morphia.createDatastore(mongo, database);
    }
    
    public MorphiaSessionManager() {
    	
    }
    
    /**
     * Creates a new session managers
     *
     * @param sessionMode the life cycle of the managed session
     */
    public MorphiaSessionManager(final SessionMode sessionMode) {
        setSessionMode(sessionMode);
    }
    
    public void setSessionMode(SessionMode sessionMode) {
    	this.sessionMode = sessionMode;
    }
    
    public SessionMode getSessionMode() {
    	return sessionMode;
    }
    
    public boolean hasSessionMode(SessionMode sMode) {
    	return this.sessionMode.equals(sMode);
    }

    private void registerSession(final Datastore session) {
    	logger.debug("Registering Session in thread " + Thread.currentThread());
        getSessions(sessionMode).add(session);
    }

    private boolean isRegistered(final Datastore session) {
        return getSessions(sessionMode).contains(session);
    }

    private void checkReleased() {
        if (released) {
            throw new IllegalStateException("Session Manager has been released");
        }
    }

	public boolean isReleased() {
		return released;
	}

	public <S> S getSession(Class<S> sessionClass) {
		if (Datastore.class.equals(sessionClass)) {
			return (S)getSession();
		}
		return null;
	}

	/**
     * Retrieves the session managed by this session manager.
     *
     * @return the managed Hibernate session
     */
    public Datastore getSession() {
        checkReleased();
        if (session == null) {
            if (sessionMode == SessionMode.LONG_RUNNING) {
                session = openSession();
            } else {
                Collection<Datastore> tlSessions = getSessions(sessionMode);
                if (tlSessions.isEmpty()) {
                    session = openSession();
                } else {
                    session = tlSessions.iterator().next();
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
	public Closure transactionalClosure(Closure toBeDone) {
		return toBeDone;
	}
}

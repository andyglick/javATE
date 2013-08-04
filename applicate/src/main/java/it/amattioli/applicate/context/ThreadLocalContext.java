package it.amattioli.applicate.context;

import it.amattioli.applicate.util.ApplicateServiceException;
import it.amattioli.dominate.sessions.CompositeSessionManager;
import it.amattioli.dominate.sessions.SessionManager;
import it.amattioli.dominate.sessions.SessionManagerRegistry;
import it.amattioli.dominate.sessions.SessionMode;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ThreadLocalContext implements MethodInterceptor {
	private static final Logger logger = LoggerFactory.getLogger(ThreadLocalContext.class);
	private Object decorated;
	private SessionManager sessionMgr;
	private SessionManagerRegistry smRegistry = new SessionManagerRegistry();

	public static <T> T newThreadLocalService(T command) {
		Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(command.getClass());
        enhancer.setCallback(new ThreadLocalContext(command));
        return (T)enhancer.create();
	}

	public ThreadLocalContext(Object decorated) {
    	this.decorated = decorated;
    	this.sessionMgr = new CompositeSessionManager(SessionMode.THREAD_LOCAL);
    }

    protected Object getDecorated() {
    	return decorated;
    }

    protected SessionManager getSessionManager() {
    	return sessionMgr;
    }

    protected void beforeMethod() {
    	//logger.debug("Injecting session manager");
    	smRegistry.useSessionManager(this.sessionMgr);
    }

    protected void afterMethod() {
    	smRegistry.releaseSessionManager();
    	//logger.debug("Session manager released");
    }

    protected Object perform(Object object, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
    	return method.invoke(decorated, args);
    }

	public final Object intercept(Object object, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
		beforeMethod();
		Object result;
		try {
			result = perform(object, method, args, methodProxy);
		} catch(InvocationTargetException e) {
			throw new ApplicateServiceException(object, e.getCause());
		} finally {
			afterMethod();
		}
		return result;
	}

}

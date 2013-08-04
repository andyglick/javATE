package it.amattioli.applicate.context;

import it.amattioli.applicate.util.ApplicateServiceException;
import it.amattioli.dominate.sessions.CompositeSessionManager;
import it.amattioli.dominate.sessions.SessionManager;
import it.amattioli.dominate.sessions.SessionManagerRegistry;
import it.amattioli.dominate.sessions.SessionMode;

import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LongRunningContext implements MethodInterceptor {
	private static final Logger logger = LoggerFactory.getLogger(LongRunningContext.class);
	private Object decorated;
	private SessionManager sessionMgr;
	private SessionManagerRegistry smRegistry = new SessionManagerRegistry();

	public static <T> T newLongRunningService(T service, SessionManager sessionMgr) {
		return newLongRunningService(service, new LongRunningContext(service, sessionMgr));
	}

	public static <T> T newLongRunningService(T service) {
		return newLongRunningService(service, new LongRunningContext(service));
	}
	
	protected static <T> T newLongRunningService(T service, LongRunningContext callback) {
		Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(service.getClass());
        enhancer.setInterfaces(new Class[] {ContextEnhanced.class});
        enhancer.setCallback(callback);
        T result = (T)enhancer.create();
        setDependenciesContextOn(service, result);
		return result;
	}
	
	public static <T> T withCurrentSessionManager(T service) {
		SessionManagerRegistry reg = new SessionManagerRegistry();
		return newLongRunningService(service, reg.currentSessionManager());
	}
	
	protected static void setDependenciesContextOn(Object original, Object enhanced) {
		for (PropertyDescriptor curr: PropertyUtils.getPropertyDescriptors(original)) {
			Method writeMethod = curr.getWriteMethod();
			if (writeMethod != null && writeMethod.getAnnotation(SameContext.class) != null) {
				try {
					Object dependency = PropertyUtils.getProperty(original, curr.getName());
					if (dependency != null) {
						BeanUtils.setProperty(enhanced, curr.getName(), dependency);
					}
				} catch (IllegalAccessException e) {
					throw new RuntimeException(e);
				} catch (InvocationTargetException e) {
					throw new RuntimeException(e.getCause());
				} catch (NoSuchMethodException e) {
					throw new RuntimeException(e);
				}
			}
		}
	}

	public LongRunningContext(Object decorated, SessionManager sessionMgr) {
        this.decorated = decorated;
        this.sessionMgr = sessionMgr;
    }

    public LongRunningContext(Object decorated) {
    	this.decorated = decorated;
    	this.sessionMgr = new CompositeSessionManager(SessionMode.LONG_RUNNING);
    }

    protected Object getDecorated() {
    	return decorated;
    }

    public SessionManager getSessionManager() {
    	return sessionMgr;
    }

    protected void beforeMethod(Object object, Method method, Object[] args, MethodProxy methodProxy) {
    	if (method.getAnnotation(SameContext.class) != null && args.length > 0 && args[0] != null) {
    		args[0] = newLongRunningService(args[0], getSessionManager());
    	}
    	//logger.debug("Injecting session manager");
    	smRegistry.useSessionManager(this.sessionMgr);
    }

    protected void afterMethod(Object object, Method method, Object[] args, MethodProxy methodProxy) {
    	smRegistry.releaseSessionManager();
    	if ("release".equals(method.getName()) && this.sessionMgr != null) {
    		this.sessionMgr.release();
    		logger.debug("Session manager released");
    	}
    }

    protected Object perform(Object object, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
    	method.setAccessible(true);
    	return method.invoke(decorated, args);
    }

	public final Object intercept(Object object, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
		beforeMethod(object, method, args, methodProxy);
		Object result = null;
		try {
			result = perform(object, method, args, methodProxy);
		} catch(InvocationTargetException e) {
			throw new ApplicateServiceException(object, e.getCause());
		} finally {
			afterMethod(object, method, args, methodProxy);
		}
		return result;
	}

}

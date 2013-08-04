package it.amattioli.applicate.serviceFactory;

import it.amattioli.applicate.sessions.ServiceCreationException;
import it.amattioli.applicate.sessions.UnknownServiceException;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.apache.commons.lang.StringUtils;

public abstract class AbstractServiceFactory implements ServiceFactory {

    @Override
    public Object createService(String serviceName) {
        try {
            Method factoryMethod = factoryMethod(serviceName);
            if (factoryMethod == null) {
            	throw new UnknownServiceException(serviceName);
            }
			return factoryMethod.invoke(this);
        } catch (IllegalAccessException e) {
            // TODO handle exception
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new ServiceCreationException(e.getCause());
        }
    }

    @Override
    public boolean knowsService(String serviceName) {
        return factoryMethod(serviceName) != null;
    }

    private Method factoryMethod(String serviceName) {
        String methodName = "create" + StringUtils.capitalize(serviceName);
        Method factoryMethod;
        try {
            factoryMethod = getClass().getMethod(methodName);
        } catch (SecurityException e) {
            // TODO handle exception
            throw new RuntimeException(e);
        } catch (NoSuchMethodException e) {
            factoryMethod = null;
        }
        return factoryMethod;
    }

}

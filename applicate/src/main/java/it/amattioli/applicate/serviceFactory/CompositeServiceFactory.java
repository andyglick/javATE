package it.amattioli.applicate.serviceFactory;

import it.amattioli.applicate.sessions.UnknownServiceException;

import java.util.Collection;
import java.util.HashSet;

/**
 * A service factory that delegates service creation to other factories.
 * This factory contains a collection of other {@link ServiceFactory} objects.
 * When asked for a new service it will call every service factory it contains
 * until it find one that is able to create an object with the given name and
 * returns the result.
 * If no service factory is able to create such a service a null will be returned.
 * 
 * @author andrea
 *
 */
public class CompositeServiceFactory  implements ServiceFactory {
    private Collection<ServiceFactory> factories = new HashSet<ServiceFactory>();

    private ServiceFactory findFactory(String serviceName) {
        for (ServiceFactory curr : factories) {
            if (curr.knowsService(serviceName)) {
                return curr;
            }
        }
        return null;
    }

    @Override
    public Object createService(String serviceName) {
        ServiceFactory factory = findFactory(serviceName);
        if (factory == null) {
        	throw new UnknownServiceException(serviceName);
        }
        return factory.createService(serviceName);
    }

    @Override
    public boolean knowsService(String serviceName) {
        return findFactory(serviceName) != null;
    }

    public void addFactory(ServiceFactory factory) {
    	factories.add(factory);
    }
    
    public void setFactories(Collection<ServiceFactory> factories) {
    	this.factories.clear();
    	this.factories.addAll(factories);
    }

}

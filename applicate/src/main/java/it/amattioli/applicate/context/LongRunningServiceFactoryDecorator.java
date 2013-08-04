package it.amattioli.applicate.context;

import it.amattioli.applicate.serviceFactory.ServiceFactory;

/**
 * A decorator for {@link ServiceFactory} objects that uses {@link LongRunningContext} to
 * add a long runnning context to the services created by the decorated factory.
 *  
 * @author andrea
 *
 */
public class LongRunningServiceFactoryDecorator implements ServiceFactory {
	private ServiceFactory decorated;
	
	public LongRunningServiceFactoryDecorator(ServiceFactory decorated) {
		this.decorated = decorated;
	}
	
	@Override
	public Object createService(String serviceName) {
		Object service = decorated.createService(serviceName);
		return LongRunningContext.newLongRunningService(service);
	}

	@Override
	public boolean knowsService(String serviceName) {
		return decorated.knowsService(serviceName);
	}

}

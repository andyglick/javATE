package it.amattioli.applicate.context;

import it.amattioli.applicate.commands.Command;
import it.amattioli.applicate.serviceFactory.ServiceFactory;

/**
 * A decorator for {@link ServiceFactory} objects that uses {@link TransactionalCommandContext}
 * to add transactional support to services created by the decorated factory.
 * 
 * The services created by the decorated factory must implement the {@link Command} interface.
 * 
 * @author andrea
 *
 */
public class TransactionalServiceFactoryDecorator implements ServiceFactory {
	private ServiceFactory decorated;
	
	public TransactionalServiceFactoryDecorator(ServiceFactory decorated) {
		this.decorated = decorated;
	}
	
	@Override
	public Object createService(String serviceName) {
		Command service = (Command)decorated.createService(serviceName);
		return TransactionalCommandContext.newTransaction(service);
	}

	@Override
	public boolean knowsService(String serviceName) {
		return decorated.knowsService(serviceName);
	}

}

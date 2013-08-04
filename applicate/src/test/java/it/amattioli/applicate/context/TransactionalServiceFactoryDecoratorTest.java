package it.amattioli.applicate.context;

import org.mockito.Mockito;

import it.amattioli.applicate.commands.Command;
import it.amattioli.applicate.serviceFactory.ServiceFactory;
import junit.framework.TestCase;

public class TransactionalServiceFactoryDecoratorTest extends TestCase {
	
	public void testKnowsService() {
		ServiceFactory factory = Mockito.mock(ServiceFactory.class);
		Mockito.when(factory.knowsService("myService")).thenReturn(true);
		factory = new TransactionalServiceFactoryDecorator(factory);
		assertTrue(factory.knowsService("myService"));
	}

	public void testServiceCreation() {
		ServiceFactory factory = Mockito.mock(ServiceFactory.class);
		Command cmd = new CommandStub();
		Mockito.when(factory.createService("myService")).thenReturn(cmd);
		factory = new TransactionalServiceFactoryDecorator(factory);
		Object service = factory.createService("myService");
		assertTrue(service instanceof ContextEnhanced);
	}
	
}

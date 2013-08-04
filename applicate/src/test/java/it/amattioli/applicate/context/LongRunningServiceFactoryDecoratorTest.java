package it.amattioli.applicate.context;

import org.mockito.Mockito;

import it.amattioli.applicate.serviceFactory.ServiceFactory;
import junit.framework.TestCase;

public class LongRunningServiceFactoryDecoratorTest extends TestCase {
	
	public void testKnowsService() {
		ServiceFactory factory = Mockito.mock(ServiceFactory.class);
		Mockito.when(factory.knowsService("myService")).thenReturn(true);
		factory = new LongRunningServiceFactoryDecorator(factory);
		assertTrue(factory.knowsService("myService"));
	}

	public void testServiceCreation() {
		ServiceFactory factory = Mockito.mock(ServiceFactory.class);
		Mockito.when(factory.createService("myService")).thenReturn(new MockService());
		factory = new LongRunningServiceFactoryDecorator(factory);
		Object service = factory.createService("myService");
		assertTrue(service instanceof ContextEnhanced);
	}
	
}

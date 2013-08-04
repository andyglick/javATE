package it.amattioli.applicate.serviceFactory;

import java.util.ArrayList;
import java.util.Collection;

import it.amattioli.applicate.serviceFactory.CompositeServiceFactory;
import it.amattioli.applicate.sessions.UnknownServiceException;
import junit.framework.TestCase;

public class CompositeServiceFactoryTest extends TestCase {
	
	public void testAddFactory() {
		CompositeServiceFactory sf = new CompositeServiceFactory();
		sf.addFactory(new ServiceFactoryStub());
		assertTrue(sf.knowsService("service1"));
	}
	
	public void testSetFactories() {
		CompositeServiceFactory sf = new CompositeServiceFactory();
		Collection<ServiceFactory> factories = new ArrayList<ServiceFactory>();
		factories.add(new ServiceFactoryStub());
		factories.add(new ServiceFactoryStub2());
		sf.setFactories(factories);
		assertTrue(sf.knowsService("service1"));
		assertTrue(sf.knowsService("service2"));
	}
	
	public void testServiceCreation1() {
		CompositeServiceFactory sf = new CompositeServiceFactory();
		sf.addFactory(new ServiceFactoryStub());
		sf.addFactory(new ServiceFactoryStub2());
		assertNotNull(sf.createService("service1"));
	}
	
	public void testServiceCreation2() {
		CompositeServiceFactory sf = new CompositeServiceFactory();
		sf.addFactory(new ServiceFactoryStub());
		sf.addFactory(new ServiceFactoryStub2());
		assertNotNull(sf.createService("service2"));
	}
	
	public void testCreateUnknownService() {
		CompositeServiceFactory sf = new CompositeServiceFactory();
		sf.addFactory(new ServiceFactoryStub());
		sf.addFactory(new ServiceFactoryStub2());
		try {
			Object service = sf.createService("service11");
			fail();
		} catch(UnknownServiceException e) {
			assertEquals("Unknown service service11", e.getMessage());
		}
	}
	
}

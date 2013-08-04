package it.amattioli.applicate.serviceFactory;

import it.amattioli.applicate.serviceFactory.ServiceFactory;
import it.amattioli.applicate.sessions.ServiceCreationException;
import it.amattioli.applicate.sessions.UnknownServiceException;
import junit.framework.TestCase;

public class ServiceFactoryTest extends TestCase {

	public void testKnowsService() {
		ServiceFactory sf = new ServiceFactoryStub();
		assertTrue(sf.knowsService("service1"));
	}
	
	public void testKnowsServiceFail() {
		ServiceFactory sf = new ServiceFactoryStub();
		assertFalse(sf.knowsService("service11"));
	}
	
	public void testCreateService() {
		ServiceFactory sf = new ServiceFactoryStub();
		Object service = sf.createService("service1");
		assertNotNull(service);
	}
	
	public void testCreateUnknownService() {
		ServiceFactory sf = new ServiceFactoryStub();
		try {
			Object service = sf.createService("service11");
			fail();
		} catch(UnknownServiceException e) {
			assertEquals("Unknown service service11", e.getMessage());
		}
	}

	public void testCreateProtectedService() {
		ServiceFactory sf = new ServiceFactoryStub();
		try {
			Object service = sf.createService("protectedService");
			fail();
		} catch(UnknownServiceException e) {
			assertEquals("Unknown service protectedService", e.getMessage());
		}
	}
	
	public void testCreatePrivateService() {
		ServiceFactory sf = new ServiceFactoryStub();
		try {
			Object service = sf.createService("privateService");
			fail();
		} catch(UnknownServiceException e) {
			assertEquals("Unknown service privateService", e.getMessage());
		}
	}
	
	public void testCreateServiceWithError() {
		ServiceFactory sf = new ServiceFactoryStub();
		try {
			Object service = sf.createService("serviceWithError");
			fail();
		} catch(ServiceCreationException e) {
			assertEquals("Exception during service creation: exceptional factory method", e.getMessage());
		}
	}
}

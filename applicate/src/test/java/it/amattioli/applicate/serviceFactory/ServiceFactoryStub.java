package it.amattioli.applicate.serviceFactory;

import it.amattioli.applicate.commands.MockCommandListener;
import it.amattioli.applicate.serviceFactory.AbstractServiceFactory;
import it.amattioli.applicate.sessions.CommandStub;

public class ServiceFactoryStub extends AbstractServiceFactory {

	public Object createService1() {
		return new Object();
	}
	
	protected Object createProtectedService() {
		return new Object();
	}
	
	private Object createPrivateService() {
		return new Object();
	}
	
	public Object createServiceWithError() {
		throw new RuntimeException("exceptional factory method");
	}
	
	public Object createMockCommandListener() {
		return new MockCommandListener();
	}
	
	public Object createCommandStub() {
		return new CommandStub();
	}
}

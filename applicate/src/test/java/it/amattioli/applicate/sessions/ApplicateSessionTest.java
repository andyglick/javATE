package it.amattioli.applicate.sessions;

import it.amattioli.applicate.commands.Command;
import it.amattioli.applicate.commands.MockCommandListener;
import it.amattioli.applicate.serviceFactory.ServiceFactoryStub;
import junit.framework.TestCase;

public class ApplicateSessionTest extends TestCase {

	public void testCreateService() {
		ApplicateSession session = new ApplicateSession();
		session.setServiceFactory(new ServiceFactoryStub());
		assertNotNull(session.createService("service1"));
	}
	
	public void testCommandListener() {
		ApplicateSession session = new ApplicateSession();
		session.setServiceFactory(new ServiceFactoryStub());
		Command cmd = (Command)session.createService("commandStub");
		MockCommandListener lst = (MockCommandListener)session.createService("mockCommandListener");
		cmd.doCommand();
		assertTrue(lst.isCommandDoneReceived());
	}
	
	public void testExecuteCommand() {
		ApplicateSession session = new ApplicateSession();
		MockCommandExecutor cmdExecutor = new MockCommandExecutor();
		session.setCommandExecutor(cmdExecutor);
		session.setServiceFactory(new ServiceFactoryStub());
		Command cmd = (Command)session.createService("commandStub");
		session.execute(cmd);
		assertTrue(cmdExecutor.executed(cmd));
	}
	
}

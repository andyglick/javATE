package it.amattioli.applicate.context;

import static org.mockito.Mockito.*;

import org.apache.commons.collections.Closure;

import it.amattioli.applicate.commands.Command;
import it.amattioli.applicate.commands.CommandEvent;
import it.amattioli.applicate.commands.CommandListener;
import it.amattioli.applicate.commands.CommandResult;
import it.amattioli.dominate.sessions.ConcurrencyException;
import it.amattioli.dominate.sessions.SessionManager;
import junit.framework.TestCase;

public class TransactionalCommandContextTest extends TestCase {
	
	public void testEnhancement() {
		Command service = new CommandStub();
		service = TransactionalCommandContext.newTransaction(service);
		assertTrue(service instanceof ContextEnhanced);
	}

	public void testCorrectTransaction() {
		SessionManager sMgr = mock(SessionManager.class);
		Closure transaction = mock(Closure.class);
		when(sMgr.transactionalClosure(any(Closure.class))).thenReturn(transaction);
		Command cmd = new CommandStub();
		cmd = TransactionalCommandContext.newTransaction(cmd, sMgr);
		cmd.doCommand();
		verify(transaction).execute(null);
	}
	
	public void testExceptionalTransaction() {
		Closure transaction = mock(Closure.class);
		doThrow(new RuntimeException()).when(transaction).execute(null);
		SessionManager sMgr = mock(SessionManager.class);
		when(sMgr.transactionalClosure(any(Closure.class))).thenReturn(transaction);
		Command cmd = new CommandStub();
		cmd = TransactionalCommandContext.newTransaction(cmd, sMgr);
		try {
			cmd.doCommand();
			fail("Should throw exception");
		} catch(Exception e) {
			
		}
	}
	
	public void testConcurrencyException() {
		Closure transaction = mock(Closure.class);
		final String ENTITY_NAME = "myEntity";
		doThrow(new ConcurrencyException(ENTITY_NAME,null)).when(transaction).execute(null);
		SessionManager sMgr = mock(SessionManager.class);
		when(sMgr.transactionalClosure(any(Closure.class))).thenReturn(transaction);
		Command cmd = new CommandStub();
		cmd = TransactionalCommandContext.newTransaction(cmd, sMgr);
		try {
			cmd.doCommand();
			fail("Should throw exception");
		} catch(it.amattioli.applicate.commands.ConcurrencyException e) {
			assertEquals("Concurrent modification of myEntity",e.getMessage());
		}
	}
	
	public void testSuccessfulEvent() {
		SessionManager sMgr = mock(SessionManager.class);
		Closure transaction = mock(Closure.class);
		when(sMgr.transactionalClosure(any(Closure.class))).thenReturn(transaction);
		Command originalCmd = new CommandStub();
		Command cmd = TransactionalCommandContext.newTransaction(originalCmd, sMgr);
		CommandListener listener = mock(CommandListener.class);
		cmd.addCommandListener(listener);
		cmd.doCommand();
		verify(listener).commandDone(new CommandEvent(originalCmd, CommandResult.SUCCESSFUL));
	}
	
	public void testUnSuccessfulEvent() {
		Closure transaction = mock(Closure.class);
		doThrow(new RuntimeException()).when(transaction).execute(null);
		SessionManager sMgr = mock(SessionManager.class);
		when(sMgr.transactionalClosure(any(Closure.class))).thenReturn(transaction);
		Command originalCmd = new CommandStub();
		Command cmd = TransactionalCommandContext.newTransaction(originalCmd, sMgr);
		CommandListener listener = mock(CommandListener.class);
		cmd.addCommandListener(listener);
		try {
			cmd.doCommand();
		} catch(Exception e) {
			verify(listener).commandDone(new CommandEvent(originalCmd, CommandResult.UNSUCCESSFUL));
		}
	}
	
}

package it.amattioli.dominate.hibernate;

import it.amattioli.dominate.sessions.ConcurrencyException;
import it.amattioli.dominate.sessions.SessionMode;

import org.apache.commons.collections.Closure;
import org.hibernate.StaleObjectStateException;
import org.hibernate.Transaction;
import static org.mockito.Mockito.*;

import junit.framework.TestCase;

public class HibernateTransactionTest extends TestCase {

	@Override
	protected void setUp() throws Exception {
		HibernateTestInit.init();
	}
	
	public void testCorrectTransaction() {
		HibernateSessionManager smgr = new HibernateSessionManager(SessionMode.THREAD_LOCAL);
		Transaction jdbcTransaction = smgr.getSession().getTransaction();
		Closure toBeDone = mock(Closure.class);
		
		Closure transaction = smgr.transactionalClosure(toBeDone);
		transaction.execute(null);
		
		assertTrue(jdbcTransaction.wasCommitted());
		verify(toBeDone).execute(null);
	}
	
	public void testExceptionalTransaction() {
		HibernateSessionManager smgr = new HibernateSessionManager(SessionMode.THREAD_LOCAL);
		Transaction jdbcTransaction = smgr.getSession().getTransaction();
		Closure transaction = smgr.transactionalClosure(new Closure() {
			
			@Override
			public void execute(Object input) {
				throw new RuntimeException();
			}
		});
		try {
			transaction.execute(null);
			fail("Should throw exception");
		} catch(Exception e) {
			assertTrue(jdbcTransaction.wasRolledBack());
		}
	}
	
	public void testConcurrentExceptionalTransaction() {
		HibernateSessionManager smgr = new HibernateSessionManager(SessionMode.THREAD_LOCAL);
		Transaction jdbcTransaction = smgr.getSession().getTransaction();
		Closure transaction = smgr.transactionalClosure(new Closure() {
			
			@Override
			public void execute(Object input) {
				throw new StaleObjectStateException("it.amattioli.dominate.hibernate.FakeEntity", 1L);
			}
		});
		try {
			transaction.execute(null);
			fail("Should throw exception");
		} catch(ConcurrencyException e) {
			assertTrue(jdbcTransaction.wasRolledBack());
		}
	}
	
	@Override
	protected void tearDown() throws Exception {
		HibernateSessionManager.disconnectAll();
		HibernateSessionManager.setCfgResource(null);
	}
}

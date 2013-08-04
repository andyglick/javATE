package it.amattioli.applicate.commands;

import junit.framework.TestCase;

public class ConcurrencyExceptionTest extends TestCase {

	public void testMessage() {
		ConcurrencyException e = new ConcurrencyException("myEntity");
		assertEquals("Concurrent modification of myEntity",e.getMessage());
	}
	
}

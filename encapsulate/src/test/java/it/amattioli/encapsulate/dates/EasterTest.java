package it.amattioli.encapsulate.dates;

import java.util.Calendar;

import junit.framework.TestCase;

public class EasterTest extends TestCase {

	public void testEaster() {
		Easter e = new Easter();
		assertTrue(e.includes(new Day(23,Calendar.MARCH,2008)));
	}
	
}

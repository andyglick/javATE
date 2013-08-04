package it.amattioli.encapsulate.dates;

import java.util.Calendar;

import junit.framework.TestCase;

public class DayIteratorTest extends TestCase {

	public void testNext() {
		Day start = new Day(1, Calendar.JANUARY, 2008);
		Day end = new Day(10, Calendar.JANUARY, 2008);
		DayIterator i = new DayIterator(start, end);
		assertEquals(start, i.next());
		assertEquals(start.next(), i.next());
	}
	
	public void testHasNext() {
		Day start = new Day(1, Calendar.JANUARY, 2008);
		Day end = new Day(2, Calendar.JANUARY, 2008);
		DayIterator i = new DayIterator(start, end);
		assertTrue(i.hasNext());
		i.next();
		i.next();
		assertFalse(i.hasNext());
	}
	
}

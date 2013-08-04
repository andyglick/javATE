package it.amattioli.encapsulate.dates;

import java.util.Calendar;
import java.util.GregorianCalendar;

import junit.framework.TestCase;

public class YearTest extends TestCase {

	public void testNewYear() {
		Year y = new Year(2004);
		assertEquals(2004, y.getYear());
	}

	public void testNewYearFromDate() {
		Calendar cal = new GregorianCalendar(2004, Calendar.MAY, 5);
		Year y = new Year(cal.getTime());
		assertEquals(2004, y.getYear());
	}

}

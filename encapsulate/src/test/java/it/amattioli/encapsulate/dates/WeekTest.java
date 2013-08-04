package it.amattioli.encapsulate.dates;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

import junit.framework.TestCase;

/**
 * @author a.mattioli
 * 
 * Creato il Jan 28, 2005 alle 9:44:39 PM
 */
public class WeekTest extends TestCase {
	private Locale defaultLocale;

	@Override
	public void setUp() {
		defaultLocale = Locale.getDefault();
		Locale.setDefault(Locale.ITALY);
	}

	@Override
	public void tearDown() {
		Locale.setDefault(defaultLocale);
	}

	public void testNewWeek() {
		Calendar cal = new GregorianCalendar(2008, Calendar.AUGUST, 1);
		Date d = cal.getTime();
		Week w = new Week(d);
		assertEquals(Calendar.JULY, w.getMonth());
		assertEquals(2008, w.getYear());
	}

	public void testNextMonth() {
		Calendar cal = new GregorianCalendar(2008, Calendar.AUGUST, 1);
		Date d = cal.getTime();
		Week w = new Week(d);

		Calendar ncal = new GregorianCalendar(2008, Calendar.AUGUST, 7);
		Date nd = ncal.getTime();
		Week nw = new Week(nd);
		assertEquals(nw, w.next());
	}

	public void testPreviousMonth() {
		Calendar cal = new GregorianCalendar(2008, Calendar.AUGUST, 1);
		Date d = cal.getTime();
		Week w = new Week(d);

		Calendar pcal = new GregorianCalendar(2008, Calendar.JULY, 24);
		Date pd = pcal.getTime();
		Week pw = new Week(pd);
		assertEquals(pw, w.previous());
	}

	public void testClone() {
		Calendar cal = new GregorianCalendar(2008, Calendar.AUGUST, 1);
		Date d = cal.getTime();
		Week w = new Week(d);
		assertEquals(w, w.clone());
	}

	public void testEquals() {
		Calendar cal1 = new GregorianCalendar(2008, Calendar.AUGUST, 1);
		Date d1 = cal1.getTime();
		Week w1 = new Week(d1);

		Calendar cal2 = new GregorianCalendar(2008, Calendar.JULY, 24);
		Date d2 = cal2.getTime();
		Week w2 = new Week(d2);
		assertTrue(w1.equals(w1));
		assertFalse(w1.equals(w2));
	}

	public void testCompare() {
		Calendar cal1 = new GregorianCalendar(2008, Calendar.AUGUST, 1);
		Date d1 = cal1.getTime();
		Week w1 = new Week(d1);

		Calendar cal2 = new GregorianCalendar(2008, Calendar.JULY, 24);
		Date d2 = cal2.getTime();
		Week w2 = new Week(d2);

		assertEquals(1, w1.compareTo(w2));
		assertEquals(-1, w2.compareTo(w1));
		assertEquals(0, w1.compareTo(w1));
	}

	public void testBefore() {
		Calendar cal1 = new GregorianCalendar(2008, Calendar.AUGUST, 1);
		Date d1 = cal1.getTime();
		Week w1 = new Week(d1);

		Calendar cal2 = new GregorianCalendar(2008, Calendar.JULY, 24);
		Date d2 = cal2.getTime();
		Week w2 = new Week(d2);

		assertTrue(w2.before(w1));
		assertFalse(w1.before(w2));
	}

	public void testAfter() {
		Calendar cal1 = new GregorianCalendar(2008, Calendar.AUGUST, 1);
		Date d1 = cal1.getTime();
		Week w1 = new Week(d1);

		Calendar cal2 = new GregorianCalendar(2008, Calendar.JULY, 24);
		Date d2 = cal2.getTime();
		Week w2 = new Week(d2);
		assertTrue(w1.after(w2));
		assertFalse(w2.after(w1));
	}

	public void testStartDay() {
		Calendar cal1 = new GregorianCalendar(2008, Calendar.AUGUST, 1);
		Date d1 = cal1.getTime();
		Week w1 = new Week(d1);
		assertEquals(new Day(28, Calendar.JULY, 2008), w1.getStartDay());
	}

	public void testEndDay() {
		Calendar cal1 = new GregorianCalendar(2008, Calendar.AUGUST, 1);
		Date d1 = cal1.getTime();
		Week w1 = new Week(d1);
		assertEquals(new Day(3, Calendar.AUGUST, 2008), w1.getEndDay());
	}

}

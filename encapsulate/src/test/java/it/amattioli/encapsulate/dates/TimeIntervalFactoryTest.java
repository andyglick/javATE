package it.amattioli.encapsulate.dates;

import java.util.Calendar;
import java.util.Date;

import junit.framework.TestCase;

public class TimeIntervalFactoryTest extends TestCase {

	public void testYear() {
		Year y = new Year(2009);
		TimeInterval i = new TimeIntervalFactory().createTimeInterval(y.getLow(), y.getHigh());
		assertTrue(i instanceof Year);
		assertEquals(y, i);
	}
	
	public void testMonth() {
		Month m = new Month(Calendar.APRIL, 2009);
		TimeInterval i = new TimeIntervalFactory().createTimeInterval(m.getLow(), m.getHigh());
		assertTrue(i instanceof Month);
		assertEquals(i, m);
	}
	
	public void testDecember() {
		Month m = new Month(Calendar.DECEMBER, 2009);
		TimeInterval i = new TimeIntervalFactory().createTimeInterval(m.getLow(), m.getHigh());
		assertTrue(i instanceof Month);
		assertEquals(i, m);
	}
	
	public void testDay() {
		Day d = new Day(4, Calendar.APRIL, 2009);
		TimeInterval i = new TimeIntervalFactory().createTimeInterval(d.getLow(), d.getHigh());
		assertTrue(i instanceof Day);
		assertEquals(i, d);
	}
	
	public void testLastDayOfMonth() {
		Day d = new Day(30, Calendar.APRIL, 2009);
		TimeInterval i = new TimeIntervalFactory().createTimeInterval(d.getLow(), d.getHigh());
		assertTrue(i instanceof Day);
		assertEquals(i, d);
	}
	
	public void testLastDayOfYear() {
		Day d = new Day(31, Calendar.DECEMBER, 2008);
		TimeInterval i = new TimeIntervalFactory().createTimeInterval(d.getLow(), d.getHigh());
		assertTrue(i instanceof Day);
		assertEquals(i, d);
	}
	
	public void testHour() {
		Date d = PhysicalDuration.ONE_HOUR.after(new Day(4, Calendar.APRIL, 2009).getInitTime());
		Hour h = new Hour(d);
		TimeInterval i = new TimeIntervalFactory().createTimeInterval(h.getLow(), h.getHigh());
		assertTrue(i instanceof Hour);
		assertEquals(i, h);
	}
	
	public void testGeneric() {
		Day d1 = new Day(1, Calendar.APRIL, 2009);
		Day d2 = new Day(4, Calendar.APRIL, 2009);
		TimeInterval i = new TimeIntervalFactory().createTimeInterval(d1.getLow(), d2.getHigh());
		assertTrue(i instanceof GenericTimeInterval);
		assertEquals(d1.getLow(), i.getLow());
		assertEquals(d2.getHigh(), i.getHigh());
	}
	
	public void testNoLowBound() {
		Day d1 = new Day(1, Calendar.APRIL, 2009);
		TimeInterval i = new TimeIntervalFactory().createTimeInterval(null, d1.getHigh());
		assertTrue(i instanceof GenericTimeInterval);
		assertFalse(i.isLowBounded());
		assertEquals(d1.getHigh(), i.getHigh());
	}
	
	public void testNoHighBound() {
		Day d1 = new Day(1, Calendar.APRIL, 2009);
		TimeInterval i = new TimeIntervalFactory().createTimeInterval(d1.getLow(), null);
		assertTrue(i instanceof GenericTimeInterval);
		assertFalse(i.isHighBounded());
		assertEquals(d1.getLow(), i.getLow());
	}
}

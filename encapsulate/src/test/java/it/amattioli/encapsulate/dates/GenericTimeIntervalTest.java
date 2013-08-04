package it.amattioli.encapsulate.dates;

import java.util.Calendar;

import junit.framework.TestCase;

public class GenericTimeIntervalTest extends TestCase {

	public void testGenericTimeInterval() throws Exception {
		Day startDay = new Day(01, Calendar.JANUARY, 2008);
		Day endDay = new Day(15, Calendar.APRIL, 2009);
		GenericTimeInterval i = new GenericTimeInterval(startDay, endDay);
		assertEquals(startDay, i.getLowDay());
		assertEquals(endDay, i.getHighDay());
	}
	
	public void testMonthInterval() {
		Month startMonth = new Month(Calendar.JANUARY, 2008);
		Month endMonth = new Month(Calendar.APRIL, 2009);
		GenericTimeInterval i = new GenericTimeInterval(startMonth, endMonth);
		assertEquals(startMonth.getLowDay(), i.getLowDay());
		assertEquals(endMonth.getHighDay(), i.getHighDay());
	}
	
	public void testLowBoundedTimeInterval() throws Exception {
		Day startDay = new Day(01, Calendar.JANUARY, 2008);
		TimeInterval i = GenericTimeInterval.lowBoundedInterval(startDay.getInitTime());
		assertTrue(i.isLowBounded());
		assertFalse(i.isHighBounded());
		assertEquals(startDay, i.getLowDay());
	}
	
	public void testLowBoundedMonthInterval() throws Exception {
		Month startMonth = new Month(Calendar.JANUARY, 2008);
		TimeInterval i = GenericTimeInterval.lowBoundedInterval(startMonth);
		assertTrue(i.isLowBounded());
		assertFalse(i.isHighBounded());
		assertEquals(startMonth.getLowDay(), i.getLowDay());
	}
	
	public void testHighBoundedTimeInterval() throws Exception {
		Day endDay = new Day(15, Calendar.APRIL, 2009);
		TimeInterval i = GenericTimeInterval.highBoundedInterval(endDay.getEndTime());
		assertFalse(i.isLowBounded());
		assertTrue(i.isHighBounded());
		assertEquals(endDay, i.getHighDay());
	}
	
	public void testHighBoundedMonthInterval() throws Exception {
		Month endMonth = new Month(Calendar.APRIL, 2009);
		TimeInterval i = GenericTimeInterval.highBoundedInterval(endMonth);
		assertFalse(i.isLowBounded());
		assertTrue(i.isHighBounded());
		assertEquals(endMonth.getHighDay(), i.getHighDay());
	}
	
	public void testDuration() throws Exception {
		Day startDay = new Day(1, Calendar.APRIL, 2010);
		Day endDay = new Day(25, Calendar.MAY, 2010);
		GenericTimeInterval i = new GenericTimeInterval(startDay, endDay);
		assertEquals(PhysicalDuration.fromDays(55), i.getDuration());
	}
	
}

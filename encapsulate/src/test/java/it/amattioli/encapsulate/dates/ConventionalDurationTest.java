package it.amattioli.encapsulate.dates;

import java.util.Calendar;
import java.util.Date;

import junit.framework.TestCase;

public class ConventionalDurationTest extends TestCase {

	public void testFromDays() {
		Day start = new Day(5,Calendar.MAY,2010);
		Day end = new Day(15,Calendar.JUNE,2010);
		ConventionalDuration d = new ConventionalDuration(start,end);
		assertEquals(end.getInitTime(), d.after(start.getInitTime()));
	}
	
	public void testFromDaysWithDaylightSaving() {
		Day start = new Day(5,Calendar.FEBRUARY,2010);
		Day end = new Day(15,Calendar.AUGUST,2010);
		ConventionalDuration d = new ConventionalDuration(start,end);
		assertEquals(end.getInitTime(), d.after(start.getInitTime()));
	}
	
	public void testFromDaysDifferentYears() {
		Day start = new Day(5,Calendar.APRIL,1966);
		Day end = new Day(15,Calendar.JANUARY,2010);
		ConventionalDuration d = new ConventionalDuration(start,end);
		assertEquals(end.getInitTime(), d.after(start.getInitTime()));
	}
	
	public void testFromMonthsSameMonth() {
		Month start = new Month(Calendar.MAY, 2010);
		Month end = new Month(Calendar.MAY, 2010);
		ConventionalDuration d = new ConventionalDuration(start,end);
		assertEquals(end.getInitTime(), d.after(start.getInitTime()));
	}
	
	public void testFromMonthsSameYear() {
		Month start = new Month(Calendar.MARCH, 2010);
		Month end = new Month(Calendar.JUNE, 2010);
		ConventionalDuration d = new ConventionalDuration(start,end);
		assertEquals(end.getInitTime(), d.after(start.getInitTime()));
	}
	
	public void testFromMonthsDifferentYear() {
		Month start = new Month(Calendar.APRIL, 2007);
		Month end = new Month(Calendar.OCTOBER, 2010);
		ConventionalDuration d = new ConventionalDuration(start,end);
		assertEquals(end.getInitTime(), d.after(start.getInitTime()));
	}
	
	public void testFromYearsSameYear() {
		Year start = new Year(2010);
		Year end = new Year(2010);
		ConventionalDuration d = new ConventionalDuration(start,end);
		assertEquals(end.getInitTime(), d.after(start.getInitTime()));
	}
	
	public void testFromYearsDifferentYear() {
		Year start = new Year(2007);
		Year end = new Year(2010);
		ConventionalDuration d = new ConventionalDuration(start,end);
		assertEquals(end.getInitTime(), d.after(start.getInitTime()));
	}
	
	public void testPlus() {
		ConventionalDuration d1 = ConventionalDuration.ONE_MONTH;
		ConventionalDuration d2 = ConventionalDuration.ONE_DAY;
		Day start = new Day(1, Calendar.FEBRUARY, 2010);
		Date expected = new Day(2, Calendar.MARCH, 2010).getInitTime();
		Date actual = d1.plus(d2).after(start.getInitTime());
		assertEquals(expected, actual);
	}
	
}

package it.amattioli.encapsulate.dates.format;

import static java.util.Calendar.*;

import java.text.ParseException;
import java.util.Locale;

import it.amattioli.encapsulate.dates.Day;
import it.amattioli.encapsulate.dates.GenericTimeInterval;
import it.amattioli.encapsulate.dates.Month;
import it.amattioli.encapsulate.dates.TimeInterval;
import it.amattioli.encapsulate.dates.Year;
import junit.framework.TestCase;

public class TimeIntervalFormatTest extends TestCase {
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

	public void testParseDay() throws Exception {
		TimeIntervalFormat fmt = new TimeIntervalFormat();
		TimeInterval result = fmt.parse("15/03/2010");
		assertEquals(new Day(15,MARCH,2010), result);
	}
	
	public void testParseAlphaDay() throws Exception {
		TimeIntervalFormat fmt = new TimeIntervalFormat();
		TimeInterval result = fmt.parse("15 Mar 2010");
		assertEquals(new Day(15,MARCH,2010), result);
	}
	
	public void testParseMonth() throws Exception {
		TimeIntervalFormat fmt = new TimeIntervalFormat();
		TimeInterval result = fmt.parse("03/2010");
		assertEquals(new Month(MARCH,2010), result);
	}
	
	public void testParseAlphaMonth() throws Exception {
		TimeIntervalFormat fmt = new TimeIntervalFormat();
		TimeInterval result = fmt.parse("Marzo 2010");
		assertEquals(new Month(MARCH,2010), result);
	}
	
	public void testParseYear() throws Exception {
		TimeIntervalFormat fmt = new TimeIntervalFormat();
		TimeInterval result = fmt.parse("2010");
		assertEquals(new Year(2010), result);
	}
	
	public void testParseDayInterval() throws Exception {
		TimeIntervalFormat fmt = new TimeIntervalFormat();
		TimeInterval result = fmt.parse("05/03/2010 -07/10/2010");
		Day startDay = new Day(5,MARCH,2010);
		Day endDay = new Day(7,OCTOBER,2010);
		TimeInterval expected = new GenericTimeInterval(startDay,endDay);
		assertEquals(expected, result);
	}
	
	public void testParseDayIntervalSameMonth() throws Exception {
		TimeIntervalFormat fmt = new TimeIntervalFormat();
		TimeInterval result = fmt.parse("05-07/10/2010");
		Day startDay = new Day(5,OCTOBER,2010);
		Day endDay = new Day(7,OCTOBER,2010);
		TimeInterval expected = new GenericTimeInterval(startDay,endDay);
		assertEquals(expected, result);
	}
	
	public void testParseWrongDayIntervalSameMonth() throws Exception {
		TimeIntervalFormat fmt = new TimeIntervalFormat();
		try {
			TimeInterval result = fmt.parse("35-07/10/2010");
			fail("Should throw ParseException");
		} catch(ParseException e) {
			// OK
		}
	}
	
	public void testParseMonthInterval() throws Exception {
		TimeIntervalFormat fmt = new TimeIntervalFormat();
		TimeInterval result = fmt.parse("04/2010 - 11/2010");
		Month startMonth = new Month(APRIL,2010);
		Month endMonth = new Month(NOVEMBER,2010);
		TimeInterval expected = new GenericTimeInterval(startMonth,endMonth);
		assertEquals(expected, result);
	}
	
	public void testParseMonthIntervalSameYear() throws Exception {
		TimeIntervalFormat fmt = new TimeIntervalFormat();
		TimeInterval result = fmt.parse("04 - 11/2010");
		Month startMonth = new Month(APRIL,2010);
		Month endMonth = new Month(NOVEMBER,2010);
		TimeInterval expected = new GenericTimeInterval(startMonth,endMonth);
		assertEquals(expected, result);
	}
	
	public void testParseWrongMonthIntervalSameYear() throws Exception {
		TimeIntervalFormat fmt = new TimeIntervalFormat();
		try {
			TimeInterval result = fmt.parse("15-10/2010");
			fail("Should throw ParseException");
		} catch(ParseException e) {
			// OK
		}
	}
	
	public void testParseAlphaMonthInterval1() throws Exception {
		TimeIntervalFormat fmt = new TimeIntervalFormat();
		TimeInterval result = fmt.parse("Apr - Nov 2010");
		Month startMonth = new Month(APRIL,2010);
		Month endMonth = new Month(NOVEMBER,2010);
		TimeInterval expected = new GenericTimeInterval(startMonth,endMonth);
		assertEquals(expected, result);
	}
	
	public void testParseAlphaMonthInterval2() throws Exception {
		TimeIntervalFormat fmt = new TimeIntervalFormat();
		TimeInterval result = fmt.parse("Apr - Nov");
		Month startMonth = new Month(APRIL, Year.thisYear().getYear());
		Month endMonth = new Month(NOVEMBER, Year.thisYear().getYear());
		TimeInterval expected = new GenericTimeInterval(startMonth,endMonth);
		assertEquals(expected, result);
	}
	
	public void testParseYearInterval() throws Exception {
		TimeIntervalFormat fmt = new TimeIntervalFormat();
		TimeInterval result = fmt.parse("2007-2010");
		Year startYear = new Year(2007);
		Year endYear = new Year(2010);
		TimeInterval expected = new GenericTimeInterval(startYear,endYear);
		assertEquals(expected, result);
	}
	
	public void testParseYearIntervalWithDots() throws Exception {
		TimeIntervalFormat fmt = new TimeIntervalFormat();
		TimeInterval result = fmt.parse("2007...2010");
		Year startYear = new Year(2007);
		Year endYear = new Year(2010);
		TimeInterval expected = new GenericTimeInterval(startYear,endYear);
		assertEquals(expected, result);
	}
	
	public void testParseLowOpenYearInterval() throws Exception {
		TimeIntervalFormat fmt = new TimeIntervalFormat();
		TimeInterval result = fmt.parse("...2010");
		Year endYear = new Year(2010);
		TimeInterval expected = new GenericTimeInterval(null,endYear.getHigh());
		assertEquals(expected, result);
	}
	
	public void testParseHighOpenYearInterval() throws Exception {
		TimeIntervalFormat fmt = new TimeIntervalFormat();
		TimeInterval result = fmt.parse("2010...");
		Year startYear = new Year(2010);
		TimeInterval expected = new GenericTimeInterval(startYear.getLow(),null);
		assertEquals(expected, result);
	}
	
	public void testParseAlphaDayInterval() throws Exception {
		TimeIntervalFormat fmt = new TimeIntervalFormat();
		TimeInterval result = fmt.parse("ieri...domani");
		TimeInterval expected = new GenericTimeInterval(Day.yesterday(),Day.tomorrow());
		assertEquals(expected, result);
	}
	
	public void testParseWrongOrderInterval() throws Exception {
		TimeIntervalFormat fmt = new TimeIntervalFormat();
		try {
			TimeInterval result = fmt.parse("2017-2010");
			fail("Should throw ParseException");
		} catch(ParseException e) {
			// OK
		}
	}
	
	public void testUnparseableString() {
		TimeIntervalFormat fmt = new TimeIntervalFormat();
		try {
			fmt.parse("Unknown");
			fail("Should throw exception");
		} catch(ParseException e) {
			// OK
		}
	}
	
	public void testFormatYear() {
		TimeIntervalFormat fmt = new TimeIntervalFormat();
		Year toBeFormatted = new Year(2010);
		String result = fmt.format(toBeFormatted);
		assertEquals("2010",result);
	}
	
	public void testFormatMonth() {
		TimeIntervalFormat fmt = new TimeIntervalFormat();
		Month toBeFormatted = new Month(JULY,2010);
		String result = fmt.format(toBeFormatted);
		assertEquals("07/2010",result);
	}
	
	public void testFormatDay() {
		TimeIntervalFormat fmt = new TimeIntervalFormat();
		Day toBeFormatted = new Day(8,JULY,2010);
		String result = fmt.format(toBeFormatted);
		assertEquals("08/07/2010",result);
	}
	
	public void testFormatInterval() {
		TimeIntervalFormat fmt = new TimeIntervalFormat();
		Day startDay = new Day(8,JULY,2010);
		Day endDay = new Day(23,SEPTEMBER,2010);
		TimeInterval toBeFormatted = new GenericTimeInterval(startDay, endDay);
		String result = fmt.format(toBeFormatted);
		assertEquals("08/07/2010 - 23/09/2010",result);
	}
	
	public void testFormatLowBoundedInterval() {
		TimeIntervalFormat fmt = new TimeIntervalFormat();
		Day startDay = new Day(8,JULY,2010);
		TimeInterval toBeFormatted = GenericTimeInterval.lowBoundedInterval(startDay);
		String result = fmt.format(toBeFormatted);
		assertEquals("08/07/2010...",result);
	}
	
	public void testFormatHighBoundedInterval() {
		TimeIntervalFormat fmt = new TimeIntervalFormat();
		Day startDay = new Day(8,JULY,2010);
		TimeInterval toBeFormatted = GenericTimeInterval.highBoundedInterval(startDay);
		String result = fmt.format(toBeFormatted);
		assertEquals("...08/07/2010",result);
	}
	
	public void testFormatMonthInterval() {
		TimeIntervalFormat fmt = new TimeIntervalFormat();
		Day startDay = new Day(1,JULY,2010);
		Day endDay = new Day(30,SEPTEMBER,2010);
		TimeInterval toBeFormatted = new GenericTimeInterval(startDay, endDay);
		String result = fmt.format(toBeFormatted);
		assertEquals("07/2010 - 09/2010",result);
	}
	
	public void testFormatLowBoundedMonthInterval() {
		TimeIntervalFormat fmt = new TimeIntervalFormat();
		Month startMonth = new Month(SEPTEMBER,2010);
		TimeInterval toBeFormatted = new GenericTimeInterval(startMonth, null);
		String result = fmt.format(toBeFormatted);
		assertEquals("09/2010...",result);
	}
	
	public void testFormatHighBoundedMonthInterval() {
		TimeIntervalFormat fmt = new TimeIntervalFormat();
		Month endMonth = new Month(SEPTEMBER,2010);
		TimeInterval toBeFormatted = new GenericTimeInterval(null, endMonth);
		String result = fmt.format(toBeFormatted);
		assertEquals("...09/2010",result);
	}
	
	public void testFormatYearInterval() {
		TimeIntervalFormat fmt = new TimeIntervalFormat();
		Day startDay = new Day(1,JANUARY,2010);
		Day endDay = new Day(31,DECEMBER,2012);
		TimeInterval toBeFormatted = new GenericTimeInterval(startDay, endDay);
		String result = fmt.format(toBeFormatted);
		assertEquals("2010 - 2012",result);
	}
	
	public void testFormatLowBoundedYearInterval() {
		TimeIntervalFormat fmt = new TimeIntervalFormat();
		Day startDay = new Day(1,JANUARY,2010);
		TimeInterval toBeFormatted = new GenericTimeInterval(startDay, null);
		String result = fmt.format(toBeFormatted);
		assertEquals("2010...",result);
	}
	
	public void testFormatHighBoundedYearInterval() {
		TimeIntervalFormat fmt = new TimeIntervalFormat();
		Day endDay = new Day(31,DECEMBER,2010);
		TimeInterval toBeFormatted = new GenericTimeInterval(null,endDay);
		String result = fmt.format(toBeFormatted);
		assertEquals("...2010",result);
	}

}

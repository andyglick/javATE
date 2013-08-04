package it.amattioli.encapsulate.dates;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import junit.framework.TestCase;

public class TimeIntervalFormatTest extends TestCase {
	private TimeIntervalFormat timeInt = TimeIntervalFormat.getInstance();
	private TimeIntervalFormat timeIntUs = TimeIntervalFormat.getInstance(Locale.US);
	//private DateFormat format = new SimpleDateFormat("dd/MM/yyyy");

	public void testFormatDay() {
		String testDate = timeInt.format(new Day(30,Calendar.JUNE,1978));
		assertEquals("30/06/1978", testDate);
	}
	
	public void testFormatDayUs() {
		String testDate = timeIntUs.format(new Day(30,Calendar.JUNE,1978));
		assertEquals("06/30/1978", testDate);
	}
	
	public void testFormatMonth() {
		String testDate = timeInt.format(new Month(Calendar.JUNE,1978));
		assertEquals("06/1978", testDate);
	}
	
	public void testFormatMonthUs() {
		String testDate = timeIntUs.format(new Month(Calendar.JUNE,1978));
		assertEquals("06/1978", testDate);
	}
	
	public void testFormatYear() {
		String testDate = timeInt.format(new Year(1978));
		assertEquals("1978", testDate);
	}
	
	public void testFormatYearUs() {
		String testDate = timeIntUs.format(new Year(1978));
		assertEquals("1978", testDate);
	}
	
	public void testFormatIntervalOfDays() {
		GenericTimeInterval interval = new GenericTimeInterval(new Day(30,Calendar.JUNE,1978),new Day(13,Calendar.JULY,1978));
		String testDate = timeInt.format(interval);
		assertEquals("30/06/1978 - 13/07/1978", testDate);
	}
	
	public void testFormatIntervalOfMonths() {
		GenericTimeInterval interval = new GenericTimeInterval(new Month(Calendar.JUNE,1978),new Month(Calendar.JULY,1978));
		String testDate = timeInt.format(interval);
		assertEquals("01/06/1978 - 31/07/1978", testDate);
	}
	
	public void testFormatIntervalOfYears() {
		GenericTimeInterval interval = new GenericTimeInterval(new Year(1978),new Year(1979));
		String testDate = timeInt.format(interval);
		assertEquals("01/01/1978 - 31/12/1979", testDate);
	}
	
	public void testParseDay() throws Exception {
		TimeInterval testDate = (TimeInterval)timeInt.parseObject("30/06/1978");
		assertEquals(new Day(30,Calendar.JUNE,1978),testDate);
	}
	
	public void testParseDayUs() throws Exception {
		TimeInterval testDate = (TimeInterval)timeIntUs.parseObject("06/30/1978");
		assertEquals(new Day(30,Calendar.JUNE,1978),testDate);
	}
	
	public void testParseMonth() throws ParseException {
		TimeInterval testDate = timeInt.parse("05/2004");
		assertEquals(testDate, new Month(Calendar.MAY,2004));
	}
	
	public void testParseMonthUs() throws ParseException {
		TimeInterval testDate = timeIntUs.parse("05/2004");
		assertEquals(testDate, new Month(Calendar.MAY,2004));
	}
	
	public void testParseYear() throws ParseException {
		TimeInterval testDate = timeInt.parse("2004");
		assertEquals(testDate, new Year(2004));
	}
	
	public void testParseYearUs() throws ParseException {
		TimeInterval testDate = timeIntUs.parse("2004");
		assertEquals(testDate, new Year(2004));
	}
	
	public void testParseInterval() throws ParseException {
		TimeInterval testDate = timeInt.parse("30/06/1978 - 27/07/2006");
		Date dataDa = (Date)testDate.getLow();
		Date dataA = (Date)testDate.getHigh();
		DateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		assertEquals("30/06/1978 00:00:00", format.format(dataDa));
		assertEquals("28/07/2006 00:00:00", format.format(dataA));
	}

}

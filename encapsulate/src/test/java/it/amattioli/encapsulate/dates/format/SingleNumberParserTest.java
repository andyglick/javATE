package it.amattioli.encapsulate.dates.format;

import static java.util.Calendar.*;

import java.util.Locale;

import it.amattioli.encapsulate.dates.Day;
import it.amattioli.encapsulate.dates.Month;
import it.amattioli.encapsulate.dates.TimeInterval;
import it.amattioli.encapsulate.dates.Year;
import junit.framework.TestCase;

public class SingleNumberParserTest extends TestCase {

	public void testWithoutOtherSide() throws Exception {
		SingleNumberParser p = new SingleNumberParser();
		TimeInterval result = p.parse("2009");
		assertEquals(new Year(2009), result);
	}
	
	public void testWithYearOnTheOtherSide() throws Exception {
		SingleNumberParser p = new SingleNumberParser(Locale.getDefault(), null,new Year(2010));
		TimeInterval result = p.parse("2009");
		assertEquals(new Year(2009), result);
	}
	
	public void testWithMonthOnTheOtherSide() throws Exception {
		SingleNumberParser p = new SingleNumberParser(Locale.getDefault(), null,new Month(FEBRUARY,2010));
		TimeInterval result = p.parse("10");
		assertEquals(new Month(OCTOBER,2010), result);
	}
	
	public void testWithDayOnTheOtherSide() throws Exception {
		SingleNumberParser p = new SingleNumberParser(Locale.getDefault(), null,new Day(22,FEBRUARY,2010));
		TimeInterval result = p.parse("10");
		assertEquals(new Day(10,FEBRUARY,2010), result);
	}
	
}

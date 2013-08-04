package it.amattioli.encapsulate.dates.format;

import static java.util.Calendar.*;

import java.text.ParseException;
import java.util.Locale;

import it.amattioli.encapsulate.dates.Day;
import it.amattioli.encapsulate.dates.TimeInterval;
import junit.framework.TestCase;

public class DayParserTest extends TestCase {

	public void testCorrectDay() throws Exception {
		DayParser p = DayParser.instance(Locale.ITALIAN, null);
		TimeInterval result = p.parse("13/01/2009");
		assertEquals(new Day(13,JANUARY,2009), result);
	}
	
	public void testIncorrectDay() throws Exception {
		DayParser p = DayParser.instance(Locale.ITALIAN, null);
		try {
			TimeInterval result = p.parse("01/2009");
			fail("Should throw ParseException");
		} catch(ParseException e) {
			// OK
		}
	}
	
}

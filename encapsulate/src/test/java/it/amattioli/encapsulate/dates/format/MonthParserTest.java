package it.amattioli.encapsulate.dates.format;

import static java.util.Calendar.*;

import java.text.ParseException;

import it.amattioli.encapsulate.dates.Month;
import it.amattioli.encapsulate.dates.TimeInterval;
import junit.framework.TestCase;

public class MonthParserTest extends TestCase {

	public void testCorrectMonth() throws Exception {
		MonthParser p = new MonthParser("\\d*/\\d*", "MM/yyyy", null);
		TimeInterval result = p.parse("01/2009");
		assertEquals(new Month(JANUARY,2009), result);
	}
	
	public void testIncorrectMonth() throws Exception {
		MonthParser p = new MonthParser("\\d*/\\d*", "MM/yyyy", null);
		try {
			TimeInterval result = p.parse("3/01/2009");
			fail("Should throw ParseException");
		} catch(ParseException e) {
			// OK
		}
	}
	
	public void testUnparseable() throws Exception {
		MonthParser p = new MonthParser("\\d*/\\d*", "MM/yyyy", null);
		try {
			TimeInterval result = p.parse("3/01/2009");
			fail("Should throw ParseException");
		} catch(ParseException e) {
			// OK
		}
	}
}

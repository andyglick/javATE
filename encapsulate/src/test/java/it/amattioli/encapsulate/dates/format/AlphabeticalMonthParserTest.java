package it.amattioli.encapsulate.dates.format;

import java.util.Calendar;
import java.util.Locale;

import it.amattioli.encapsulate.dates.Month;
import it.amattioli.encapsulate.dates.TimeInterval;
import it.amattioli.encapsulate.dates.Year;
import junit.framework.TestCase;

public class AlphabeticalMonthParserTest extends TestCase {
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

	public void testWithoutOtherSide() throws Exception {
		AlphabeticalMonthParser parser = new AlphabeticalMonthParser();
		TimeInterval result = parser.parse("Marzo");
		TimeInterval expected = new Month(Calendar.MARCH, Year.thisYear().getYear());
		assertEquals(expected, result);
	}
	
	public void testWithMonthOnOtherSide() throws Exception {
		Month otherSide = new Month(Calendar.JUNE, 2009);
		AlphabeticalMonthParser parser = new AlphabeticalMonthParser(Locale.getDefault(), null, otherSide);
		TimeInterval result = parser.parse("Marzo");
		TimeInterval expected = new Month(Calendar.MARCH, otherSide.getYear());
		assertEquals(expected, result);
	}
	
}

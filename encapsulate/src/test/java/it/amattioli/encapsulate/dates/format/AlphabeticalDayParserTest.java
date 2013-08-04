package it.amattioli.encapsulate.dates.format;

import java.util.Locale;

import it.amattioli.encapsulate.dates.Day;
import it.amattioli.encapsulate.dates.TimeInterval;
import junit.framework.TestCase;

public class AlphabeticalDayParserTest extends TestCase {
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
	
	public void testYesterday() throws Exception {
		AlphabeticalDayParser parser = new AlphabeticalDayParser();
		TimeInterval result = parser.parse("ieri");
		assertEquals(Day.yesterday(), result);
	}
	
	public void testToday() throws Exception {
		AlphabeticalDayParser parser = new AlphabeticalDayParser();
		TimeInterval result = parser.parse("oggi");
		assertEquals(Day.today(), result);
	}
	
	public void testTomorrow() throws Exception {
		AlphabeticalDayParser parser = new AlphabeticalDayParser();
		TimeInterval result = parser.parse("domani");
		assertEquals(Day.tomorrow(), result);
	}
}

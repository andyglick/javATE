package it.amattioli.encapsulate.dates;

import java.text.SimpleDateFormat;
import java.util.Date;

import junit.framework.TestCase;

public class CompositeDurationTest extends TestCase {
	static SimpleDateFormat timeFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

	public void testAfter() throws Exception {
		Date begin = timeFormat.parse("03/01/2003 11:00:00");
		CompositeDuration d = new CompositeDuration(ConventionalDuration.ONE_DAY, PhysicalDuration.ONE_HOUR);
		Date end = d.after(begin);
		Date expected = timeFormat.parse("04/01/2003 12:00:00");
		assertEquals(expected, end);
	}
	
	public void testBefore() throws Exception {
		Date begin = timeFormat.parse("03/01/2003 11:00:00");
		CompositeDuration d = new CompositeDuration(ConventionalDuration.ONE_DAY, PhysicalDuration.ONE_HOUR);
		Date end = d.before(begin);
		Date expected = timeFormat.parse("02/01/2003 10:00:00");
		assertEquals(expected, end);
	}
	
}

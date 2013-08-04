package it.amattioli.encapsulate.dates;

import java.text.ParseException;

import junit.framework.TestCase;

public class DurationFormatTest extends TestCase {
	
	public void testFormat() throws Exception {
		PhysicalDuration d = PhysicalDuration.fromSeconds(86520);
		DurationFormat df = new DurationFormat();
		assertEquals("1 day 2 minutes",df.format(d));
	}
	
	public void tespParse1() throws Exception {
		DurationFormat df = new DurationFormat();
		PhysicalDuration d = (PhysicalDuration)df.parseObject("2 minutes   12  s");
		assertEquals(PhysicalDuration.fromSeconds(132),d);
	}
	
	public void tespParse2() throws Exception {
		DurationFormat df = new DurationFormat();
		PhysicalDuration d = (PhysicalDuration)df.parseObject("1hour 12 s");
		assertEquals(PhysicalDuration.fromSeconds(3612),d);
	}
	
	public void tespParse3() throws Exception {
		DurationFormat df = new DurationFormat();
		PhysicalDuration d = (PhysicalDuration)df.parseObject("     1hour 12 s   ");
		assertEquals(PhysicalDuration.fromSeconds(3612),d);
	}
	
	public void tespParseWrongNumber() throws Exception {
		DurationFormat df = new DurationFormat();
		try {
			df.parseObject("f2 minutes");
			fail();
		} catch(ParseException e) {
			
		}
	}
	
	public void tespParseWrongUnit() throws Exception {
		DurationFormat df = new DurationFormat();
		try {
			df.parseObject("2 ciao");
			fail();
		} catch(ParseException e) {
			
		}
	}
	
	public void tespParseMissingUnit() throws Exception {
		DurationFormat df = new DurationFormat();
		try {
			df.parseObject("2 hours 4");
			fail();
		} catch(ParseException e) {
			
		}
	}

}

package it.amattioli.guidate.converters;

import java.util.Date;

import junit.framework.TestCase;

public class ConvertersTest extends TestCase {

	public void testCreateForString() {
		assertTrue(Converters.createFor(String.class) instanceof NullConverter);
	}
	
	public void testCreateForLong() {
		assertTrue(Converters.createFor(Long.class) instanceof IntConverter);
	}
	
	public void testCreateForInteger() {
		assertTrue(Converters.createFor(Integer.class) instanceof IntConverter);
	}
	
	public void testCreateForDate() {
		assertTrue(Converters.createFor(Date.class) instanceof DayConverter);
	}

	public void testCreateForEntity() {
		assertTrue(Converters.createFor(AnEntity.class) instanceof EntityConverter);
	}
	
	public void testCreateFromNull() {
		assertTrue(Converters.createFrom(null) instanceof AutoConverter);
	}
}

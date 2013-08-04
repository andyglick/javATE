package it.amattioli.guidate.converters;

import junit.framework.TestCase;

public class IntConverterTest extends TestCase {

	public void testCoerceToBean() {
		IntConverter converter = new IntConverter();
		Object l = converter.coerceToBean("1", null);
		assertEquals(Long.valueOf(1), l);
	}
	
	public void testCoerceToUi() {
		IntConverter converter = new IntConverter();
		Object s = converter.coerceToUi(Long.valueOf(1), null);
		assertEquals("1", s);
	}
}

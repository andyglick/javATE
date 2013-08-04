package it.amattioli.guidate.converters;

import junit.framework.TestCase;

public class DefaultConverterTest extends TestCase {

	public void testStringConversion() {
		DefaultConverter conv = new DefaultConverter();
		assertEquals("Hello",conv.coerceToUi("Hello", null));
	}
	
	public void testObjectConversion() {
		Object o = new Long(1);
		DefaultConverter conv = new DefaultConverter();
		assertEquals(o.toString(),conv.coerceToUi(o, null));
	}
	
	public void testDescribedConversion() {
		ADescribedEntity e = new ADescribedEntity();
		DefaultConverter conv = new DefaultConverter();
		assertEquals(e.getDescription(),conv.coerceToUi(e, null));
	}
	
}

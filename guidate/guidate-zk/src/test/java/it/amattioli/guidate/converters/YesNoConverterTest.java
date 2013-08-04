package it.amattioli.guidate.converters;

import java.util.Locale;

import org.zkoss.util.Locales;

import junit.framework.TestCase;

public class YesNoConverterTest extends TestCase {

	public void testCoerceToUi() {
		Locales.setThreadLocal(Locale.US);
		YesNoConverter c = new YesNoConverter();
		assertEquals("No",c.coerceToUi(Boolean.FALSE, null));
		assertEquals("Yes",c.coerceToUi(Boolean.TRUE, null));
	}
	
}

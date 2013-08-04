package it.amattioli.encapsulate.money;

import java.math.BigDecimal;
import java.util.Currency;

import junit.framework.TestCase;

public class EuroTest extends TestCase {

	public void testIncompatibleCompare() throws Exception {
		Money dollars = new Money(new BigDecimal("100"),Currency.getInstance("USD"));
		Euro euros = new Euro("100");
		try {
			euros.compareTo(dollars);
			fail();
		} catch(ClassCastException e) {
			assertEquals("Non e` possibile confrontare due Money con divisa diversa", e.getMessage());
		}
	}

}

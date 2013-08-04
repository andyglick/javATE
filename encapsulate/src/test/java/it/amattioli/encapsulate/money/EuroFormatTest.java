package it.amattioli.encapsulate.money;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Locale;

import junit.framework.TestCase;

public class EuroFormatTest extends TestCase {
	
	public void testParseCorrectMoney() throws Exception {
		EuroFormat mf = new EuroFormat(Locale.ITALY);
		Money m = (Money)mf.parseObject("123456");
		assertEquals("EUR",m.getCurrency().getCurrencyCode());
		assertEquals(new BigDecimal("123456.00"),m.getValue());
	}
	
	public void testParseLittleMoney() throws Exception {
		EuroFormat mf = new EuroFormat(Locale.ITALY);
		Money m = (Money)mf.parseObject("0,10");
		assertEquals("EUR",m.getCurrency().getCurrencyCode());
		assertEquals(new BigDecimal("0.10"),m.getValue());
	}
	
	public void testParseWrongMoney() throws Exception {
		try {
			EuroFormat mf = new EuroFormat(Locale.ITALY);
			mf.parseObject("pippo");
			fail("Dovrebbe generare ParseException");
		} catch(ParseException e) {
			// OK
		}
	}
	
	public void testParseNullMoney() throws Exception {
		EuroFormat mf = new EuroFormat(Locale.ITALY);
		Money m = mf.parse(null);
		assertNull(m);
	}
	
	public void testParseEmptyMoney() throws Exception {
		EuroFormat mf = new EuroFormat(Locale.ITALY);
		Money m = mf.parse("");
		assertNull(m);
	}
	
}

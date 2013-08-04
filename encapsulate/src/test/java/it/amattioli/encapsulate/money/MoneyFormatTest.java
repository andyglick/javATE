package it.amattioli.encapsulate.money;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Currency;
import java.util.Locale;

import junit.framework.TestCase;

public class MoneyFormatTest extends TestCase {
	
	public void testParseCorrectMoney() throws Exception {
		MoneyFormat mf = new MoneyFormat(Currency.getInstance("EUR"));
		Money m = (Money)mf.parseObject("123456");
		assertEquals("EUR",m.getCurrency().getCurrencyCode());
		assertEquals(new BigDecimal("123456.00"),m.getValue());
	}
	
	public void testParseLittleMoney() throws Exception {
		MoneyFormat mf = new MoneyFormat(Locale.ITALY, Currency.getInstance("EUR"));
		Money m = (Money)mf.parseObject("0,10");
		assertEquals("EUR",m.getCurrency().getCurrencyCode());
		assertEquals(new BigDecimal("0.10"),m.getValue());
	}
	
	public void testParseWrongMoney() throws Exception {
		try {
			MoneyFormat mf = new MoneyFormat(Currency.getInstance("EUR"));
			mf.parse("pippo");
			fail("Dovrebbe generare ParseException");
		} catch(ParseException e) {
			// OK
		}
	}
	
	public void testParseNullMoney() throws Exception {
		MoneyFormat mf = new MoneyFormat(Currency.getInstance("EUR"));
		Money m = (Money)mf.parse(null);
		assertNull(m);
	}
	
	public void testParseEmptyMoney() throws Exception {
		MoneyFormat mf = new MoneyFormat(Currency.getInstance("EUR"));
		Money m = (Money)mf.parse("");
		assertNull(m);
	}

}

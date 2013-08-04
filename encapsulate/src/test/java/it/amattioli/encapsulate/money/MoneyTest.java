package it.amattioli.encapsulate.money;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Currency;

import junit.framework.TestCase;

public class MoneyTest extends TestCase {
	
	public void testMoney() {
		Money m = new Money(new BigDecimal("123.65"),Currency.getInstance("USD"));
		assertEquals(new BigDecimal("123.65"),m.getValue());
		assertEquals(Currency.getInstance("USD"),m.getCurrency());
	}
	
	public void testEquals() {
		Money m1 = new Money(new BigDecimal("100"),Currency.getInstance("USD"));
		Money m2 = new Money(new BigDecimal("150"),Currency.getInstance("USD"));
		Money me = new Money(new BigDecimal("100"),Currency.getInstance("EUR"));
		assertTrue(m1.equals(m1));
		assertFalse(m1.equals(m2));
		assertFalse(m1.equals(me));
	}
	
	public void testNullValueConstruction() {
		try {
			Money m = new Money(null, Currency.getInstance("USD"));
			fail();
		} catch(NullPointerException e) {
			assertEquals("Non e` possibile costruire un'istanza di Money con value null", e.getMessage());
		}
	}
	
	public void testNullCurrencyConstruction() {
		try {
			Money m = new Money(new BigDecimal("123.65"), null);
			fail();
		} catch(NullPointerException e) {
			assertEquals("Non e` possibile costruire un'istanza di Money con currency null", e.getMessage());
		}
	}
	
	public void testSum() throws Exception {
		Money m1 = new Money(new BigDecimal("123.65"),Currency.getInstance("USD"));
		Money m2 = new Money(new BigDecimal("564.32"),Currency.getInstance("USD"));
		Money m3 = Money.sum(m1,m2);
		assertEquals(new BigDecimal("687.97"),m3.getValue());
		assertEquals(Currency.getInstance("USD"),m3.getCurrency());
	}
	
	public void testAdd() throws Exception {
		Money m1 = new Money(new BigDecimal("123.65"),Currency.getInstance("USD"));
		Money m2 = new Money(new BigDecimal("564.32"),Currency.getInstance("USD"));
		Money m3 = m1.add(m2);
		assertEquals(new BigDecimal("687.97"),m3.getValue());
	}
	
	public void testIncompatibleAdd() throws Exception {
		Money dollars = new Money(new BigDecimal("100"),Currency.getInstance("USD"));
		Money euros = new Money(new BigDecimal("100"),Currency.getInstance("EUR"));
		try {
			dollars.add(euros);
			fail();
		} catch(IncompatibleCurrency e) {
			
		}
	}
	
	public void testSumCollection() throws Exception {
		Money m1 = new Money(new BigDecimal("123.65"),Currency.getInstance("USD"));
		Money m2 = new Money(new BigDecimal("564.32"),Currency.getInstance("USD"));
		ArrayList<Money> c = new ArrayList<Money>();
		c.add(m1);
		c.add(m2);
		Money m3 = Money.sum(c);
		assertEquals(new BigDecimal("687.97"),m3.getValue());
		assertEquals(Currency.getInstance("USD"),m3.getCurrency());
	}
	
	public void testIncompatibleSumCollection() throws Exception {
		Money m1 = new Money(new BigDecimal("123.65"),Currency.getInstance("USD"));
		Money m2 = new Money(new BigDecimal("564.32"),Currency.getInstance("EUR"));
		ArrayList<Money> c = new ArrayList<Money>();
		c.add(m1);
		c.add(m2);
		try {
			Money m3 = Money.sum(c);
			fail();
		} catch(IncompatibleCurrency e) {
			
		}
	}
	
	public void testSubtract() throws Exception {
		Money m1 = new Money(new BigDecimal("123.65"),Currency.getInstance("USD"));
		Money m2 = new Money(new BigDecimal("564.32"),Currency.getInstance("USD"));
		Money m3 = Money.subtract(m2,m1);
		assertEquals(new BigDecimal("440.67"),m3.getValue());
		assertEquals(Currency.getInstance("USD"),m3.getCurrency());
	}
	
	public void testIncompatibleSubtract() throws Exception {
		Money dollars = new Money(new BigDecimal("100"),Currency.getInstance("USD"));
		Money euros = new Money(new BigDecimal("100"),Currency.getInstance("EUR"));
		try {
			dollars.subtract(euros);
			fail();
		} catch(IncompatibleCurrency e) {
			
		}
	}
	
	public void testIntMultiply() throws Exception {
		Money m1 = new Money(new BigDecimal("100"),Currency.getInstance("USD"));
		Money m2 = new Money(new BigDecimal("200"),Currency.getInstance("USD"));
		assertEquals(m2, m1.multiply(2));
		assertEquals(new BigDecimal("100.00"), m1.getValue());
	}
	
	public void testBigDecimalMultiply() throws Exception {
		Money m1 = new Money(new BigDecimal("100"),Currency.getInstance("USD"));
		Money m2 = new Money(new BigDecimal("150"),Currency.getInstance("USD"));
		assertEquals(m2, m1.multiply(new BigDecimal("1.5")));
		assertEquals(new BigDecimal("100.00"), m1.getValue());
	}
	
	public void testAddPercent() throws Exception {
		Money m1 = new Money(new BigDecimal("100"),Currency.getInstance("USD"));
		Money m2 = new Money(new BigDecimal("150"),Currency.getInstance("USD"));
		assertEquals(m2, m1.addPercent(new BigDecimal("0.5")));
	}
	
	public void testSubtractPercent() throws Exception {
		Money m1 = new Money(new BigDecimal("100"),Currency.getInstance("USD"));
		Money m2 = new Money(new BigDecimal("50"),Currency.getInstance("USD"));
		assertEquals(m2, m1.subtractPercent(new BigDecimal("0.5")));
	}
	
	public void testAllocate() throws Exception {
		Money m1 = new Money(new BigDecimal("100"),Currency.getInstance("USD"));
		Money[] expected = new Money[] {
				new Money(new BigDecimal("33.34"),Currency.getInstance("USD")),
				new Money(new BigDecimal("33.33"),Currency.getInstance("USD")),
				new Money(new BigDecimal("33.33"),Currency.getInstance("USD"))
		};
		Money[] result = m1.allocate(3);
		assertEquals(3, result.length);
		assertEquals(expected[0], result[0]);
		assertEquals(expected[1], result[1]);
		assertEquals(expected[2], result[2]);
	}
	
	public void testAllocateNegative() throws Exception {
		Money m1 = new Money(new BigDecimal("-100"),Currency.getInstance("USD"));
		Money[] expected = new Money[] {
				new Money(new BigDecimal("-33.34"),Currency.getInstance("USD")),
				new Money(new BigDecimal("-33.33"),Currency.getInstance("USD")),
				new Money(new BigDecimal("-33.33"),Currency.getInstance("USD"))
		};
		Money[] result = m1.allocate(3);
		assertEquals(3, result.length);
		assertEquals(expected[0], result[0]);
		assertEquals(expected[1], result[1]);
		assertEquals(expected[2], result[2]);
	}
	
	public void testAllocateMoneyRatios() throws Exception {
		Money m1 = new Money(new BigDecimal("100"),Currency.getInstance("USD"));
		Money[] ratios = new Money[] {
				new Money(new BigDecimal("10.00"),Currency.getInstance("USD")),
				new Money(new BigDecimal("10.00"),Currency.getInstance("USD")),
				new Money(new BigDecimal("20.00"),Currency.getInstance("USD"))
		};
		Money[] expected = new Money[] {
				new Money(new BigDecimal("25.00"),Currency.getInstance("USD")),
				new Money(new BigDecimal("25.00"),Currency.getInstance("USD")),
				new Money(new BigDecimal("50.00"),Currency.getInstance("USD"))
		};
		Money[] result = m1.allocate(ratios);
		assertEquals(3, result.length);
		assertEquals(expected[0], result[0]);
		assertEquals(expected[1], result[1]);
		assertEquals(expected[2], result[2]);
	}
	
	public void testAllocateNegativeMoneyRatios() throws Exception {
		Money m1 = new Money(new BigDecimal("-100.01"),Currency.getInstance("USD"));
		Money[] ratios = new Money[] {
				new Money(new BigDecimal("10.00"),Currency.getInstance("USD")),
				new Money(new BigDecimal("10.00"),Currency.getInstance("USD")),
				new Money(new BigDecimal("20.00"),Currency.getInstance("USD"))
		};
		Money[] expected = new Money[] {
				new Money(new BigDecimal("-25.01"),Currency.getInstance("USD")),
				new Money(new BigDecimal("-25.00"),Currency.getInstance("USD")),
				new Money(new BigDecimal("-50.00"),Currency.getInstance("USD"))
		};
		Money[] result = m1.allocate(ratios);
		assertEquals(3, result.length);
		assertEquals(expected[0], result[0]);
		assertEquals(expected[1], result[1]);
		assertEquals(expected[2], result[2]);
	}
	
	public void testCompare() throws Exception {
		Money m1 = new Money(new BigDecimal("123.65"),Currency.getInstance("USD"));
		Money m2 = new Money(new BigDecimal("564.32"),Currency.getInstance("USD"));
		assertEquals(-1, m1.compareTo(m2));
		assertEquals(1, m2.compareTo(m1));
		assertEquals(0, m1.compareTo(m1));
	}
	
	public void testIncompatibleCompare() throws Exception {
		Money dollars = new Money(new BigDecimal("100"),Currency.getInstance("USD"));
		Euro euros = new Euro("100");
		try {
			dollars.compareTo(euros);
			fail();
		} catch(ClassCastException e) {
			assertEquals("Non e` possibile confrontare due Money con divisa diversa", e.getMessage());
		}
	}
	
	public void testPropertySum() {
		Collection<FakeMoneyEntity> es = new ArrayList<FakeMoneyEntity>();
		FakeMoneyEntity e1 = new FakeMoneyEntity();
		e1.setMoney(new Money(10L,Currency.getInstance("EUR")));
		es.add(e1);
		FakeMoneyEntity e2 = new FakeMoneyEntity();
		e2.setMoney(new Money(11L,Currency.getInstance("EUR")));
		es.add(e2);
		FakeMoneyEntity e3 = new FakeMoneyEntity();
		e3.setMoney(new Money(17L,Currency.getInstance("EUR")));
		es.add(e3);
		assertEquals(new Money(38L,Currency.getInstance("EUR")), Money.propertySum(es, "money"));
	}

	public void testEuro() {
		BigDecimal ten = new BigDecimal("10.00");
		Money tenEuros = Money.euro(ten);
		assertEquals(ten, tenEuros.getValue());
		assertEquals("EUR", tenEuros.getCurrency().getCurrencyCode());
	}
	
	public void testEuroInt() {
		Money tenEuros = Money.euro(10);
		assertEquals(new BigDecimal("10.00"), tenEuros.getValue());
		assertEquals("EUR", tenEuros.getCurrency().getCurrencyCode());
	}
	
	public void testEuroLong() {
		Money tenEuros = Money.euro(10L);
		assertEquals(new BigDecimal("10.00"), tenEuros.getValue());
		assertEquals("EUR", tenEuros.getCurrency().getCurrencyCode());
	}
}


package it.amattioli.encapsulate.percent;

import java.math.BigDecimal;

import junit.framework.TestCase;

public class PercentTest extends TestCase {

	public void testMultiplier() {
		Percent p = new Percent("0.1");
		assertEquals(new BigDecimal("0.1"), p.getMultiplier());
	}
	
	public void testOf() {
		Percent tenPercent = new Percent("0.1");
		BigDecimal result = tenPercent.of(new BigDecimal("120"));
		assertEquals(new BigDecimal("12.0"), result);
	}
	
	public void testAddTo() {
		Percent tenPercent = new Percent("0.1");
		BigDecimal result = tenPercent.addTo(new BigDecimal("120"));
		assertEquals(new BigDecimal("132.0"), result);
	}
	
}

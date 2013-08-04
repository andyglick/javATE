package it.amattioli.encapsulate.range;

import java.text.ParseException;

import junit.framework.TestCase;

public class NumericRangeformatTest extends TestCase {

	public void testDoubleBoundedRangeFormat() {
		Range<Long> r = new GenericContinousRange<Long>(1L, 10L);
		NumericRangeFormat fmt = NumericRangeFormat.getInstance();
		assertEquals("1 - 10", fmt.format(r));
	}
	
	public void testLowBoundedRangeFormat() {
		Range<Long> r = new GenericContinousRange<Long>(1L, null);
		NumericRangeFormat fmt = NumericRangeFormat.getInstance();
		assertEquals("1 - ", fmt.format(r));
	}
	
	public void testHighBoundedRangeFormat() {
		Range<Long> r = new GenericContinousRange<Long>(null, 10L);
		NumericRangeFormat fmt = NumericRangeFormat.getInstance();
		assertEquals(" - 10", fmt.format(r));
	}
	
	public void testDoubleBoundedRangeParsing1() throws Exception {
		NumericRangeFormat fmt = NumericRangeFormat.getInstance();
		Range<?> r = (Range<?>)NumericRangeFormat.getInstance().parseObject("1 10 ");
		assertEquals(1L, r.getLow());
		assertEquals(10L, r.getHigh());
	}
	
	public void testDoubleBoundedRangeParsing2() throws Exception {
		NumericRangeFormat fmt = NumericRangeFormat.getInstance();
		Range<?> r = (Range<?>)NumericRangeFormat.getInstance().parseObject("1-10");
		assertEquals(1L, r.getLow());
		assertEquals(10L, r.getHigh());
	}
	
	public void testDoubleBoundedRangeParsing3() throws Exception {
		NumericRangeFormat fmt = NumericRangeFormat.getInstance();
		Range<?> r = (Range<?>)NumericRangeFormat.getInstance().parseObject("1 - 10");
		assertEquals(1L, r.getLow());
		assertEquals(10L, r.getHigh());
	}
	
	public void testDoubleBoundedRangeParsing4() throws Exception {
		NumericRangeFormat fmt = NumericRangeFormat.getInstance();
		Range<?> r = (Range<?>)NumericRangeFormat.getInstance().parseObject("-1 -10");
		assertEquals(-1L, r.getLow());
		assertEquals(-10L, r.getHigh());
	}
	
	public void testLowBoundedRangeParsing1() throws Exception {
		NumericRangeFormat fmt = NumericRangeFormat.getInstance();
		Range<?> r = (Range<?>)NumericRangeFormat.getInstance().parseObject("1 - ");
		assertEquals(1L, r.getLow());
		assertFalse(r.isHighBounded());
	}
	
	public void testLowBoundedRangeParsing2() throws Exception {
		NumericRangeFormat fmt = NumericRangeFormat.getInstance();
		Range<?> r = (Range<?>)NumericRangeFormat.getInstance().parseObject("1 ... ");
		assertEquals(1L, r.getLow());
		assertFalse(r.isHighBounded());
	}
	
	public void testLowBoundedRangeParsing3() throws Exception {
		NumericRangeFormat fmt = NumericRangeFormat.getInstance();
		Range<?> r = (Range<?>)NumericRangeFormat.getInstance().parseObject("1-... ");
		assertEquals(1L, r.getLow());
		assertFalse(r.isHighBounded());
	}
	
	public void testLowBoundedRangeParsing4() throws Exception {
		NumericRangeFormat fmt = NumericRangeFormat.getInstance();
		Range<?> r = (Range<?>)NumericRangeFormat.getInstance().parseObject("1... ");
		assertEquals(1L, r.getLow());
		assertFalse(r.isHighBounded());
	}
	
	public void testHighBoundedRangeParsing1() throws Exception {
		NumericRangeFormat fmt = NumericRangeFormat.getInstance();
		Range<?> r = (Range<?>)NumericRangeFormat.getInstance().parseObject("- 10");
		assertFalse(r.isLowBounded());
		assertEquals(10L, r.getHigh());
	}
	
	public void testHighBoundedRangeParsing2() throws Exception {
		NumericRangeFormat fmt = NumericRangeFormat.getInstance();
		Range<?> r = (Range<?>)NumericRangeFormat.getInstance().parseObject("... 10");
		assertFalse(r.isLowBounded());
		assertEquals(10L, r.getHigh());
	}
	
	public void testHighBoundedRangeParsing3() throws Exception {
		NumericRangeFormat fmt = NumericRangeFormat.getInstance();
		Range<?> r = (Range<?>)NumericRangeFormat.getInstance().parseObject("...-10");
		assertFalse(r.isLowBounded());
		assertEquals(10L, r.getHigh());
	}
	
	public void testHighBoundedRangeParsing4() throws Exception {
		NumericRangeFormat fmt = NumericRangeFormat.getInstance();
		Range<?> r = (Range<?>)NumericRangeFormat.getInstance().parseObject("... 10");
		assertFalse(r.isLowBounded());
		assertEquals(10L, r.getHigh());
	}
	
	public void testHighBoundedRangeParsing5() throws Exception {
		NumericRangeFormat fmt = NumericRangeFormat.getInstance();
		Range<?> r = (Range<?>)NumericRangeFormat.getInstance().parseObject("... -10");
		assertFalse(r.isLowBounded());
		assertEquals(-10L, r.getHigh());
	}
	
	public void testSingleNumberParsing() throws Exception {
		NumericRangeFormat fmt = NumericRangeFormat.getInstance();
		Range<?> r = (Range<?>)NumericRangeFormat.getInstance().parseObject("10");
		assertEquals(10L, r.getLow());
		assertEquals(10L, r.getHigh());
	}
	
	public void testWrongStringParsing() throws Exception {
		try {
			NumericRangeFormat fmt = NumericRangeFormat.getInstance();
			Range<?> r = (Range<?>)NumericRangeFormat.getInstance().parseObject("this is wrong!");
			fail("Should throw exception");
		} catch(ParseException e) {
			
		}
	}
	
}

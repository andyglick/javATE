package it.amattioli.dominate.util;

import junit.framework.TestCase;

public class GenericComparatorTest extends TestCase {
	
	public void testCompare() {
		ComparingBean b1 = new ComparingBean(1,"one");
		ComparingBean b2 = new ComparingBean(2,"two");
		GenericComparator<ComparingBean> comp = new GenericComparator<ComparingBean>("stringValue");
		assertTrue(comp.compare(b1,b2) < 0);
		assertTrue(comp.compare(b2,b1) > 0);
		assertTrue(comp.compare(b1,b1) == 0);
	}
	
	public void testCompareNullFirst() {
		ComparingBean b1 = new ComparingBean(1,"one");
		ComparingBean b2 = new ComparingBean(2,null);
		GenericComparator<ComparingBean> comp = new GenericComparator<ComparingBean>("stringValue", false);
		assertTrue(comp.compare(b1,b2) < 0);
		assertTrue(comp.compare(b2,b1) > 0);
	}

	public static class ComparingBean {
		private String stringValue;
		private Integer integerValue;

		public ComparingBean(Integer integerValue, String stringValue) {
			this.integerValue = integerValue;
			this.stringValue = stringValue;
		}

		public String getStringValue() {
			return stringValue;
		}

		public void setStringValue(String stringValue) {
			this.stringValue = stringValue;
		}

		public Integer getIntegerValue() {
			return integerValue;
		}

		public void setIntegerValue(Integer integerValue) {
			this.integerValue = integerValue;
		}
		
	}
	
}

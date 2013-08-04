package it.amattioli.dominate.util;

import it.amattioli.dominate.repositories.Order;
import junit.framework.TestCase;

public class MultiPropertyComparatorTest extends TestCase {
	
	public void testCompareSingleProperty() {
		ComparingBean b1 = new ComparingBean(1,"one");
		ComparingBean b2 = new ComparingBean(2,"two");
		MultiPropertyComparator<ComparingBean> comp = new MultiPropertyComparator<ComparingBean>("stringValue");
		assertTrue(comp.compare(b1,b2) < 0);
		assertTrue(comp.compare(b2,b1) > 0);
		assertTrue(comp.compare(b1,b1) == 0);
	}
	
	public void testCompareSinglePropertyReversed() {
		ComparingBean b1 = new ComparingBean(1,"one");
		ComparingBean b2 = new ComparingBean(2,"two");
		MultiPropertyComparator<ComparingBean> comp = new MultiPropertyComparator<ComparingBean>("stringValue", true, false);
		assertTrue(comp.compare(b1,b2) > 0);
		assertTrue(comp.compare(b2,b1) < 0);
		assertTrue(comp.compare(b1,b1) == 0);
	}
	
	public void testCompareTwoProperties1() {
		ComparingBean b1 = new ComparingBean(1,"one");
		ComparingBean b2 = new ComparingBean(1,"two");
		Order o1 = new Order("integerValue",false);
		Order o2 = new Order("stringValue",false);
		MultiPropertyComparator<ComparingBean> comp = new MultiPropertyComparator<ComparingBean>(o1,o2);
		assertTrue(comp.compare(b1,b2) < 0);
		assertTrue(comp.compare(b2,b1) > 0);
		assertTrue(comp.compare(b1,b1) == 0);
	}
	
	public void testCompareTwoProperties2() {
		ComparingBean b1 = new ComparingBean(1,"one");
		ComparingBean b2 = new ComparingBean(1,"two");
		Order o1 = new Order("stringValue",false);
		Order o2 = new Order("integerValue",false);
		MultiPropertyComparator<ComparingBean> comp = new MultiPropertyComparator<ComparingBean>(o1,o2);
		assertTrue(comp.compare(b1,b2) < 0);
		assertTrue(comp.compare(b2,b1) > 0);
		assertTrue(comp.compare(b1,b1) == 0);
	}
	
	public void testCompareTwoPropertiesReversed() {
		ComparingBean b1 = new ComparingBean(1,"one");
		ComparingBean b2 = new ComparingBean(1,"two");
		Order o1 = new Order("integerValue",true);
		Order o2 = new Order("stringValue",true);
		MultiPropertyComparator<ComparingBean> comp = new MultiPropertyComparator<ComparingBean>(o1,o2);
		assertTrue(comp.compare(b1,b2) > 0);
		assertTrue(comp.compare(b2,b1) < 0);
		assertTrue(comp.compare(b1,b1) == 0);
	}
	
	public void testCompareSinglePropertyNullFirst1() {
		ComparingBean b1 = new ComparingBean(1,"one");
		ComparingBean b2 = new ComparingBean(1,null);
		Order o1 = new Order("stringValue",false);
		Order o2 = new Order("integerValue",false);
		MultiPropertyComparator<ComparingBean> comp = new MultiPropertyComparator<ComparingBean>(o1,o2);
		assertTrue(comp.compare(b1,b2) < 0);
		assertTrue(comp.compare(b2,b1) > 0);
	}
	
	public void testCompareSinglePropertyNullFirst2() {
		ComparingBean b1 = new ComparingBean(1,"one");
		ComparingBean b2 = new ComparingBean(1,null);
		Order o1 = new Order("integerValue",false);
		Order o2 = new Order("stringValue",false);
		MultiPropertyComparator<ComparingBean> comp = new MultiPropertyComparator<ComparingBean>(o1,o2);
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

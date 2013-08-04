package it.amattioli.dominate.properties;

import junit.framework.TestCase;

public class PropertiesTest extends TestCase {

	public class MyBean {
		private String myProperty = "defaultValue";
		private MyBean nested;
		
		public String getMyProperty() {
			return myProperty;
		}
		
		public void setMyProperty(String value) {
			myProperty = value;
		}

		public MyBean getNested() {
			return nested;
		}

		public void setNested(MyBean nested) {
			this.nested = nested;
		}
	}
	
	public void testGet() {
		MyBean m = new MyBean();
		String v = (String)Properties.get(m, "myProperty");
		assertEquals(v, "defaultValue");
	}
	
	public void testGetUnreadeable() {
		try {
			MyBean m = new MyBean();
			String v = (String)Properties.get(m, "unreadeable");
			fail("Should throw UnreadeablePropertyException");
		} catch (UnreadeablePropertyException e) {
			// Success !!!
		}
	}
	
	public void testGetNestedNull() {
		MyBean m = new MyBean();
		String v = (String)Properties.get(m, "nested.myProperty");
		assertNull(v);
	}
	
	public void testSet() {
		MyBean m = new MyBean();
		Properties.set(m, "myProperty", "newValue");
		assertEquals("newValue", m.getMyProperty());
	}
	
	public void testSetUnwritable() {
		try {
			MyBean m = new MyBean();
			Properties.set(m, "unwritable", "newValue");
			fail("Should throw UnwritablePropertyException");
		} catch(UnwritablePropertyException e) {
			// Success !!!
		}
	}
	
}

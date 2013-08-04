package it.amattioli.dominate.repositories;

import junit.framework.TestCase;

public class OrderListTest extends TestCase {

	public void testAdd() {
		OrderList l = new OrderList();
		l.add("property1", false);
		assertEquals(1,l.getOrders().size());
		assertEquals("property1",l.getOrders().get(0).getProperty());
		assertEquals(false,l.getOrders().get(0).isReverse());
	}
	
	public void testClear() {
		OrderList l = new OrderList();
		l.add("property1", false);
		l.clear();
		assertTrue(l.getOrders().isEmpty());
	}
	
	public void testRemoveLastWhen1() {
		OrderList l = new OrderList();
		l.add("property1", false);
		l.removeLast();
		assertTrue(l.getOrders().isEmpty());
	}
	
	public void testRemoveLastWhen2() {
		OrderList l = new OrderList();
		l.add("property1", false);
		l.add("property2", true);
		l.removeLast();
		assertEquals(1,l.getOrders().size());
		assertEquals("property1",l.getOrders().get(0).getProperty());
		assertEquals(false,l.getOrders().get(0).isReverse());
	}
	
	public void testRemoveLastWhenEmpty() {
		OrderList l = new OrderList();
		assertTrue(l.getOrders().isEmpty());
	}
	
	public void testFirstProperty() {
		OrderList l = new OrderList();
		l.add("property1", false);
		l.add("property2", true);
		assertEquals("property1",l.getFirstProperty());
	}
	
	public void testFirstPropertyWhenEmpty() {
		OrderList l = new OrderList();
		assertNull(l.getFirstProperty());
	}
	
	public void testLastProperty() {
		OrderList l = new OrderList();
		l.add("property1", false);
		l.add("property2", true);
		assertEquals("property2",l.getLastProperty());
	}
	
	public void testLastPropertyWhenEmpty() {
		OrderList l = new OrderList();
		assertNull(l.getLastProperty());
	}
	
	public void testFirstReverse() {
		OrderList l = new OrderList();
		l.add("property1", false);
		l.add("property2", true);
		assertEquals(false, l.isFirstReverse());
	}
	
	public void testLastReverse() {
		OrderList l = new OrderList();
		l.add("property1", false);
		l.add("property2", true);
		assertEquals(true, l.isLastReverse());
	}
	
	public void testFirstReverseWhenEmpty() {
		OrderList l = new OrderList();
		assertEquals(false, l.isFirstReverse());
	}
	
	public void testLastReverseWhenEmpty() {
		OrderList l = new OrderList();
		assertEquals(false, l.isLastReverse());
	}
	
	public void testIsEmpty() {
		OrderList l = new OrderList();
		assertTrue(l.isEmpty());
		l.add("property1", false);
		assertFalse(l.isEmpty());
	}
	
}

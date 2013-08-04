package it.amattioli.dominate.properties;

import java.util.ArrayList;

import junit.framework.TestCase;

public class CollectionChangeEventTest extends TestCase {

	public void testEquals() {
		ArrayList<String> coll = new ArrayList<String>();
		CollectionChangeEvent e1 = new CollectionChangeEvent(coll);
		CollectionChangeEvent e2 = new CollectionChangeEvent(coll);
		
		boolean areEqual = e1.equals(e2);
		
		assertTrue(areEqual);
	}
	
	public void testNotEquals() {
		ArrayList<String> coll1 = new ArrayList<String>();
		CollectionChangeEvent e1 = new CollectionChangeEvent(coll1);
		ArrayList<String> coll2 = new ArrayList<String>();
		CollectionChangeEvent e2 = new CollectionChangeEvent(coll2);
		
		boolean areEqual = e1.equals(e2);
		
		assertFalse(areEqual);
	}
	
	public void testNotEqualsNull() {
		ArrayList<String> coll = new ArrayList<String>();
		CollectionChangeEvent e1 = new CollectionChangeEvent(coll);
		
		boolean areEqual = e1.equals(null);
		
		assertFalse(areEqual);
	}
	
}

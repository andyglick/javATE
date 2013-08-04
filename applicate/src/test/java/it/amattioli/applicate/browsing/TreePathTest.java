package it.amattioli.applicate.browsing;

import java.util.Arrays;

import junit.framework.TestCase;

public class TreePathTest extends TestCase {

	public void testDepth() {
		TreePath p = new TreePath(1,3,5);
		assertEquals(3, p.depth());
	}
	
	public void testElementAt() {
		TreePath p = new TreePath(1,3,5);
		assertEquals(5, p.elementAt(2));
	}
	
	public void testAsIntArray() {
		TreePath p = new TreePath(1,3,5);
		assertTrue(Arrays.equals(new int[] {1,3,5}, p.asIntArray()));
	}
	
	public void testEquals() {
		assertEquals(new TreePath(1,3,5), new TreePath(1,3,5));
	}
	
	public void testFromString() {
		TreePath p = new TreePath(1,3,5);
		assertEquals(p, new TreePath(p.toString()));
	}
	
	public void testParentPath() {
		TreePath path = new TreePath(1,3,5);
		assertEquals(new TreePath(1,3), path.parentPath());
	}
	
}

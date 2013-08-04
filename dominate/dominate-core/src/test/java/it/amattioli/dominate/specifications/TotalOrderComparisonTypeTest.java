package it.amattioli.dominate.specifications;

import junit.framework.TestCase;
import static it.amattioli.dominate.specifications.TotalOrderComparisonType.*;

public class TotalOrderComparisonTypeTest extends TestCase {

	public void testEqual() {
		assertTrue(EQUAL.isSatisfiedBy(1L, 1L));
		assertFalse(EQUAL.isSatisfiedBy(1L, 2L));
		assertFalse(EQUAL.isSatisfiedBy(2L, 1L));
	}
	
	public void testNotEqual() {
		assertFalse(NOT_EQUAL.isSatisfiedBy(1L, 1L));
		assertTrue(NOT_EQUAL.isSatisfiedBy(1L, 2L));
		assertTrue(NOT_EQUAL.isSatisfiedBy(2L, 1L));
	}
	
	public void testGreater() {
		assertFalse(GREATER.isSatisfiedBy(1L, 1L));
		assertFalse(GREATER.isSatisfiedBy(1L, 2L));
		assertTrue(GREATER.isSatisfiedBy(2L, 1L));
	}
	
	public void testGreaterEq() {
		assertTrue(GREATER_EQ.isSatisfiedBy(1L, 1L));
		assertFalse(GREATER_EQ.isSatisfiedBy(1L, 2L));
		assertTrue(GREATER_EQ.isSatisfiedBy(2L, 1L));
	}
	
	public void testLower() {
		assertFalse(LOWER.isSatisfiedBy(1L, 1L));
		assertTrue(LOWER.isSatisfiedBy(1L, 2L));
		assertFalse(LOWER.isSatisfiedBy(2L, 1L));
	}
	
	public void testLowerEq() {
		assertTrue(LOWER_EQ.isSatisfiedBy(1L, 1L));
		assertTrue(LOWER_EQ.isSatisfiedBy(1L, 2L));
		assertFalse(LOWER_EQ.isSatisfiedBy(2L, 1L));
	}
	
}

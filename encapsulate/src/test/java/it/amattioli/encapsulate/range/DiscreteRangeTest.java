package it.amattioli.encapsulate.range;

import it.amattioli.encapsulate.dates.Day;

import java.util.*;

import junit.framework.TestCase;

public class DiscreteRangeTest extends TestCase {
  static final Day UPPER_LIMIT = new Day(30,Calendar.APRIL,2000);
  static final Day LOWER_LIMIT = new Day(1,Calendar.APRIL,2000);
  
  static final Day SMALLEST = new Day(1,Calendar.JANUARY,2000);
  static final Day SMALLER = new Day(31,Calendar.MARCH,2000);
  static final Day BETWEEN = new Day(15,Calendar.APRIL,2000);
  static final Day BIGGER = new Day(1,Calendar.MAY,2000);
  static final Day BIGGEST = new Day(1,Calendar.AUGUST,2000);
  
  static final Range<Day> range = new GenericDiscreteRange<Day>(LOWER_LIMIT, UPPER_LIMIT);
  static final Range<Day> lbRange = new GenericDiscreteRange<Day>(LOWER_LIMIT, null);
  static final Range<Day> hbRange = new GenericDiscreteRange<Day>(null, UPPER_LIMIT);
	
  public void setUp() {
  }
  
  public void testIncludes() {
    assertTrue(range.includes(BETWEEN));
    assertFalse(range.includes(BIGGER));
    assertFalse(range.includes(SMALLER));
    assertTrue(range.includes(LOWER_LIMIT));
    assertTrue(range.includes(UPPER_LIMIT));
  }
    
  public void testOverlaps() {
    assertFalse(range.overlaps(new GenericDiscreteRange<Day>(SMALLEST,SMALLER)));
    assertTrue(range.overlaps(new GenericDiscreteRange<Day>(SMALLEST,LOWER_LIMIT)));
    assertTrue(range.overlaps(new GenericDiscreteRange<Day>(SMALLEST,BETWEEN)));
    assertTrue(range.overlaps(new GenericDiscreteRange<Day>(SMALLEST,UPPER_LIMIT)));
    assertTrue(range.overlaps(new GenericDiscreteRange<Day>(SMALLEST,BIGGER)));
    
    assertTrue(range.overlaps(new GenericDiscreteRange<Day>(LOWER_LIMIT,BETWEEN)));
    assertTrue(range.overlaps(new GenericDiscreteRange<Day>(LOWER_LIMIT,UPPER_LIMIT)));
    assertTrue(range.overlaps(new GenericDiscreteRange<Day>(LOWER_LIMIT,BIGGER)));
    
    assertTrue(range.overlaps(new GenericDiscreteRange<Day>(BETWEEN,UPPER_LIMIT)));
    assertTrue(range.overlaps(new GenericDiscreteRange<Day>(BETWEEN,BIGGER)));
    
    assertTrue(range.overlaps(new GenericDiscreteRange<Day>(UPPER_LIMIT,BIGGER)));
    
    assertFalse(range.overlaps(new GenericDiscreteRange<Day>(BIGGER,BIGGEST)));
    
    assertTrue(range.overlaps(new GenericDiscreteRange<Day>(null,null)));
    assertFalse(range.overlaps(new GenericDiscreteRange<Day>(null,SMALLER)));
    assertTrue(range.overlaps(new GenericDiscreteRange<Day>(null,BETWEEN)));
    assertTrue(range.overlaps(new GenericDiscreteRange<Day>(null,BIGGER)));
    assertFalse(range.overlaps(new GenericDiscreteRange<Day>(BIGGER,null)));
    assertTrue(range.overlaps(new GenericDiscreteRange<Day>(BETWEEN,null)));
    assertTrue(range.overlaps(new GenericDiscreteRange<Day>(SMALLER,null)));
  }
  
  public void testEquality() {
    Range<Day> r2 = new GenericDiscreteRange<Day>(LOWER_LIMIT, UPPER_LIMIT);
    assertEquals(range,r2);
  }

  public void testInequality() {
    Range<Day> r2 = new GenericDiscreteRange<Day>(LOWER_LIMIT, BIGGEST);
    assertTrue(range.hasSameLow(r2));
    assertFalse(range.hasSameHigh(r2));
    assertFalse(range.equals(r2));
    assertFalse(range.equals(null));
  }
  
  public void testBounding() {
    assertTrue(range.isLowBounded());
    assertTrue(range.isHighBounded());
    assertFalse(lbRange.isHighBounded());
    assertTrue(lbRange.isLowBounded());
    assertFalse(hbRange.isLowBounded());
    assertTrue(hbRange.isHighBounded());
  }
  
  public void testUnboundedRange() {
    assertTrue(lbRange.includes(BETWEEN));
    assertTrue(hbRange.includes(BETWEEN));
    assertTrue(lbRange.overlaps(hbRange));
    assertTrue(hbRange.overlaps(lbRange));
  }
  
  public void testMerge() {
    Range<Day> r1 = new GenericDiscreteRange<Day>(BETWEEN, BIGGER);
    Range<Day> r2 = range.mergeWith(r1);
    assertEquals(LOWER_LIMIT,r2.getLow());
    assertEquals(BIGGER,r2.getHigh());
  }
  
  public void testMergeLowerUnbounded() {
    Range<Day> r1 = new GenericDiscreteRange<Day>(null, BETWEEN);
    Range<Day> r2 = range.mergeWith(r1);
    assertFalse(r2.isLowBounded());
    assertEquals(UPPER_LIMIT, r2.getHigh());
    Range<Day> r3 = r1.mergeWith(range);
    assertFalse(r3.isLowBounded());
    assertEquals(UPPER_LIMIT, r3.getHigh());
  }
	  
  public void testMergeUpperUnbounded() {
    Range<Day> r1 = new GenericDiscreteRange<Day>(BETWEEN, null);
    Range<Day> r2 = range.mergeWith(r1);
    assertFalse(r2.isHighBounded());
    assertEquals(LOWER_LIMIT, r2.getLow());
    Range<Day> r3 = r1.mergeWith(range);
    assertFalse(r3.isHighBounded());
    assertEquals(LOWER_LIMIT, r3.getLow());
  }
  
  public void testAbut() {
    Range<Day> r1 = new GenericDiscreteRange<Day>(SMALLEST,SMALLER);
    Range<Day> r2 = new GenericDiscreteRange<Day>(BIGGER,BIGGEST);
    assertTrue(range.abutOn(r1));
    assertTrue(lbRange.abutOn(r1));
    assertTrue(range.abutOn(r2));
    assertTrue(hbRange.abutOn(r2));
    assertFalse(r1.abutOn(r2));
    assertFalse(lbRange.abutOn(r2));
    assertFalse(hbRange.abutOn(r1));
  }
  
  public void testGap() {
    Range<Day> r1 = new GenericDiscreteRange<Day>(SMALLEST,SMALLER);
    Range<Day> r2 = new GenericDiscreteRange<Day>(BIGGER,BIGGEST);
    Range<Day> gap = r1.gap(r2);
    assertEquals(range,gap);
    Range<Day> gap2 = r2.gap(r1);
    assertEquals(range,gap2);
  }
  
  public void testGapFromUnbounded() {
    Range<Day> r1 = new GenericDiscreteRange<Day>(BIGGER, null);
    Range<Day> r2 = new GenericDiscreteRange<Day>(null, SMALLER);
    Range<Day> gap = r1.gap(r2);
    assertEquals(range,gap);
    Range<Day> gap2 = r2.gap(r1);
    assertEquals(range,gap2);
  }
  
  public void testIntersect() {
	  Range<Day> r1 = new GenericDiscreteRange<Day>(SMALLER,UPPER_LIMIT);
	  Range<Day> r2 = new GenericDiscreteRange<Day>(LOWER_LIMIT,BIGGER);
	  Range<Day> intersect1 = r1.intersect(r2);
	  assertEquals(range, intersect1);
	  Range<Day> intersect2 = r2.intersect(r1);
	  assertEquals(range, intersect2);
  }
  
  public void testIntersectUnbound() {
	  Range<Day> r1 = new GenericDiscreteRange<Day>(null,UPPER_LIMIT);
	  Range<Day> r2 = new GenericDiscreteRange<Day>(LOWER_LIMIT,null);
	  Range<Day> intersect1 = r1.intersect(r2);
	  assertEquals(range, intersect1);
	  Range<Day> intersect2 = r2.intersect(r1);
	  assertEquals(range, intersect2);
  }
  
  public void testNoIntersect() {
	Range<Day> r1 = new GenericDiscreteRange<Day>(SMALLEST,LOWER_LIMIT);
	Range<Day> r2 = new GenericDiscreteRange<Day>(UPPER_LIMIT,BIGGEST);
	assertNull(r1.intersect(r2));  
	assertNull(r2.intersect(r1));
  }
  
  public void testMinus() {
	  Range<Day> r1 = new GenericDiscreteRange<Day>(SMALLER,UPPER_LIMIT);
	  Range<Day> r2 = new GenericDiscreteRange<Day>(LOWER_LIMIT,BIGGER);
	  Set<Range<Day>> minus1 = r1.minus(r2);
	  assertEquals(1, minus1.size());
	  assertTrue(minus1.contains(new GenericDiscreteRange<Day>(SMALLER,LOWER_LIMIT.previous())));
	  Set<Range<Day>> minus2 = r2.minus(r1);
	  assertEquals(1, minus2.size());
	  assertTrue(minus2.contains(new GenericDiscreteRange<Day>(UPPER_LIMIT.next(),BIGGER)));
  }
  
  public void testMinusContained() {
	  Range<Day> r1 = new GenericDiscreteRange<Day>(SMALLER,BIGGER);
	  Range<Day> r2 = new GenericDiscreteRange<Day>(LOWER_LIMIT,UPPER_LIMIT);
	  Set<Range<Day>> minus1 = r1.minus(r2);
	  assertEquals(2, minus1.size());
	  assertTrue(minus1.contains(new GenericDiscreteRange<Day>(SMALLER,LOWER_LIMIT.previous())));
	  assertTrue(minus1.contains(new GenericDiscreteRange<Day>(UPPER_LIMIT.next(),BIGGER)));
  }
  
  public void testMinusNotContained() {
	  Range<Day> r1 = new GenericDiscreteRange<Day>(SMALLER,BIGGER);
	  Range<Day> r2 = new GenericDiscreteRange<Day>(LOWER_LIMIT,UPPER_LIMIT);
	  Set<Range<Day>> minus1 = r2.minus(r1);
	  assertTrue(minus1.isEmpty());
  }
  
  public void testMinusUnbound() {
	  Range<Day> r1 = new GenericDiscreteRange<Day>(null,UPPER_LIMIT);
	  Range<Day> r2 = new GenericDiscreteRange<Day>(LOWER_LIMIT,null);
	  Set<Range<Day>> minus1 = r1.minus(r2);
	  assertEquals(1, minus1.size());
	  assertTrue(minus1.contains(new GenericDiscreteRange<Day>(null,LOWER_LIMIT.previous())));
	  Set<Range<Day>> minus2 = r2.minus(r1);
	  assertEquals(1, minus2.size());
	  assertTrue(minus2.contains(new GenericDiscreteRange<Day>(UPPER_LIMIT.next(),null)));
  }

  public void tearDown() {
  }
}

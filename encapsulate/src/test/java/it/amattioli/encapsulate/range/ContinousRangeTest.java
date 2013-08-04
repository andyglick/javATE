package it.amattioli.encapsulate.range;
 
import java.util.Set;

import junit.framework.TestCase;

public class ContinousRangeTest extends TestCase {
  static final Integer UPPER_LIMIT = new Integer(100);
  static final Integer LOWER_LIMIT = new Integer(10);
  
  static final Integer SMALLEST = new Integer(0);
  static final Integer SMALLER = new Integer(1);
  static final Integer BETWEEN = new Integer(50);
  static final Integer BIGGER = new Integer(1000);
  static final Integer BIGGEST = new Integer(1001);
  
  static final Range<Integer> range = new GenericContinousRange<Integer>(LOWER_LIMIT, UPPER_LIMIT);
  static final Range<Integer> lbRange = new GenericContinousRange<Integer>(LOWER_LIMIT, null);
  static final Range<Integer> hbRange = new GenericContinousRange<Integer>(null, UPPER_LIMIT);
	
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
    assertFalse(range.overlaps(new GenericContinousRange<Integer>(SMALLEST,SMALLER)));
    assertTrue(range.overlaps(new GenericContinousRange<Integer>(SMALLEST,LOWER_LIMIT)));
    assertTrue(range.overlaps(new GenericContinousRange<Integer>(SMALLEST,BETWEEN)));
    assertTrue(range.overlaps(new GenericContinousRange<Integer>(SMALLEST,UPPER_LIMIT)));
    assertTrue(range.overlaps(new GenericContinousRange<Integer>(SMALLEST,BIGGER)));
    
    assertTrue(range.overlaps(new GenericContinousRange<Integer>(LOWER_LIMIT,BETWEEN)));
    assertTrue(range.overlaps(new GenericContinousRange<Integer>(LOWER_LIMIT,UPPER_LIMIT)));
    assertTrue(range.overlaps(new GenericContinousRange<Integer>(LOWER_LIMIT,BIGGER)));
    
    assertTrue(range.overlaps(new GenericContinousRange<Integer>(BETWEEN,UPPER_LIMIT)));
    assertTrue(range.overlaps(new GenericContinousRange<Integer>(BETWEEN,BIGGER)));
    
    assertTrue(range.overlaps(new GenericContinousRange<Integer>(UPPER_LIMIT,BIGGER)));
    
    assertFalse(range.overlaps(new GenericContinousRange<Integer>(BIGGER,BIGGEST)));
    
    assertTrue(range.overlaps(new GenericContinousRange<Integer>(null,null)));
    assertFalse(range.overlaps(new GenericContinousRange<Integer>(null,SMALLER)));
    assertTrue(range.overlaps(new GenericContinousRange<Integer>(null,BETWEEN)));
    assertTrue(range.overlaps(new GenericContinousRange<Integer>(null,BIGGER)));
    assertFalse(range.overlaps(new GenericContinousRange<Integer>(BIGGER,null)));
    assertTrue(range.overlaps(new GenericContinousRange<Integer>(BETWEEN,null)));
    assertTrue(range.overlaps(new GenericContinousRange<Integer>(SMALLER,null)));
  }
  
  public void testEquality() {
    Range<Integer> r2 = new GenericContinousRange<Integer>(LOWER_LIMIT, UPPER_LIMIT);
    assertEquals(range,r2);
    assertEquals(r2,range);
    assertEquals(range,range);
  }

  public void testInequality() {
    Range<Integer> r2 = new GenericContinousRange<Integer>(LOWER_LIMIT, BIGGEST);
    assertTrue(range.hasSameLow(r2));
    assertFalse(range.hasSameHigh(r2));
    assertFalse(range.hasSameLow(hbRange));
    assertFalse(range.hasSameHigh(lbRange));
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
    assertFalse(lbRange.equals(hbRange));
    assertFalse(hbRange.equals(lbRange));
  }
  
  public void testMerge() {
    Range<Integer> r1 = new GenericContinousRange<Integer>(BETWEEN, BIGGER);
    Range<Integer> r2 = range.mergeWith(r1);
    assertEquals(LOWER_LIMIT,r2.getLow());
    assertEquals(BIGGER,r2.getHigh());
    Range<Integer> r3 = r1.mergeWith(range);
    assertEquals(LOWER_LIMIT,r3.getLow());
    assertEquals(BIGGER,r3.getHigh());
  }
  
  public void testMergeLowerUnbounded() {
    Range<Integer> r1 = new GenericContinousRange<Integer>(null, BETWEEN);
    Range<Integer> r2 = range.mergeWith(r1);
    assertFalse(r2.isLowBounded());
    assertEquals(UPPER_LIMIT, r2.getHigh());
    Range<Integer> r3 = r1.mergeWith(range);
    assertFalse(r3.isLowBounded());
    assertEquals(UPPER_LIMIT, r3.getHigh());
  }
  
  public void testMergeUpperUnbounded() {
    Range<Integer> r1 = new GenericContinousRange<Integer>(BETWEEN, null);
    Range<Integer> r2 = range.mergeWith(r1);
    assertFalse(r2.isHighBounded());
    assertEquals(LOWER_LIMIT, r2.getLow());
    Range<Integer> r3 = r1.mergeWith(range);
    assertFalse(r3.isHighBounded());
    assertEquals(LOWER_LIMIT, r3.getLow());
  }
  
  public void testAbut() {
    Range<Integer> r1 = new GenericContinousRange<Integer>(SMALLER,LOWER_LIMIT);
    Range<Integer> r2 = new GenericContinousRange<Integer>(UPPER_LIMIT,BIGGER);
    assertTrue(range.abutOn(r1));
    assertTrue(lbRange.abutOn(r1));
    assertTrue(range.abutOn(r2));
    assertTrue(hbRange.abutOn(r2));
    assertFalse(r1.abutOn(r2));
    assertFalse(lbRange.abutOn(r2));
    assertFalse(hbRange.abutOn(r1));
  }
  
  public void testGap() {
    Range<Integer> r1 = new GenericContinousRange<Integer>(SMALLER,LOWER_LIMIT);
    Range<Integer> r2 = new GenericContinousRange<Integer>(UPPER_LIMIT,BIGGER);
    Range<Integer> gap1 = r1.gap(r2);
    assertEquals(range,gap1);
    Range<Integer> gap2 = r2.gap(r1);
    assertEquals(range,gap2);
  }
  
  public void testGapFromUnbounded() {
    Range<Integer> r1 = new GenericContinousRange<Integer>(UPPER_LIMIT, null);
    Range<Integer> r2 = new GenericContinousRange<Integer>(null, LOWER_LIMIT);
    Range<Integer> gap1 = r1.gap(r2);
    assertEquals(range,gap1);
    Range<Integer> gap2 = r2.gap(r1);
    assertEquals(range,gap2);
  }
  
  public void testNoGap() {
    Range<Integer> r1 = new GenericContinousRange<Integer>(SMALLEST,BIGGER);
    Range<Integer> r2 = new GenericContinousRange<Integer>(SMALLER,BIGGEST);
    assertNull(r1.gap(r2));  
  }
  
  public void testIntersect() {
	  Range<Integer> r1 = new GenericContinousRange<Integer>(SMALLER,UPPER_LIMIT);
	  Range<Integer> r2 = new GenericContinousRange<Integer>(LOWER_LIMIT,BIGGER);
	  Range<Integer> intersect1 = r1.intersect(r2);
	  assertEquals(range, intersect1);
	  Range<Integer> intersect2 = r2.intersect(r1);
	  assertEquals(range, intersect2);
  }
  
  public void testIntersectUnbound() {
	  Range<Integer> r1 = new GenericContinousRange<Integer>(null,UPPER_LIMIT);
	  Range<Integer> r2 = new GenericContinousRange<Integer>(LOWER_LIMIT,null);
	  Range<Integer> intersect1 = r1.intersect(r2);
	  assertEquals(range, intersect1);
	  Range<Integer> intersect2 = r2.intersect(r1);
	  assertEquals(range, intersect2);
  }
  
  public void testNoIntersect() {
	Range<Integer> r1 = new GenericContinousRange<Integer>(SMALLEST,LOWER_LIMIT);
	Range<Integer> r2 = new GenericContinousRange<Integer>(UPPER_LIMIT,BIGGEST);
	assertNull(r1.intersect(r2));  
	assertNull(r2.intersect(r1));
  }
  
  public void testMinus() {
	  Range<Integer> r1 = new GenericContinousRange<Integer>(SMALLER,UPPER_LIMIT);
	  Range<Integer> r2 = new GenericContinousRange<Integer>(LOWER_LIMIT,BIGGER);
	  Set<Range<Integer>> minus1 = r1.minus(r2);
	  assertEquals(1, minus1.size());
	  assertTrue(minus1.contains(new GenericContinousRange<Integer>(SMALLER,LOWER_LIMIT)));
	  Set<Range<Integer>> minus2 = r2.minus(r1);
	  assertEquals(1, minus2.size());
	  assertTrue(minus2.contains(new GenericContinousRange<Integer>(UPPER_LIMIT,BIGGER)));
  }
  
  public void testMinusContained() {
	  Range<Integer> r1 = new GenericContinousRange<Integer>(SMALLER,BIGGER);
	  Range<Integer> r2 = new GenericContinousRange<Integer>(LOWER_LIMIT,UPPER_LIMIT);
	  Set<Range<Integer>> minus1 = r1.minus(r2);
	  assertEquals(2, minus1.size());
	  assertTrue(minus1.contains(new GenericContinousRange<Integer>(SMALLER,LOWER_LIMIT)));
	  assertTrue(minus1.contains(new GenericContinousRange<Integer>(UPPER_LIMIT,BIGGER)));
  }
  
  public void testMinusNotContained() {
	  Range<Integer> r1 = new GenericContinousRange<Integer>(SMALLER,BIGGER);
	  Range<Integer> r2 = new GenericContinousRange<Integer>(LOWER_LIMIT,UPPER_LIMIT);
	  Set<Range<Integer>> minus1 = r2.minus(r1);
	  assertTrue(minus1.isEmpty());
  }
  
  public void testMinusUnbound() {
	  Range<Integer> r1 = new GenericContinousRange<Integer>(null,UPPER_LIMIT);
	  Range<Integer> r2 = new GenericContinousRange<Integer>(LOWER_LIMIT,null);
	  Set<Range<Integer>> minus1 = r1.minus(r2);
	  assertEquals(1, minus1.size());
	  assertTrue(minus1.contains(new GenericContinousRange<Integer>(null,LOWER_LIMIT)));
	  Set<Range<Integer>> minus2 = r2.minus(r1);
	  assertEquals(1, minus2.size());
	  assertTrue(minus2.contains(new GenericContinousRange<Integer>(UPPER_LIMIT,null)));
  }
  
  public void tearDown() {
  }
}

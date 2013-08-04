package it.amattioli.encapsulate.dates;

import java.util.*;
import java.text.*;

import junit.framework.TestCase;


public class DayTest extends TestCase {
  public static Day prec = new Day(31,Calendar.DECEMBER,2003);
  public static Day day = new Day(1,Calendar.JANUARY,2004);
  public static Day succ = new Day(2,Calendar.JANUARY,2004);
  public static DateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss"); 
  
  public void testDayOfMonth() {
    assertEquals(1,day.getDayOfMonth());
  }
  
  public void testMonth() {
    assertEquals(Calendar.JANUARY,day.getMonth());
  }
  
  public void testYear() {
    assertEquals(2004,day.getYear());
  }
  
  public void testPrecDay() {
    assertEquals(prec,day.previous());
  }
  
  public void testSuccDay() {
    assertEquals(succ,day.next());
  }
  
  public void testIncludes() throws Exception {
    Date inner = format.parse("01/01/2004 15:30:00");
    Date after = format.parse("02/01/2004 01:30:00");
    Date before = format.parse("31/12/2003 23:30:00");
    assertTrue(day.includes(inner));
    assertFalse(day.includes(after));
    assertFalse(day.includes(before));
  }
  
  public void testBefore() {
    assertTrue(day.before(succ));
    assertFalse(day.before(prec));    
  }
  
  public void testBeforeDate() throws Exception {
    Date inner = format.parse("01/01/2004 15:30:00");
    Date after = format.parse("02/01/2004 01:30:00");
    Date before = format.parse("31/12/2003 23:30:00");
    assertTrue(day.before(after));
    assertFalse(day.before(inner));
    assertFalse(day.before(before));
  }
  
  public void testAfter() {
    assertTrue(day.after(prec));
    assertFalse(day.after(succ));
  }
  
  public void testAfterDate() throws Exception {
    Date inner = format.parse("01/01/2004 15:30:00");
    Date after = format.parse("02/01/2004 01:30:00");
    Date before = format.parse("31/12/2003 23:30:00");
    assertTrue(day.after(before));
    assertFalse(day.after(inner));
    assertFalse(day.after(after));
  }
  
  public void testHighDay() throws Exception {
    assertEquals(day,day.getHighDay());
  }
  
  public void testLowDay() throws Exception {
    assertEquals(day,day.getLowDay());
  }
  
}

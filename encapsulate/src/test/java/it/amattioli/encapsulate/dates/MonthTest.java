package it.amattioli.encapsulate.dates;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;

import junit.framework.TestCase;

/**
 * @author a.mattioli
 *
 * Creato il Jan 28, 2005 alle 9:44:39 PM
 */
public class MonthTest extends TestCase {
  private Locale defaultLocale;
  
  @Override
  public void setUp() {
	defaultLocale = Locale.getDefault();
	Locale.setDefault(Locale.ITALY);  
  }
  
  @Override
  public void tearDown() {
	Locale.setDefault(defaultLocale);  
  }
	
  public void testNewMonth() {
    Month m = new Month(5,2004);
    assertEquals(5,m.getMonth());
    assertEquals(2004,m.getYear());
  }
  
  public void testNewMonthFromDate() {
	Calendar cal = new GregorianCalendar(2004,Calendar.MAY,5);
    Month m = new Month(cal.getTime());
    assertEquals(Calendar.MAY,m.getMonth());
    assertEquals(2004,m.getYear());
  }
  
  public void testNextMonth() {
    Month m = new Month(5,2004);
    assertEquals(new Month(6,2004),m.next());
  }
  
  public void testPreviousMonth() {
    Month m = new Month(5,2004);
    assertEquals(new Month(4,2004),m.previous());
  }
  
  public void testClone() {
    Month m = new Month(5,2004);
    assertEquals(m,m.clone());
  }
  
  public void testEquals() {
    Month m1 = new Month(4,2004);
    Month m2 = new Month(5,2004);
    assertTrue(m1.equals(m1));
    assertFalse(m1.equals(m2));
  }
  
  public void testCompare() {
    Month m1 = new Month(4,2004);
    Month m2 = new Month(5,2004);
    assertEquals(-1,m1.compareTo(m2));
    assertEquals(1,m2.compareTo(m1));
    assertEquals(0,m1.compareTo(m1));
  }
  
  public void testBefore() {
    Month m1 = new Month(4,2004);
    Month m2 = new Month(5,2004);
    assertTrue(m1.before(m2));
    assertFalse(m2.before(m1));
  }
  
  public void testAfter() {
    Month m1 = new Month(4,2004);
    Month m2 = new Month(5,2004);
    assertTrue(m2.after(m1));
    assertFalse(m1.after(m2));
  }
  
  public void testStartDay() {
    Month m1 = new Month(4,2004);
    assertEquals(new Day(1,4,2004),m1.getStartDay());
  }
  
  public void testEndDay() {
    Month m1 = new Month(1,2004);
    assertEquals(new Day(29,1,2004),m1.getEndDay());
  }
  
  public void testFirstWeek() {
    Month m1 = new Month(Calendar.JANUARY,2004);
	assertEquals(new Day(29,Calendar.DECEMBER,2003), m1.getFirstWeek().getStartDay());  
  }
  
  public void testLastWeek() {
    Month m1 = new Month(Calendar.JANUARY,2004);
	assertEquals(new Day(01,Calendar.FEBRUARY,2004), m1.getLastWeek().getEndDay());  
  }
  
}

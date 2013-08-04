package it.amattioli.encapsulate.dates;

import java.util.Calendar;
import java.util.Date;
import java.text.SimpleDateFormat;

import junit.framework.TestCase;

public class PhysicalDurationTest extends TestCase {
  static SimpleDateFormat dayFormat = new SimpleDateFormat("dd/MM/yyyy");
  static SimpleDateFormat timeFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
  Date begin;
  Date end;
  
  public void testNullBegin() {
    try {
      end = timeFormat.parse("01/01/2003 10:00:02");
    } catch(Exception e) {}
    try {
    	new PhysicalDuration(null, end);
    	fail();
    } catch(NullPointerException e) {
    	assertEquals("Non e` possibile costruire una durata con istante iniziale null", e.getMessage());
    }
  }
  
  public void testNullDayBegin() {
    try {
      new PhysicalDuration(null, new Day(01, Calendar.JANUARY, 2003));
      fail();
    } catch(NullPointerException e) {
      assertEquals("Non e` possibile costruire una durata con istante iniziale null", e.getMessage());
    }
  }
  
  public void testNullEnd() {
    try {
      begin = timeFormat.parse("01/01/2003 10:00:00");
    } catch(Exception e) {}
    try {
    	new PhysicalDuration(begin, null);
    	fail();
    } catch(NullPointerException e) {
    	assertEquals("Non e` possibile costruire una durata con istante finale null", e.getMessage());
    }
  }
  
  public void testNullDayEnd() {
    try {
      new PhysicalDuration(new Day(01, Calendar.JANUARY, 2003), null);
      fail();
    } catch(NullPointerException e) {
      assertEquals("Non e` possibile costruire una durata con istante finale null", e.getMessage());
    }
  }
  
  public void testMilliseconds() {
    try {
      begin = timeFormat.parse("01/01/2003 10:00:00");
      end = timeFormat.parse("01/01/2003 10:00:02");
    } catch(Exception e) {}
    PhysicalDuration d = new PhysicalDuration(begin,end);
    assertEquals(d.inMilliseconds(),2000);
  }
  
  public void testSeconds() {
    try {
      begin = timeFormat.parse("01/01/2003 10:00:00");
      end = timeFormat.parse("01/01/2003 10:00:02");
    } catch(Exception e) {}
    PhysicalDuration d = new PhysicalDuration(begin,end);
    assertEquals(d.inSeconds(),2);
  }
  
  public void testMinutes() {
    try {
      begin = timeFormat.parse("01/01/2003 10:00:00");
      end = timeFormat.parse("01/01/2003 10:05:00");
    } catch(Exception e) {}
    PhysicalDuration d = new PhysicalDuration(begin,end);
    assertEquals(d.inMinutes(),5);
  }
  
  public void testHours() {
    try {
      begin = timeFormat.parse("01/01/2003 10:00:00");
      end = timeFormat.parse("01/01/2003 11:00:00");
    } catch(Exception e) {}
    PhysicalDuration d = new PhysicalDuration(begin,end);
    assertEquals(d.inHours(),1);
  }
  
  public void testDays() {
    try {
      begin = timeFormat.parse("01/01/2003 10:00:00");
      end = timeFormat.parse("03/01/2003 11:00:00");
    } catch(Exception e) {}
    PhysicalDuration d = new PhysicalDuration(begin,end);
    assertEquals(d.inDays(),2);
  }
  
  public void testRemainingSeconds() {
	try {
	  begin = timeFormat.parse("01/01/2003 10:00:00");
	  end = timeFormat.parse("03/01/2003 12:53:15");
    } catch(Exception e) {}
    PhysicalDuration d = new PhysicalDuration(begin,end);
    assertEquals(15,d.getRemainingSeconds());
  }
  
  public void testRemainingMinutes() {
	try {
	  begin = timeFormat.parse("01/01/2003 10:00:00");
	  end = timeFormat.parse("03/01/2003 12:53:00");
	} catch(Exception e) {}
	PhysicalDuration d = new PhysicalDuration(begin,end);
	assertEquals(53,d.getRemainingMinutes());
  }
  
  public void testRemainingHours() {
	try {
	  begin = timeFormat.parse("01/01/2003 10:00:00");
	  end = timeFormat.parse("03/01/2003 12:13:00");
	} catch(Exception e) {}
	PhysicalDuration d = new PhysicalDuration(begin,end);
	long result = d.getRemainingHours();
	assertEquals(2,result);
  }
  
  public void testEquals() {
    try {
      begin = timeFormat.parse("01/01/2003 10:00:00");
      end = timeFormat.parse("03/01/2003 11:00:00");
    } catch(Exception e) {}
    PhysicalDuration d1 = new PhysicalDuration(begin,end);
    PhysicalDuration d2 = new PhysicalDuration(begin,end);
    assertEquals(true,d1.equals(d2));
    assertEquals(true,d2.equals(d1));
    assertEquals(true,d1.equals(d1));
  }
  
  public void testNotEquals() {
    Date begin1 = null;
    Date end1 = null;
    Date begin2 = null;
    Date end2 = null;
    try {
      begin1 = timeFormat.parse("01/01/2003 10:00:00");
      end1 = timeFormat.parse("03/01/2003 11:00:00");
      begin2 = timeFormat.parse("01/01/2003 10:05:00");
      end2 = timeFormat.parse("03/01/2003 09:00:00");
    } catch(Exception e) {}
    PhysicalDuration d1 = new PhysicalDuration(begin1,end1);
    PhysicalDuration d2 = new PhysicalDuration(begin2,end2);
    assertEquals(false,d1.equals(d2));
  }
  
  public void testNullEquals() {
    try {
      begin = timeFormat.parse("01/01/2003 10:00:00");
      end = timeFormat.parse("03/01/2003 11:00:00");
    } catch(Exception e) {}
    PhysicalDuration d1 = new PhysicalDuration(begin,end);
    assertEquals(false,d1.equals(null));
  }
  
  public void testIncompatibleEquals() {
    try {
      begin = timeFormat.parse("01/01/2003 10:00:00");
      end = timeFormat.parse("03/01/2003 11:00:00");
    } catch(Exception e) {}
    PhysicalDuration d1 = new PhysicalDuration(begin,end);
    assertEquals(false,d1.equals(""));
  }
  
  public void testBefore() throws Exception {
	  Date start = timeFormat.parse("03/01/2003 11:00:00");
	  Date expected = timeFormat.parse("02/01/2003 11:00:00");
	  Date result = PhysicalDuration.ONE_DAY.before(start);
	  assertEquals(expected,result);
  }
  
  public void testAfter() throws Exception {
	  Date start = timeFormat.parse("03/01/2003 11:00:00");
	  Date expected = timeFormat.parse("04/01/2003 11:00:00");
	  Date result = PhysicalDuration.ONE_DAY.after(start);
	  assertEquals(expected,result);
  }
}

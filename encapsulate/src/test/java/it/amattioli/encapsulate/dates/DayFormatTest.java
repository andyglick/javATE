package it.amattioli.encapsulate.dates;

import java.text.ParseException;
import java.util.Calendar;

import junit.framework.TestCase;

/**
 * 
 * @author a.mattioli
 */
public class DayFormatTest extends TestCase {
  
  public void testFormat() {
    DayFormat format = DayFormat.getInstance("dd/MM/yyyy");
    String result = format.format(new Day(1,Calendar.AUGUST,2003));
    assertEquals("01/08/2003",result);
  }
  
  public void testParse() throws Exception {
    DayFormat format = DayFormat.getInstance("dd/MM/yyyy");
    Day result = format.parse("01/08/2003");
    assertEquals(new Day(1,Calendar.AUGUST,2003),result);
  }
  
  public void testWrongParse() throws ParseException {
    DayFormat format = DayFormat.getInstance("dd/MM/yyyy");
    try {
    	format.parse("31/04/2003");
    	fail();
    } catch(ParseException e) {
    	
    }
  }

}

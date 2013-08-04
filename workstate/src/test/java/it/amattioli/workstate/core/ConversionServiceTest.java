package it.amattioli.workstate.core;
import it.amattioli.workstate.conversion.ConversionService;
import junit.framework.*;
import java.util.*;
import java.text.*;

public class ConversionServiceTest extends TestCase {
  private ConversionService service = new ConversionService();
  
  public void setUp() {
    service.registerFormat(Date.class,new SimpleDateFormat("dd/MM/yyyy"));
    service.registerFormat(Number.class,NumberFormat.getInstance());
  }
  
  public void testFormatDate() {
    Calendar cal = new GregorianCalendar(2004,Calendar.APRIL,1);
    String result = service.format(cal.getTime());
    assertEquals("01/04/2004",result);
  }
  
  public void testFormatInteger() {
    String result = service.format(new Integer(123));
    assertEquals("123",result);
  }
  
  public void testParseDate() throws Exception {
    Object result = service.parse("01/04/2004",Date.class);
    Calendar cal = new GregorianCalendar(2004,Calendar.APRIL,1);
    assertEquals(cal.getTime(),result);
  }
  
}

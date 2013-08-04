package it.amattioli.encapsulate.dates;

import java.util.Calendar;

public class RangeEachYear1MonthTest extends AbstractRangeEachYearTest {
  
  public void setUp() throws Exception {
	dateBelow = format.parse("01/01/2003");
    dateBelowLowLimit = format.parse("30/04/2003");
    dateLowLimit = format.parse("01/05/2003");
    dateBetweenLimits = format.parse("15/05/2003");
    dateHighLimit = format.parse("31/05/2003");
    dateAboveHighLimit = format.parse("01/06/2003");
    dateAbove = format.parse("31/12/2003");
    testExpression = new RangeEachYear(Calendar.MAY);
  }

  public void testInvalidMonth() {
	  try {
		  new RangeEachYear(-1);
		  fail();
	  } catch(IllegalArgumentException e) {
		  
	  }
  }

}
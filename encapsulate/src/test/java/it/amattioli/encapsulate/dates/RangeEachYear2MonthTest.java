package it.amattioli.encapsulate.dates;

import java.util.Calendar;

public class RangeEachYear2MonthTest extends AbstractRangeEachYearTest {

  public void setUp() throws Exception {
	dateBelow = format.parse("01/01/2003");
    dateBelowLowLimit = format.parse("30/04/2003");
    dateLowLimit = format.parse("01/05/2003");
    dateBetweenLimits = format.parse("15/06/2003");
    dateHighLimit = format.parse("31/08/2003");
    dateAboveHighLimit = format.parse("01/09/2003");
    dateAbove = format.parse("31/12/2003");
    testExpression = new RangeEachYear(Calendar.MAY, Calendar.AUGUST);
  }

  public void testInvalidFirstMonth() {
	  try {
		  new RangeEachYear(-1,Calendar.AUGUST);
	  } catch(IllegalArgumentException e) {
		  
	  }
  }

  public void testInvalidSecondMonth() {
	  try {
		  new RangeEachYear(Calendar.JANUARY,15);
	  } catch(IllegalArgumentException e) {
		  
	  }
  }

}
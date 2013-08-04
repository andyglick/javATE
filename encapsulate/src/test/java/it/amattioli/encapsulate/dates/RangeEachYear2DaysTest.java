package it.amattioli.encapsulate.dates;

import java.util.Calendar;

public class RangeEachYear2DaysTest extends AbstractRangeEachYearTest {

  public void setUp() throws Exception {
	dateBelow = format.parse("01/01/2003");
    dateBelowLowLimit = format.parse("14/05/2003");
    dateLowLimit = format.parse("15/05/2003");
    dateBetweenLimits = format.parse("30/05/2003");
    dateHighLimit = format.parse("10/06/2003");
    dateAboveHighLimit = format.parse("11/06/2003");
    dateAbove = format.parse("31/12/2003");
    testExpression = new RangeEachYear(Calendar.MAY,15,Calendar.JUNE,10);
  }

  public void testInvalidFirstDay() {
	  try {
		  new RangeEachYear(Calendar.FEBRUARY,30,Calendar.MARCH,15);
		  fail();
	  } catch(IllegalArgumentException e) {
		  
	  }
  }

  public void testInvalidSecondDay() {
	  try {
		  new RangeEachYear(Calendar.FEBRUARY,01,Calendar.MARCH,35);
	  } catch(IllegalArgumentException e) {
		  
	  }
  }

}
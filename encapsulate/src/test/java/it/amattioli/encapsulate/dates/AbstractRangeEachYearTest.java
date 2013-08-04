package it.amattioli.encapsulate.dates;

import java.util.Date;
import java.text.SimpleDateFormat;

import junit.framework.TestCase;


public abstract class AbstractRangeEachYearTest extends TestCase {
  static SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
  RangeEachYear testExpression;
  protected Date dateBelow;
  protected Date dateBelowLowLimit;
  protected Date dateLowLimit;
  protected Date dateBetweenLimits;
  protected Date dateHighLimit;
  protected Date dateAboveHighLimit;
  protected Date dateAbove;

  public void testNotIncludes() {
    assertFalse("Not includes dateBelow",testExpression.includes(dateBelow));
    assertFalse("Not includes dateBelowLowLimit",testExpression.includes(dateBelowLowLimit));
    assertFalse("Not includes dateAboveHighLimit",testExpression.includes(dateAboveHighLimit));
    assertFalse("Not includes dateAbove",testExpression.includes(dateAbove));
  }

  public void testIncludes() {
    assertTrue("Includes dateLowLimit",testExpression.includes(dateLowLimit));
    assertTrue("Includes dateBetweenLimits",testExpression.includes(dateBetweenLimits));
    assertTrue("Includes dateHighLimit",testExpression.includes(dateHighLimit));
  }
}
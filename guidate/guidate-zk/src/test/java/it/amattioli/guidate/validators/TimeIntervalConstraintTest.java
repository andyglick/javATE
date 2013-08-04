package it.amattioli.guidate.validators;

import java.util.Locale;

import org.zkoss.util.Locales;
import org.zkoss.zk.ui.WrongValueException;

import junit.framework.TestCase;

public class TimeIntervalConstraintTest extends TestCase {

	public void testWhenValid() {
		Locales.setThreadLocal(Locale.US);
		TimeIntervalConstraint c = new TimeIntervalConstraint();
		c.validate(null, "2010");
	}
	
	public void testWhenInvalid() {
		Locales.setThreadLocal(Locale.US);
		TimeIntervalConstraint c = new TimeIntervalConstraint();
		try {
			c.validate(null, "foo");
			fail("Should throw WrongValueException");
		} catch(WrongValueException e) {
			
		}
	}
	
}

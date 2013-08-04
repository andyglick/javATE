package it.amattioli.encapsulate.dates;

import java.util.Date;

/**
 * A TemporalExpression is a boolean expression that can be satisfied or not by a time point.
 * For example such an expression could check if a date is a holiday or not.
 * 
 */
public interface TemporalExpression {

	/**
	 * Verifies if a date satisfies this expression
	 * 
	 * @param d the date to be checked

	 * @return true if the date satisfies this expression, false otherwise
	 */
	public boolean includes(Date d);

}
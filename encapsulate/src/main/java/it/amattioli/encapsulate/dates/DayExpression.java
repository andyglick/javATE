package it.amattioli.encapsulate.dates;

/**
 * Similar to a {@link TemporalExpression} but checks object of {@link Day} class
 * instead of time points.
 * 
 * @see TemporalExpression
 */
public interface DayExpression {

	/**
	 * Verifies if a {@link Day} satisfies this expression
	 * 
	 * @param d the day to be checked
	 *
	 * @return true if the day satisfies this expression, false otherwise
	 */
	public boolean includes(Day d);

}

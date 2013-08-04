package it.amattioli.encapsulate.dates;

import it.amattioli.encapsulate.range.*;

import java.util.Date;

/**
 * A range extension that represents a time interval.
 * 
 */
public interface TimeInterval extends Range<Date> {

	/**
	 * Returns the duration of this time interval. This make sense only for
	 * a time interval that has both extremes, otherwise the duration is infinite. 
	 * 
	 * @return this time interval duration
	 * @throws UnboundedRangeException if this interval is not bounded
	 */
	public Duration getDuration();
	
	public PhysicalDuration getPhysicalDuration();

	/**
	 * Verifies that a time point is in this interval
	 * 
	 * @param d the time point to be checked
	 *            
	 * @return true if the parameter is inside this interval, false otherwise
	 */
	public boolean includes(Date d);

	/**
	 * Verifies if another time interval completely precedes this.
	 * 
	 * @param other the time interval to be checked
	 *            
	 * @return true if the passed time interval precedes this, otherwise false
	 * @throws NullPointerException if the parameter is null
	 */
	public boolean before(TimeInterval other);

	/**
	 * Verifies if another time interval completely follows this.
	 * 
	 * @param other the time interval to be checked
	 * 
	 * @return true if the passed time interval follows this, otherwise flase
	 * @throws NullPointerException if the parameter is null
	 */
	public boolean after(TimeInterval other);

	public Day getLowDay();

	public Day getHighDay();
}

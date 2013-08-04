package it.amattioli.encapsulate.dates;

import java.util.Date;

/**
 * A time interval duration. A duration is something that can be added or subtracted
 * from a point in time to obtain another point in time.
 * 
 */
public interface Duration {

	/**
	 * Returns the time point obtained summing this duration to the given time point
	 * 
	 * @param begin
	 * @return
	 */
	public Date after(Date begin);

	/**
	 * Returns the time point obtained subtracting this duration to the given time point
	 * 
	 * @param begin
	 * @return
	 */
	public Date before(Date begin);
	
	/**
	 * Add this duration to another to obtain another duration.
	 * 
	 * @param d
	 * @return
	 */
	public Duration plus(Duration d);

}

package it.amattioli.encapsulate.dates;

import java.util.Date;
import java.util.Calendar;

/**
 * Check if a time point is between two hours, independently of the day.
 * For example it can check if the time is between 09:00 AM and 06:00 PM.
 * 
 */
public class RangeEachDay implements TemporalExpression {
	private int startHour = 0;
	private int startMinute = 0;
	private int endHour = 0;
	private int endMinute = 0;

	/**
	 * Constructs an expression using the start and end hours.
	 * 
	 * @param startHour the start hour (0 to 24)
	 *            
	 * @param endHour the end hour (0 to 24)
	 */
	public RangeEachDay(int startHour, int endHour) {
		if (startHour < 0 || startHour > 24) {
			throw new IllegalArgumentException();
		}
		if (endHour < 0 || endHour > 24) {
			throw new IllegalArgumentException();
		}
		if (endHour > startHour) {
			throw new IllegalArgumentException();
		}
		this.startHour = startHour;
		this.endHour = endHour;
	}

	/**
	 * Constructs an expression using the start and end hours and minutes.
	 * 
	 * @param startHour the start hour (0 to 23) 
	 * 
	 * @param startMinute the start minute
	 * 
	 * @param endHour the end hour (0 to 24)
	 * 
	 * @param endMinute the end minute
	 * 
	 */
	public RangeEachDay(int startHour, int startMinute, int endHour, int endMinute) {
		this.startHour = startHour;
		this.startMinute = startMinute;
		this.endHour = endHour;
		this.endMinute = endMinute;
	}

	public boolean includes(Date d) {
		Calendar rangeStart = Calendar.getInstance();
		rangeStart.setTime(d);
		rangeStart.set(Calendar.HOUR_OF_DAY, startHour);
		rangeStart.set(Calendar.MINUTE, startMinute);
		rangeStart.set(Calendar.SECOND, 0);
		rangeStart.set(Calendar.MILLISECOND, 0);

		Calendar rangeEnd = Calendar.getInstance();
		rangeEnd.setTime(d);
		rangeEnd.set(Calendar.HOUR_OF_DAY, endHour);
		rangeEnd.set(Calendar.MINUTE, endMinute);
		rangeEnd.set(Calendar.SECOND, 0);
		rangeEnd.set(Calendar.MILLISECOND, 0);

		return !rangeStart.getTime().after(d) && !rangeEnd.getTime().before(d);
	}

}

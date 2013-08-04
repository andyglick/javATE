package it.amattioli.encapsulate.dates;

import static it.amattioli.encapsulate.dates.DatesErrorMessages.NULL_DURATION_BEGIN;
import static it.amattioli.encapsulate.dates.DatesErrorMessages.NULL_DURATION_END;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class PhysicalDuration implements Duration, Comparable<PhysicalDuration> {
	private long milliseconds; // internamente una durata viene registrata in millisecondi

	public static final PhysicalDuration ONE_SECOND = fromSeconds(1);
	public static final PhysicalDuration ONE_MINUTE = fromMinutes(1);
	public static final PhysicalDuration ONE_HOUR = fromHours(1);
	public static final PhysicalDuration ONE_DAY = fromDays(1);
	public static final PhysicalDuration ONE_WEEK = fromDays(7);

	private PhysicalDuration(long milliseconds) {
		this.milliseconds = milliseconds;
	}

	/**
	 * Constructs a duration given the milliseconds
	 * 
	 * @param milliseconds
	 * @return
	 */
	public static PhysicalDuration fromMilliseconds(long milliseconds) {
		return new PhysicalDuration(milliseconds);
	}

	/**
	 * Constructs a duration given the seconds
	 * 
	 * @param seconds
	 * @return
	 */
	public static PhysicalDuration fromSeconds(long seconds) {
		return new PhysicalDuration(seconds * 1000);
	}

	/**
	 * Constructs a duration given the minutes
	 * 
	 * @param minutes
	 * @return
	 */
	public static PhysicalDuration fromMinutes(long minutes) {
		return new PhysicalDuration(minutes * 1000 * 60);
	}

	/**
	 * Constructs a duration given the hours
	 * 
	 * @param hours
	 * @return
	 */
	public static PhysicalDuration fromHours(long hours) {
		return new PhysicalDuration(hours * 1000 * 60 * 60);
	}

	/**
	 * Constructs a duration given the number of days. Each day is
	 * treated as 24 hours. Daylight saving time is ignored
	 * 
	 * @param days
	 * @return
	 */
	public static PhysicalDuration fromDays(long days) {
		return new PhysicalDuration(days * 1000 * 60 * 60 * 24);
	}

	/**
	 * Constructs a duration given two time points
	 * 
	 * @param begin the start time point
	 * 
	 * @param end the end time point
	 * 
	 * @throws NullPointerException if at least one of the two parameters is null
	 */
	public PhysicalDuration(Date begin, Date end) {
		if (begin == null) {
			throw new NullPointerException(NULL_DURATION_BEGIN.getMessage());
		}
		if (end == null) {
			throw new NullPointerException(NULL_DURATION_END.getMessage());
		}
		milliseconds = end.getTime() - begin.getTime();
	}

	/**
	 * Constructs a duration given two days.
	 * 
	 * @param begin the start day
	 * 
	 * @param end the end day
	 * 
	 * @throws NullPointerException if at least one of the two parameters is null
	 * 
	 */
	public PhysicalDuration(Day begin, Day end) {
		if (begin == null) {
			throw new NullPointerException(NULL_DURATION_BEGIN.getMessage());
		}
		if (end == null) {
			throw new NullPointerException(NULL_DURATION_END.getMessage());
		}
		milliseconds = end.getEndTime().getTime() - begin.getInitTime().getTime();
	}

	/**
	 * Returns this duration in milliseconds
	 * 
	 * @return
	 */
	public long inMilliseconds() {
		return milliseconds;
	}

	/**
	 * Returns this duration in seconds
	 * 
	 * @return
	 */
	public long inSeconds() {
		return milliseconds / 1000;
	}

	/**
	 * Returns this duration in minutes
	 * 
	 * @return
	 */
	public long inMinutes() {
		return inSeconds() / 60;
	}

	/**
	 * Returns this duration in hours
	 * 
	 * @return
	 */
	public long inHours() {
		return inMinutes() / 60;
	}

	/**
	 * Returns this duration in days
	 * 
	 * @return
	 */
	public long inDays() {
		return inHours() / 24;
	}

	public long getRemainingMilliseconds() {
		return inMilliseconds() - fromSeconds(inSeconds()).inMilliseconds();
	}

	public long getRemainingSeconds() {
		return inSeconds() - fromMinutes(inMinutes()).inSeconds();
	}

	public long getRemainingMinutes() {
		return inMinutes() - fromHours(inHours()).inMinutes();
	}

	public long getRemainingHours() {
		return inHours() - fromDays(inDays()).inHours();
	}

	public Date after(Date begin) {
		Calendar cal = new GregorianCalendar();
		cal.setTime(begin);
		cal.add(Calendar.MILLISECOND, (int) inMilliseconds());
		return cal.getTime();
	}

	public Date before(Date begin) {
		Calendar cal = new GregorianCalendar();
		cal.setTime(begin);
		cal.add(Calendar.MILLISECOND, -(int) inMilliseconds());
		return cal.getTime();
	}
	
	public Duration plus(Duration d) {
		return new CompositeDuration(this, d);
	}

	public boolean equals(Object o) {
		boolean result = false;
		if (o instanceof PhysicalDuration) {
			PhysicalDuration interval = (PhysicalDuration) o;
			result = (this.milliseconds == interval.milliseconds);
		}
		return result;
	}

	public int hashCode() {
		return (int) (milliseconds % Integer.MAX_VALUE);
	}

	public int compareTo(PhysicalDuration interval) {
		int result = 0;
		if (this.milliseconds > interval.milliseconds) {
			result = 1;
		} else if (this.milliseconds < interval.milliseconds) {
			result = -1;
		}
		return result;
	}

	public String toString() {
		return "" + this.milliseconds + "ms";
	}
}

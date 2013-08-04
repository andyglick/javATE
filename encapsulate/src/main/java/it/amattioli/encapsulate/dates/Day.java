package it.amattioli.encapsulate.dates;

import it.amattioli.encapsulate.range.Discrete;
import it.amattioli.encapsulate.range.DiscreteRange;
import it.amattioli.encapsulate.range.GenericDiscreteRange;

import java.io.Serializable;
import java.util.*;
import java.text.*;

import org.apache.commons.lang.builder.HashCodeBuilder;

/**
 * An instance of this class represents a time interval corresponding to a day,
 * from 00:00 to 24:00.
 * As the concept of 00:00 and 24:00 is Timezone dependent, the same will be for
 * instances of this class. 
 * 
 */
public class Day extends ConventionalTimeInterval implements Discrete<Day>, Serializable, Cloneable {
	private static final int PRIME_NUMBER = 35831;
	private static final int NONZERO_ODD_NUMBER = 173462847;
	private static final long serialVersionUID = 1L;

	private Day() {
		this(new Date());
	}

	private Day(Calendar cal) {
		setCalendar(cal);
	}

	/**
	 * Construct the day that contains the passed time point in the current
	 * time zone. 
	 * 
	 * @param date
	 */
	public Day(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		setCalendar(cal);
	}

	/**
	 * Construct a day given day, month and year in the current time zone
	 * 
	 * @param day
	 *            
	 * @param month
	 *            
	 * @param year
	 *            
	 */
	public Day(int day, int month, int year) {
		Calendar cal = Calendar.getInstance();
		cal.clear();
		cal.set(Calendar.DAY_OF_MONTH, day);
		cal.set(Calendar.MONTH, month);
		cal.set(Calendar.YEAR, year);
		setCalendar(cal);
	}

	/**
	 * Costruisce un Day che rappresenta la giornata odierna.
	 */
	public static Day today() {
		return new Day();
	}

	public static Day yesterday() {
		return (Day) today().previous();
	}

	public static Day tomorrow() {
		return (Day) today().next();
	}

	/**
	 * The day of the month 
	 * 
	 */
	public int getDayOfMonth() {
		return getCalendar().get(Calendar.DAY_OF_MONTH);
	}

	/**
	 * The month
	 */
	public int getMonth() {
		return getCalendar().get(Calendar.MONTH);
	}

	/**
	 * The year
	 */
	public int getYear() {
		return getCalendar().get(Calendar.YEAR);
	}

	/**
	 * Returns the day immediately preceding this.
	 */
	public Day previous() {
		Calendar precCal = (Calendar) getCalendar().clone();
		precCal.add(Calendar.DATE, -1);
		return new Day(precCal);
	}

	/**
	 * Returns the day immediately following this.
	 */
	public Day next() {
		Calendar succCal = (Calendar) getCalendar().clone();
		succCal.add(Calendar.DATE, 1);
		return new Day(succCal);
	}

	/**
	 * Returns the time point that is the end of this day
	 * 
	 */
	public Date getEndTime() {
		Calendar succCal = (Calendar) getCalendar().clone();
		succCal.add(Calendar.DATE, 1);
		return succCal.getTime();
	}

	@Override
	public int compareTo(Day d) {
		return this.getInitTime().compareTo(d.getInitTime());
	}

	@Override
	public boolean equals(Object o) {
		if (o instanceof Day) {
			Day d = (Day) o;
			return this.getInitTime().equals(d.getInitTime());
		} else {
			return false;
		}
	}

	@Override
	public int hashCode() {
		HashCodeBuilder builder = new HashCodeBuilder(NONZERO_ODD_NUMBER, PRIME_NUMBER);
		return builder.append(getInitTime())
		              .append(getEndTime())
		              .toHashCode();
	}

	@Override
	public Day clone() {
		return new Day((Calendar) getCalendar().clone());
	}

	@Override
	public String toString() {
		DateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		return format.format(getInitTime());
	}
	
	@Override
	public Duration getDuration() {
		return ConventionalDuration.ONE_DAY;
	}
	
	public DiscreteRange<Day> through(Day end) {
		return new GenericDiscreteRange<Day>(this,end);
	}
}

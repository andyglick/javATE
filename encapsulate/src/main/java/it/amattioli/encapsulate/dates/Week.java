package it.amattioli.encapsulate.dates;

import it.amattioli.encapsulate.range.Discrete;
import it.amattioli.encapsulate.range.DiscreteRange;
import it.amattioli.encapsulate.range.GenericDiscreteRange;

import java.io.Serializable;
import java.util.*;
import java.text.*;

import org.apache.commons.lang.builder.HashCodeBuilder;

/**
 * An object of this class represents a time interval corresponding to one week
 *
 * @author a.mattioli
 *
 */
public class Week extends ConventionalTimeInterval implements Discrete<Week>, Serializable, Cloneable {
	private static final long serialVersionUID = 1L;
	private static final int PRIME_NUMBER = 76753;
	private static final int NONZERO_ODD_NUMBER = 9824553;

	/**
     * Construct an object that represents the current week in the default timezone and locale
     *
     */
    private Week() {
        this(new Date());
    }

    private Week(Calendar cal) {
        setCalendar(cal);
    }

    /**
     * Construct an object that represents the week containing the passed time point
     * 
     * @param date
     */
    public Week(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.DAY_OF_WEEK, cal.getFirstDayOfWeek());
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        setCalendar(cal);
    }

    public static Week thisWeek() {
        return new Week();
    }

    /**
     * Returns the month in which the first day of the week is
     *
     * @return the month in which the first day of the week is
     */
    public int getMonth() {
        return getCalendar().get(Calendar.MONTH);
    }

    /**
     * Returns the year in which the first day of the week is
     *
     * @return the year in which the first day of the week is
     */
    public int getYear() {
        return getCalendar().get(Calendar.YEAR);
    }

    /**
     * Returns the week preceding this
     *
     * @return the week preceding this
     */
    public Week previous() {
        Calendar precCal = (Calendar) getCalendar().clone();
        precCal.setLenient(true);
        precCal.add(Calendar.WEEK_OF_MONTH, -1);
        return new Week(precCal);
    }

    /**
     * Returns the week following this.
     *
     * @return the week following this
     */
    public Week next() {
        Calendar succCal = (Calendar) getCalendar().clone();
        succCal.setLenient(true);
        succCal.add(Calendar.WEEK_OF_MONTH, 1);
        return new Week(succCal);
    }

    public Date getEndTime() {
        Calendar succCal = (Calendar) getCalendar().clone();
        succCal.setLenient(true);
        succCal.add(Calendar.WEEK_OF_MONTH, 1);
        return succCal.getTime();
    }

    public Day getStartDay() {
        return new Day(getCalendar().getTime());
    }

    public Day getEndDay() {
        return next().getStartDay().previous();
    }

    public int compareTo(Week m) {
        return this.getInitTime().compareTo(m.getInitTime());
    }

    public boolean equals(Object o) {
        if (o instanceof Week) {
            Week m = (Week) o;
            return this.getInitTime().equals(m.getInitTime());
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
    
    public Object clone() {
        return new Week((Calendar) getCalendar().clone());
    }

    public String toString() {
        DateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        return format.format(getInitTime()) + " - " + format.format(getEndTime());
    }
    
    @Override
	public Duration getDuration() {
		return ConventionalDuration.ONE_WEEK;
	}
    
    public DiscreteRange<Week> through(Week end) {
		return new GenericDiscreteRange<Week>(this,end);
	}
}

package it.amattioli.encapsulate.dates;

import it.amattioli.encapsulate.range.Discrete;
import it.amattioli.encapsulate.range.DiscreteRange;
import it.amattioli.encapsulate.range.GenericDiscreteRange;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

import org.apache.commons.lang.builder.HashCodeBuilder;

public class Year extends ConventionalTimeInterval implements Discrete<Year>, Serializable, Cloneable {
	private static final long serialVersionUID = 1L;
	private static final int PRIME_NUMBER = 56479;
	private static final int NONZERO_ODD_NUMBER = 8917263;

	/**
	 * Construct an object that represents the current year in the default locale
     * 
     */
    private Year() {
        this(new Date());
    }

    private Year(Calendar cal) {
        setCalendar(cal);
    }

    /**
     * Construct an object that represents the year containing the passed time point
     * 
     * @param date
     */
    public Year(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.MONTH, 0);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        setCalendar(cal);
    }

    /**
     * Construct an year object given the year number.
     * 
     * @param year
     */
    public Year(int year) {
        Calendar cal = Calendar.getInstance();
        cal.clear();
        cal.set(Calendar.YEAR, year);
        setCalendar(cal);
    }

    public static Year thisYear() {
        return new Year();
    }

    /**
     * Returns the year number.
     * 
     * @return l'anno
     */
    public int getYear() {
        return getCalendar().get(Calendar.YEAR);
    }

    /**
     * Returns the year preceding this
     * 
     * @return the year preceding this
     */
    public Year previous() {
        Calendar precCal = (Calendar) getCalendar().clone();
        precCal.add(Calendar.YEAR, -1);
        return new Year(precCal);
    }

    /**
     * Returns the year following this
     * 
     * @return the year following this
     */
    public Year next() {
        Calendar succCal = (Calendar) getCalendar().clone();
        succCal.add(Calendar.YEAR, 1);
        return new Year(succCal);
    }

    /**
     * Returns the time point that is the end of this year
     */
    public Date getEndTime() {
        Calendar succCal = (Calendar) getCalendar().clone();
        succCal.add(Calendar.YEAR, 1);
        return succCal.getTime();
    }

    public Day getStartDay() {
        return new Day(1, Calendar.JANUARY, getYear());
    }

    public Day getEndDay() {
        return new Day(31, Calendar.DECEMBER, getYear());
    }
    
    public Month getStartMonth() {
    	return new Month(Calendar.JANUARY, getYear());
    }
    
    public Month getEndMonth() {
    	return new Month(Calendar.DECEMBER, getYear());
    }

    public int compareTo(Year y) {
        return this.getInitTime().compareTo(y.getInitTime());
    }

    public boolean equals(Object o) {
        if (o instanceof Year) {
            Year y = (Year) o;
            return this.getInitTime().equals(y.getInitTime());
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
        return new Year((Calendar) getCalendar().clone());
    }

    public String toString() {
        DateFormat format = new SimpleDateFormat("yyyy");
        return format.format(getInitTime());
    }

    @Override
	public Duration getDuration() {
		return ConventionalDuration.ONE_YEAR;
	}
    
    public Collection<Month> months() {
    	Collection<Month> result = new ArrayList<Month>();
    	Month curr = getStartMonth();
    	while (contains(curr)) {
    		result.add(curr);
    		curr = curr.next();
    	}
    	return result;
    }
    
    public DiscreteRange<Year> through(Year end) {
		return new GenericDiscreteRange<Year>(this,end);
	}
}

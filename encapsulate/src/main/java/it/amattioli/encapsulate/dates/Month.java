package it.amattioli.encapsulate.dates;

import it.amattioli.encapsulate.range.Discrete;
import it.amattioli.encapsulate.range.DiscreteRange;
import it.amattioli.encapsulate.range.GenericDiscreteRange;

import java.io.Serializable;
import java.util.*;
import java.text.*;

import org.apache.commons.lang.builder.HashCodeBuilder;

/**
 * Rappresenta un arco di tempo corrispondente ad un mese.
 *
 * @author a.mattioli
 *
 * Creato il Jan 28, 2005 alle 10:16:18 PM
 */
public class Month extends ConventionalTimeInterval implements Discrete<Month>, Serializable, Cloneable {
	private static final int PRIME_NUMBER = 24611;
	private static final int NONZERO_ODD_NUMBER = 276543891;
	private static final long serialVersionUID = 1L;

	/**
     * Costruisce un oggetto che rappresenta il mese corrente.
     *
     */
    private Month() {
        this(new Date());
    }

    private Month(Calendar cal) {
        setCalendar(cal);
    }

    /**
     * Costruisce un oggetto che rappresenta il mese contenente la data passata
     * come parametro.
     * 
     * @param date
     *            la data che dovr&agrave; appartenere al mese da costruire
     */
    public Month(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        setCalendar(cal);
    }

    /**
     * Costruisce un oggetto mese a partire dal mese e dall'anno.
     * 
     * @param month
     *            una delle costanti della classe Calendar che rappresentano il
     *            mese da costruire
     * @param year
     *            l'anno del mese da costruire
     */
    public Month(int month, int year) {
        Calendar cal = Calendar.getInstance();
        cal.clear();
        cal.set(Calendar.MONTH, month);
        cal.set(Calendar.YEAR, year);
        setCalendar(cal);
    }

    public static Month thisMonth() {
        return new Month();
    }

    /**
     * Restituisce il mese dell'anno.
     *
     * @return il mese dell'anno come una delle costanti della classe Calendar
     *         che rappresentano un mese
     */
    public int getMonth() {
        return getCalendar().get(Calendar.MONTH);
    }

    /**
     * Restituisce l'anno a cui appartiene questo mese.
     *
     * @return l'anno a cui appartiene questo mese
     */
    public int getYear() {
        return getCalendar().get(Calendar.YEAR);
    }

    /**
     * Il mese precedente a questo.
     *
     * @return il mese precedente a questo
     */
    public Month previous() {
        Calendar precCal = (Calendar) getCalendar().clone();
        precCal.add(Calendar.MONTH, -1);
        return new Month(precCal);
    }

    /**
     * Il mese successivo a questo.
     *
     * @return il mese successivo a questo
     */
    public Month next() {
        Calendar succCal = (Calendar) getCalendar().clone();
        succCal.add(Calendar.MONTH, 1);
        return new Month(succCal);
    }

    public Date getEndTime() {
        Calendar succCal = (Calendar) getCalendar().clone();
        succCal.add(Calendar.MONTH, 1);
        return succCal.getTime();
    }

    public Day getStartDay() {
        return new Day(1, getMonth(), getYear());
    }

    public Day getEndDay() {
        return (Day) (new Day(1, getMonth() + 1, getYear())).previous();
    }
    
    /**
     * Restituisce la prima settimana che contenga almeno un giorno di
     * questo mese 
     * 
     * @return
     */
    public Week getFirstWeek() {
    	return new Week(getInitTime());
    }
    
    /**
     * Restituisce l'ultima settimana che contenga almeno un giorno
     * di questo mese
     * 
     * @return
     */
    public Week getLastWeek() {
    	return new Week(getEndDay().getInitTime());
    }

    public int compareTo(Month m) {
        return this.getInitTime().compareTo(m.getInitTime());
    }

    public boolean equals(Object o) {
        if (o instanceof Month) {
            Month m = (Month) o;
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
        return new Month((Calendar) getCalendar().clone());
    }

    public String toString() {
        DateFormat format = new SimpleDateFormat("MM/yyyy");
        return format.format(getInitTime());
    }
    
    @Override
	public Duration getDuration() {
		return ConventionalDuration.ONE_MONTH;
	}
    
    public Collection<Day> days() {
    	Collection<Day> result = new ArrayList<Day>();
    	Day curr = getStartDay();
    	while (contains(curr)) {
    		result.add(curr);
    		curr = curr.next();
    	}
    	return result;
    }
    
    public DiscreteRange<Month> through(Month end) {
		return new GenericDiscreteRange<Month>(this,end);
	}
}

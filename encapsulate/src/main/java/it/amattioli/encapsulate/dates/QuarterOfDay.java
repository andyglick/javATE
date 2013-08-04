package it.amattioli.encapsulate.dates;

import it.amattioli.encapsulate.range.Discrete;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang.builder.HashCodeBuilder;

public class QuarterOfDay extends ConventionalTimeInterval implements Discrete<QuarterOfDay>, Serializable, Cloneable {
	private static final int PRIME_NUMBER = 84467;
	private static final int NONZERO_ODD_NUMBER = 76235445;

	private QuarterOfDay() {
		this(new Date());
	}
	
	private QuarterOfDay(Calendar cal) {
		setCalendar(cal);
	}
	
	public QuarterOfDay(Date date) {
		Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR, ((int)(cal.get(Calendar.HOUR) / 6)) * 6);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        setCalendar(cal);
	}
	
	public static QuarterOfDay now() {
		return new QuarterOfDay();
	}

	@Override
	public int compareTo(QuarterOfDay o) {
		return this.getInitTime().compareTo(o.getInitTime());
	}

	@Override
	public QuarterOfDay next() {
		Calendar succCal = (Calendar) getCalendar().clone();
        succCal.add(Calendar.HOUR_OF_DAY, 6);
        return new QuarterOfDay(succCal);
	}

	@Override
	public QuarterOfDay previous() {
		Calendar precCal = (Calendar) getCalendar().clone();
        precCal.add(Calendar.HOUR_OF_DAY, -6);
        return new QuarterOfDay(precCal);
	}

	public Date getEndTime() {
        Calendar succCal = (Calendar) getCalendar().clone();
        succCal.add(Calendar.HOUR_OF_DAY, 6);
        return succCal.getTime();
    }
	
	public boolean equals(Object o) {
        if (o instanceof QuarterOfDay) {
            QuarterOfDay m = (QuarterOfDay) o;
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
	
	@Override
	public Object clone() {
        return new QuarterOfDay((Calendar) getCalendar().clone());
    }

    public String toString() {
        DateFormat format = new SimpleDateFormat("hh:mm dd/MM/yyyy");
        return format.format(getInitTime());
    }
    
    @Override
	public Duration getDuration() {
		return getPhysicalDuration();
	}
}

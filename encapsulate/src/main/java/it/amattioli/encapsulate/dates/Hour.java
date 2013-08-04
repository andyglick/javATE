package it.amattioli.encapsulate.dates;

import it.amattioli.encapsulate.range.Discrete;
import it.amattioli.encapsulate.range.DiscreteRange;
import it.amattioli.encapsulate.range.GenericDiscreteRange;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang.builder.HashCodeBuilder;

public class Hour extends ConventionalTimeInterval implements Discrete<Hour>, Serializable, Cloneable {
	private static final int PRIME_NUMBER = 60167;
	private static final int NONZERO_ODD_NUMBER = 762347319;
	
	private Hour() {
		this(new Date());
	}
	
	private Hour(Calendar cal) {
		setCalendar(cal);
	}
	
	public Hour(Date date) {
		Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        setCalendar(cal);
	}
	
	public static Hour now() {
		return new Hour();
	}

	@Override
	public int compareTo(Hour o) {
		return this.getInitTime().compareTo(o.getInitTime());
	}

	@Override
	public Hour next() {
		Calendar precCal = (Calendar) getCalendar().clone();
        precCal.add(Calendar.HOUR_OF_DAY, 1);
        return new Hour(precCal);
	}

	@Override
	public Hour previous() {
		Calendar precCal = (Calendar) getCalendar().clone();
        precCal.add(Calendar.HOUR_OF_DAY, -1);
        return new Hour(precCal);
	}

	public Date getEndTime() {
        Calendar succCal = (Calendar) getCalendar().clone();
        succCal.add(Calendar.HOUR_OF_DAY, 1);
        return succCal.getTime();
    }
	
	public boolean equals(Object o) {
        if (o instanceof Hour) {
            Hour m = (Hour) o;
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
        return new Hour((Calendar) getCalendar().clone());
    }

    public String toString() {
        DateFormat format = new SimpleDateFormat("hh:mm dd/MM/yyyy");
        return format.format(getInitTime());
    }
    
    @Override
	public Duration getDuration() {
		return getPhysicalDuration();
	}
    
    public DiscreteRange<Hour> through(Hour end) {
		return new GenericDiscreteRange<Hour>(this,end);
	}
}

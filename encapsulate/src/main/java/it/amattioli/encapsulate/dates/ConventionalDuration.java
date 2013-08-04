package it.amattioli.encapsulate.dates;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class ConventionalDuration implements Duration {
	public static enum Type {
		DAY(Calendar.DAY_OF_MONTH),
		WEEK(Calendar.WEEK_OF_YEAR),
		MONTH(Calendar.MONTH),
		YEAR(Calendar.YEAR);
		
		private int calendarField;
		
		private Type(int calendarField) {
			this.calendarField = calendarField;
		}
		
	}
	
	public static final ConventionalDuration ONE_YEAR  = new ConventionalDuration(Type.YEAR, 1);
	public static final ConventionalDuration ONE_MONTH = new ConventionalDuration(Type.MONTH, 1);
	public static final ConventionalDuration ONE_WEEK  = new ConventionalDuration(Type.WEEK, 1);
	public static final ConventionalDuration ONE_DAY   = new ConventionalDuration(Type.DAY, 1);
	
	private Type type;
	private int amount;
	
	public ConventionalDuration(Type type, int amount) {
		this.type = type;
		this.amount = amount;
	}
	
	public ConventionalDuration(Day start, Day end) {
		checkStartBeforeEnd(start, end);
		this.type = Type.DAY;
		this.amount = (int)Math.round((end.getInitTime().getTime() - start.getInitTime().getTime()) / (86400.0*1000.0));
	}
	
	public ConventionalDuration(Month start, Month end) {
		checkStartBeforeEnd(start, end);
		this.type = Type.MONTH;
		this.amount = (end.getMonth() - start.getMonth()) + 12*(end.getYear() - start.getYear());
	}
	
	public ConventionalDuration(Year start, Year end) {
		checkStartBeforeEnd(start, end);
		this.type = Type.YEAR;
		this.amount = end.getYear() - start.getYear();
	}
	
	private void checkStartBeforeEnd(TimeInterval start, TimeInterval end) {
		if (end.before(start)) {
			throw new IllegalArgumentException("Start must be after end");
		}
	}
	
	@Override
	public Date after(Date begin) {
		GregorianCalendar cal = new GregorianCalendar();
		cal.setTime(begin);
		cal.add(type.calendarField, amount);
		return cal.getTime();
	}

	@Override
	public Date before(Date begin) {
		GregorianCalendar cal = new GregorianCalendar();
		cal.setTime(begin);
		cal.add(type.calendarField, -amount);
		return cal.getTime();
	}
	
	public Duration plus(Duration d) {
		return new CompositeDuration(this, d);
	}

}

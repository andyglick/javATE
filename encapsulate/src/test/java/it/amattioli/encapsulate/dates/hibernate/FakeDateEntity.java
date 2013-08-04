package it.amattioli.encapsulate.dates.hibernate;

import java.util.Date;

import it.amattioli.dominate.EntityImpl;
import it.amattioli.encapsulate.dates.Day;
import it.amattioli.encapsulate.dates.TimeInterval;

public class FakeDateEntity extends EntityImpl {
	private Date date;
	private Day day;
	private TimeInterval interval;

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Day getDay() {
		return day;
	}

	public void setDay(Day day) {
		this.day = day;
	}

	public TimeInterval getInterval() {
		return interval;
	}

	public void setInterval(TimeInterval interval) {
		this.interval = interval;
	}
}

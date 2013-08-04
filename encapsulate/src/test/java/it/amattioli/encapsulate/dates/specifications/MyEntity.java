package it.amattioli.encapsulate.dates.specifications;

import java.util.Date;

import it.amattioli.dominate.EntityImpl;
import it.amattioli.encapsulate.dates.TimeInterval;

public class MyEntity extends EntityImpl {
	private Date myDate;
	private TimeInterval myTimeInterval;

	public Date getMyDate() {
		return myDate;
	}

	public void setMyDate(Date myDate) {
		this.myDate = myDate;
	}

	public TimeInterval getMyTimeInterval() {
		return myTimeInterval;
	}

	public void setMyTimeInterval(TimeInterval myTimeInterval) {
		this.myTimeInterval = myTimeInterval;
	}
}

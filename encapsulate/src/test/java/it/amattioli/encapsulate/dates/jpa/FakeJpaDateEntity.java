package it.amattioli.encapsulate.dates.jpa;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Transient;
import javax.persistence.Version;

import it.amattioli.dominate.jpa.JpaEntity;
import it.amattioli.encapsulate.dates.Day;
import it.amattioli.encapsulate.dates.TimeInterval;

@Entity
public class FakeJpaDateEntity extends JpaEntity {
	@Id @GeneratedValue
	private Long id;
	@Version
	private Long version;
	private Date date;
	@Transient
	private Day day;
	@Transient
	private TimeInterval interval;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getVersion() {
		return version;
	}

	public void setVersion(Long version) {
		this.version = version;
	}

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

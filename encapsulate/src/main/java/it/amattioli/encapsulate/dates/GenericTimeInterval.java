package it.amattioli.encapsulate.dates;

import it.amattioli.encapsulate.range.*;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

/**
 * Estensione di un range che rappresenta un intervallo di tempo.
 */
public class GenericTimeInterval extends GenericContinousRange<Date> implements TimeInterval, TemporalExpression, Serializable {

	private static Date initTime(ConventionalTimeInterval interval) {
		if (interval == null) {
			return null;
		}
		return interval.getInitTime();
	}
	
	private static Date endTime(ConventionalTimeInterval interval) {
		if (interval == null) {
			return null;
		}
		return interval.getEndTime();
	}
	
	/*
	 * Costruttore senza parametri privato per l'uso dei tools di
	 * Object-Relational Mapping
	 */
	@SuppressWarnings("unused")
	private GenericTimeInterval() {
	}

	/**
	 * Costruisce un intervallo di tempo dati gli istanti iniziale e finale.
	 * 
	 * @param begin
	 *            istante iniziale dell'intervallo
	 * @param end
	 *            istante finale dell'intervallo
	 * @throws NullPointerException
	 *             se almeno uno dei due parametri è nullo
	 */
	public GenericTimeInterval(Date begin, Date end) {
		super(begin, end);
	}

	/**
	 * Costruisce un intervallo di tempo dati il giorno iniziale e finale.
	 */
	public GenericTimeInterval(ConventionalTimeInterval begin, ConventionalTimeInterval end) {
		this(initTime(begin), endTime(end));
	}

	/**
	 * Costruisce un intervallo di tempo con un limite inferiore ma senza limite
	 * superiore.
	 * 
	 * @param begin
	 *            limite inferiore dell'intervallo
	 * @throws NullPointerException
	 *             se il parametro è nullo
	 */
	public static TimeInterval lowBoundedInterval(Date begin) {
		TimeInterval result = new GenericTimeInterval(begin, null);
		return result;
	}
	
	public static TimeInterval lowBoundedInterval(ConventionalTimeInterval begin) {
		TimeInterval result = new GenericTimeInterval(begin, null);
		return result;
	}

	/**
	 * Costruisce un intervallo di tempo con un limite superiore ma senza limite
	 * inferiore.
	 * 
	 * @param end
	 *            limite superiore dell'intervallo
	 * @throws NullPointerException
	 *             se il parametro è nullo
	 */
	public static TimeInterval highBoundedInterval(Date end) {
		TimeInterval result = new GenericTimeInterval(null, end);
		return result;
	}
	
	public static TimeInterval highBoundedInterval(ConventionalTimeInterval end) {
		TimeInterval result = new GenericTimeInterval(null, end);
		return result;
	}

	public PhysicalDuration getPhysicalDuration() {
		return new PhysicalDuration((Date) getLow(), (Date) getHigh());
	}
	
	public Duration getDuration() {
		return getPhysicalDuration();
	}

	/*
	 * public boolean includes(Date d) { return includes(d); }
	 */
	@SuppressWarnings("unchecked")
	protected GenericTimeInterval newRange(Date low, Date high) {
		return new GenericTimeInterval(low, high);
	}

	public Day getLowDay() {
		return new Day((Date) this.getLow());
	}

	public Day getHighDay() {
		Calendar cal = Calendar.getInstance();
		cal.setTime((Date) this.getHigh());
		if (cal.get(Calendar.HOUR_OF_DAY) != 0 || cal.get(Calendar.MINUTE) != 0 || cal.get(Calendar.SECOND) != 0 || cal.get(Calendar.MILLISECOND) != 0) {
			cal.set(Calendar.HOUR_OF_DAY, 0);
			cal.set(Calendar.MINUTE, 0);
			cal.set(Calendar.SECOND, 0);
			cal.set(Calendar.MILLISECOND, 0);
			cal.add(Calendar.DATE, 1);
		}
		return (Day) (new Day(cal.getTime())).previous();
	}

	public boolean before(TimeInterval other) {
		return getLow().before(other.getLow());
	}

	public boolean after(TimeInterval other) {
		return !getLow().before(other.getHigh());
	}
}

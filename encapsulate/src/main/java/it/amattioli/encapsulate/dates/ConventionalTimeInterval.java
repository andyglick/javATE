package it.amattioli.encapsulate.dates;

import java.io.Serializable;
import java.util.*;

import it.amattioli.encapsulate.range.*;

/**
 * 
 * Rappresenta un intervallo di tempo stabilito convenzionalmente come un
 * giorno, una settimana o un mese.
 * <p>
 * Essendo un intervallo convenzionale questo dipender&agrave; dalla locale in
 * cui ci si trova. Ad esempio lo stesso giorno potrà avere inizio e fine
 * diversi in nazioni diversi a causa del fuso orario.
 * <p>
 * L'insieme degli intervalli convenzionali dello steso tipo forma generalmente
 * un insieme discreto per cui &egrave; possibile implementare i metodi
 * dell'interfaccia @see(Discrete). Ad esempio l'insieme di tutti i giorni
 * &egrave; un insieme discreto per il quale &egrave; possibile stabilire
 * qual'&egrave; il giorno precedente e il giorno successivo
 * 
 * @author a.mattioli
 */
public abstract class ConventionalTimeInterval extends ContinousRange<Date> implements TimeInterval, Serializable {
	private Date start;
	private Calendar cal;

	/**
	 * Restituisce il calendario utilizzato per stabilire l'istante iniziale e
	 * finale di questo intervallo di tempo
	 * 
	 * @return il calendario utilizzato per stabilire l'istante iniziale e
	 *         finale di questo intervallo di tempo
	 */
	protected Calendar getCalendar() {
		if (cal == null) {
			cal = Calendar.getInstance();
			cal.setTime(start);
		}
		return cal;
	}

	/**
	 * Imposta il calendario utilizzato per stabilire l'istante iniziale e
	 * finale di questo intervallo di tempo
	 * 
	 * @param cal
	 *            il nuovo calendario da utilizzare
	 */
	protected void setCalendar(Calendar cal) {
		this.cal = cal;
		start = cal.getTime();
	}

	public abstract ConventionalTimeInterval previous();

	public abstract ConventionalTimeInterval next();

	public boolean includes(Date time) {
		return !time.before(getInitTime()) && time.before(getEndTime());
	}

	/**
	 * Restituisce l'istante di tempo iniziale di questo intervallo
	 * 
	 * @return l'istante di tempo iniziale di questo intervallo
	 */
	public Date getInitTime() {
		return start;
	}

	/**
	 * Restituisce l'istante di tempo finale di questo intervallo
	 * 
	 * @return l'istante di tempo finale di questo intervallo
	 */
	public abstract Date getEndTime();

	/**
	 * Verifica se un altro intervallo precede completamente questo. Questo vuol
	 * dire che l'istante di tempo finale dell'altro intervallo deve precedere
	 * l'istante di tempo iniziale di questo.
	 * 
	 * @param other
	 *            l'intervallo di tempo da confrontare con questo
	 * @return true se il parametro precede completamente questo, altrimenti
	 *         false
	 * @throws NullPointerException
	 *             se il parametro &egrave; null
	 */
	public boolean before(TimeInterval other) {
		return isHighBounded() && other.isLowBounded() && !getHigh().after(other.getLow());
	}

	/**
	 * Verifica se un altro intervallo segue completamente questo. Questo vuol
	 * dire che l'istante di tempo iniziale dell'altro intervallo deve seguire
	 * l'istante di tempo finale di questo.
	 * 
	 * @param other
	 *            l'intervallo di tempo da confrontare con questo
	 * @return true se il parametro segue completamente questo, altrimenti false
	 * @throws NullPointerException
	 *             se il parametro &egrave; null
	 */
	public boolean after(TimeInterval other) {
		return isLowBounded() && other.isHighBounded() && !getLow().before(other.getHigh());
	}

	public boolean before(Date date) {
		return getEndTime().before(date);
	}

	public boolean after(Date date) {
		return getInitTime().after(date);
	}

	public int hashCode() {
		return start.hashCode();
	}

	public Date getLow() {
		return getInitTime();
	}

	public Date getHigh() {
		return getEndTime();
	}

	public boolean isLowBounded() {
		return true;
	}

	public boolean isHighBounded() {
		return true;
	}

	protected GenericTimeInterval newRange(Date low, Date high) {
		return new GenericTimeInterval(low, high);
	}

	public PhysicalDuration getPhysicalDuration() {
		return new PhysicalDuration(getInitTime(), getEndTime());
	}

	public Day getLowDay() {
		return new Day((Date) this.getLow());
	}

	public Day getHighDay() {
		Day d = new Day((Date) this.getHigh());
		return (Day) d.previous();
	}

	public DayIterator dayIterator() {
		return new DayIterator(getLowDay(), getHighDay());
	}

}

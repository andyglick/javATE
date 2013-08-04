package it.amattioli.encapsulate.dates;

import java.util.*;

/**
 * Iterates over a day range. A day iterator is built given a start and end
 * day and allows to iterate over each day in the range.
 * 
 */
public class DayIterator implements Iterator<Day> {
	private Day current;
	private Day end;

	/**
	 * Construct an iterator given the initial and end day.
	 * 
	 * @param start the start day. This will be the first day the {@link #next()}
	 *        method will return
	 *            
	 * @param end the end day. Once the {@link #next()} method returned this day
	 *        the {@link #hasNext()} method will return false
	 *            
	 */
	public DayIterator(Day start, Day end) {
		this.current = (Day) start.previous();
		this.end = end;
	}

	public boolean hasNext() {
		return current.before(end);
	}

	public Day next() {
		current = (Day) current.next();
		return current;
	}

	public void remove() {
		throw new UnsupportedOperationException();
	}

}

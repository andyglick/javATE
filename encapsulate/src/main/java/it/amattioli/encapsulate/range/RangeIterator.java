package it.amattioli.encapsulate.range;

import java.util.*;

public class RangeIterator<T extends Discrete<T>> implements Iterator<T> {
	private DiscreteRange<T> range;
	private T next;

	public RangeIterator(DiscreteRange<T> range) {
		if (!range.isLowBounded() || !range.isHighBounded()) {
			throw new UnboundedRangeException("Non è possibile iterare un range illimitato");
		}
		this.range = range;
		next = range.getLow();
	}

	public boolean hasNext() {
		return next != null;
	}

	public T next() {
		if (hasNext()) {
			T result = next;
			if (next.equals(range.getHigh())) {
				next = null;
			} else {
				next = next.next();
			}
			return result;
		} else {
			throw new NoSuchElementException();
		}
	}

	public void remove() {
		throw new UnsupportedOperationException();
	}

}

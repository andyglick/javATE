package it.amattioli.encapsulate.range;

import java.util.*;

public abstract class DiscreteRange<T extends Discrete<T>> extends AbstractRange<T> implements Iterable<T> {

	public Range<T> mergeWith(Range<T> r) {
		if (!(r instanceof DiscreteRange)) {
			throw new ClassCastException("Non è possibile costruire il merge tra un range discreto e uno continuo");
		}
		if (!this.overlaps(r)) {
			throw new IllegalArgumentException();
		}
		T newLow = null;
		if (r.isLowBounded() && this.isLowBounded()) {
			newLow = (this.getLow().compareTo(r.getLow()) < 0) ? this.getLow() : r.getLow();
		}
		T newHigh = null;
		if (r.isHighBounded() && this.isHighBounded()) {
			newHigh = (this.getHigh().compareTo(r.getHigh()) > 0) ? this.getHigh() : r.getHigh();
		}
		return newRange(newLow, newHigh);
	}

	public boolean abutOn(Range<? extends T> r) {
		return (r.isLowBounded() && this.isHighBounded() && (r.getLow()).previous().equals(this.getHigh()) || r.isHighBounded() && this.isLowBounded()
				&& (r.getHigh()).next().equals(this.getLow()));
	}

	public Range<T> gap(Range<T> r) {
		if (!(r instanceof DiscreteRange)) {
			throw new ClassCastException("Non è possibile costruire il gap tra un range discreto e uno continuo");
		}
		if (this.overlaps(r)) {
			return null;
		} else {
			T newHigh = null;
			if (this.isLowBounded() && r.isLowBounded()) {
				newHigh = (this.getLow().compareTo(r.getLow()) > 0) ? (this.getLow()).previous() : (r.getLow()).previous();
			} else {
				newHigh = this.isLowBounded() ? (this.getLow()).previous() : (r.getLow()).previous();
			}
			T newLow = null;
			if (this.isHighBounded() && r.isHighBounded()) {
				newLow = (this.getHigh().compareTo(r.getHigh()) < 0) ? (this.getHigh()).next() : (r.getHigh()).next();
			} else {
				newLow = this.isHighBounded() ? (this.getHigh()).next() : (r.getHigh()).next();
			}
			return newRange(newLow, newHigh);
		}
	}

	public Iterator<T> iterator() {
		return new RangeIterator<T>(this);
	}

	@Override
	public Set<Range<T>> minus(Range<T> r) {
		Set<Range<T>> result = new HashSet<Range<T>>();
		if (r.isLowBounded() && includes(r.getLow())) {
			T newLow = this.isLowBounded() ? this.getLow() : null;
			T newHigh = r.isLowBounded() ? r.getLow().previous() : null;
			result.add((Range<T>)newRange(newLow, newHigh));
		}
		if (r.isHighBounded() && includes(r.getHigh())) {
			T newLow = r.isHighBounded() ? r.getHigh().next() : null;
			T newHigh = this.isHighBounded() ? this.getHigh() : null;
			result.add((Range<T>)newRange(newLow, newHigh));
		}
		return result;
	}

}

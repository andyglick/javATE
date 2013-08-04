package it.amattioli.encapsulate.range;

import java.util.HashSet;
import java.util.Set;

public abstract class ContinousRange<T extends Comparable<T>> extends AbstractRange<T> {
	/*
	 * public ContinousRange(Comparable low, Comparable high) { super(low,high);
	 * }
	 */
	public Range<T> mergeWith(Range<T> r) {
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
		return (r.isLowBounded() && this.isHighBounded() && r.getLow().equals(this.getHigh()) || r.isHighBounded() && this.isLowBounded()
				&& r.getHigh().equals(this.getLow()));
	}

	public Range<T> gap(Range<T> r) {
		if (this.overlaps(r)) {
			return null;
		} else {
			T newHigh = null;
			if (this.isLowBounded() && r.isLowBounded()) {
				newHigh = (this.getLow().compareTo(r.getLow()) > 0) ? this.getLow() : r.getLow();
			} else {
				newHigh = this.isLowBounded() ? this.getLow() : r.getLow();
			}
			T newLow = null;
			if (this.isHighBounded() && r.isHighBounded()) {
				newLow = (this.getHigh().compareTo(r.getHigh()) < 0) ? this.getHigh() : r.getHigh();
			} else {
				newLow = this.isHighBounded() ? this.getHigh() : r.getHigh();
			}
			return newRange(newLow, newHigh);
		}
	}

	@Override
	public Set<Range<T>> minus(Range<T> r) {
		Set<Range<T>> result = new HashSet<Range<T>>();
		if (r.isLowBounded() && includes(r.getLow())) {
			T newLow = this.isLowBounded() ? this.getLow() : null;
			T newHigh = r.isLowBounded() ? r.getLow() : null;
			result.add((Range<T>)newRange(newLow, newHigh));
		}
		if (r.isHighBounded() && includes(r.getHigh())) {
			T newLow = r.isHighBounded() ? r.getHigh() : null;
			T newHigh = this.isHighBounded() ? this.getHigh() : null;
			result.add((Range<T>)newRange(newLow, newHigh));
		}
		return result;
	}

}

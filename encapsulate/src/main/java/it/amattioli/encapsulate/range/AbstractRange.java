package it.amattioli.encapsulate.range;

public abstract class AbstractRange<T extends Comparable<? super T>> implements Range<T> {

	public boolean includes(T test) {
		return (!isLowBounded() || getLow().compareTo(test) <= 0) 
		    && (!isHighBounded() || getHigh().compareTo(test) >= 0);
	}

	public boolean contains(Range<? extends T> r) {
		return (!this.isLowBounded() || (r.isLowBounded() && r.getLow().compareTo(getLow()) >= 0))
			&& (!this.isHighBounded() || (r.isHighBounded() && r.getHigh().compareTo(getHigh()) <= 0));
	}

	public boolean overlaps(Range<? extends T> r) {
		return (!r.isLowBounded() || !this.isHighBounded() || (this.isHighBounded() && this.getHigh().compareTo(r.getLow()) >= 0))
			&& (!this.isLowBounded() || !r.isHighBounded() || (r.isHighBounded() && r.getHigh().compareTo(this.getLow()) >= 0));
	}

	public boolean hasSameLow(Range<? extends T> r) {
		if (this.isLowBounded() && r.isLowBounded()) {
			return getLow().equals(r.getLow());
		} else {
			return !this.isLowBounded() && !r.isLowBounded();
		}
	}

	public boolean hasSameHigh(Range<? extends T> r) {
		if (this.isHighBounded() && r.isHighBounded()) {
			return getHigh().equals(r.getHigh());
		} else {
			return !this.isHighBounded() && !r.isHighBounded();
		}
	}

	@SuppressWarnings("unchecked")
	public boolean equals(Object obj) {
		boolean result = false;
		if (obj instanceof Range) {
			Range<T> r = (Range<T>) obj;
			result = hasSameLow(r) && hasSameHigh(r);
		}
		return result;
	}

	public int hashCode() {
		int result = 17;
		result = 37 * result;
		if (isLowBounded()) {
			result += getLow().hashCode();
		}
		result = 37 * result;
		if (isHighBounded()) {
			result += getHigh().hashCode();
		}
		return result;
	}

	public String toString() {
		String low = isLowBounded() ? getLow().toString() : "";
		String high = isHighBounded() ? getHigh().toString() : "";
		return "(" + low + "," + high + ")";
	}

	/**
	 * Crea un nuovo range compatibile con questo.
	 * 
	 * @param <U>
	 * @param low
	 * @param high
	 * @return
	 */
	protected abstract <U extends Range<? extends T>> U newRange(T low, T high);
	
	@Override
	public Range<T> intersect(Range<T> r) {
		if (!this.overlaps(r)) {
			return null;
		} else {
			T newHigh = null;
			if (!this.isHighBounded() && r.isHighBounded()) {
				newHigh = r.getHigh();
			} else if (!r.isHighBounded() && this.isHighBounded()) {
				newHigh = this.getHigh();
			} else if (r.isHighBounded() && this.isHighBounded()) {
				newHigh = (this.getHigh().compareTo(r.getHigh()) < 0) ? this.getHigh() : r.getHigh();
			}
			T newLow = null;
			if (!this.isLowBounded() && r.isLowBounded()) {
				newLow = r.getLow();
			} else if (!r.isLowBounded() && this.isLowBounded()) {
				newLow = this.getLow();
			} else if (r.isLowBounded() && this.isLowBounded()) {
				newLow = (this.getLow().compareTo(r.getLow()) > 0) ? this.getLow() : r.getLow();
			}
			return newRange(newLow, newHigh);
		}
	}

}

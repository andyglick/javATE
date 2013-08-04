package it.amattioli.encapsulate.range;

public class GenericDiscreteRange<T extends Discrete<T>> extends DiscreteRange<T> {
	private T low, high;

	public GenericDiscreteRange(T low, T high) {
		this.low = low;
		this.high = high;
		/*
		 * if (low == null) { isLowBounded = false; } else { isLowBounded =
		 * true; } if (high == null) { isHighBounded = false; } else {
		 * isHighBounded = true; }
		 */
	}

	public T getLow() {
		if (this.isLowBounded()) {
			return low;
		} else {
			throw new UnboundedRangeException("Range senza estremo inferiore");
		}
	}

	public T getHigh() {
		if (this.isHighBounded()) {
			return high;
		} else {
			throw new UnboundedRangeException("Range senza estremo superiore");
		}
	}

	public boolean isLowBounded() {
		return low != null;
	}

	public boolean isHighBounded() {
		return high != null;
	}

	protected <U extends Range<? extends T>> U newRange(T low, T high) {
		return (U) new GenericDiscreteRange<T>(low, high);
	}

}

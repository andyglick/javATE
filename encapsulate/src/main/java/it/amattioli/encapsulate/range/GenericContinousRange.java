package it.amattioli.encapsulate.range;

public class GenericContinousRange<T extends Comparable<T>> extends ContinousRange<T> {
	private T low, high;

	// private boolean isLowBounded, isHighBounded;

	/*
	 * Costruttore senza parametri protetto per l'uso dei tools di
	 * Object-Relational Mapping
	 */
	protected GenericContinousRange() {
	}

	public GenericContinousRange(T low, T high) {
		setLow(low);
		setHigh(high);
	}

	private void setLow(T low) {
		this.low = low;
		/*
		 * if (low == null) { isLowBounded = false; } else { isLowBounded =
		 * true; }
		 */
	}

	public T getLow() {
		if (this.isLowBounded()) {
			return low;
		} else {
			throw new UnboundedRangeException("Range senza estremo inferiore");
		}
	}

	private void setHigh(T high) {
		this.high = high;
		/*
		 * if (high == null) { isHighBounded = false; } else { isHighBounded =
		 * true; }
		 */
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
		// return isLowBounded;
	}

	public boolean isHighBounded() {
		return high != null;
		// return isHighBounded;
	}

	protected <U extends Range<? extends T>> U newRange(T low, T high) {
		return (U) new GenericContinousRange<T>(low, high);
	}

}

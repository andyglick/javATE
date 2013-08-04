package it.amattioli.workstate.actions;

/**
 * <p>
 * A guard whose {@link #check(AttributeReader, AttributeReader)} method
 * always returns true
 * </p>
 * 
 * <p>
 * Istances of NullGuard are used as null-objects. It is implemented as a singleton
 * so to get an instance use the {@link #getInstance()} factory method.
 * </p>
 */
public class NullGuard extends AbstractGuard {
	public static final Integer NULL_GUARD_PRIORITY = Integer.valueOf(-1000);
	private static final NullGuard instance = new NullGuard();

	public static NullGuard getInstance() {
		return instance;
	}

	private NullGuard() {
	}

	public boolean check(AttributeReader event, AttributeReader state) {
		return true;
	}

	public Integer getPriority() {
		return NULL_GUARD_PRIORITY;
	}

}

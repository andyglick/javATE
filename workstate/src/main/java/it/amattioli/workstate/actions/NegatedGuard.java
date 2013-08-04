package it.amattioli.workstate.actions;

import it.amattioli.workstate.exceptions.ErrorMessage;
import it.amattioli.workstate.exceptions.ErrorMessages;

/**
 * A decorator that negates a guard. The result of the {@link #check(AttributeReader, AttributeReader)}
 * method on this guard corresponds to the "not" operator applied to the result
 * of the {@link #check(AttributeReader, AttributeReader)} method on the guard passed
 * to the constructor.
 * 
 */
public class NegatedGuard implements Guard {
	private Guard negatingGuard;

	/**
	 * Build a guard that negates the result of the guard passed as parameter.
	 * 
	 * @param negatingGuard
	 *            the negating guard
	 * @throws NullPointerException
	 *             if the negating guard is null
	 */
	public NegatedGuard(Guard negatingGuard) {
		if (negatingGuard == null) {
			throw ErrorMessages
					.newNullPointerException(ErrorMessage.NULL_NEGATING_GUARD);
		}
		this.negatingGuard = negatingGuard;
	}

	public boolean check(AttributeReader event, AttributeReader state) {
		return !negatingGuard.check(event, state);
	}

	/**
	 * The priority of this guard is equals to the priority of the
	 * negating guard passed to the constructor
	 */
	public Integer getPriority() {
		return negatingGuard.getPriority();
	}

	/**
	 * Check if this guard is the negation of the guard passed as the
	 * parameter
	 */
	public boolean negates(Guard guard) {
		return negatingGuard.equals(guard);
	}

	@Override
	public boolean equals(Object o) {
		boolean result = false;
		if (o instanceof NegatedGuard) {
			NegatedGuard guard = (NegatedGuard) o;
			result = this.negatingGuard.equals(guard.negatingGuard);
		}
		return result;
	}

	private static final int PRIME = 41;
	
	@Override
	public int hashCode() {
		return PRIME + negatingGuard.hashCode();
	}
}

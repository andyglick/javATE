package it.amattioli.workstate.core;

import it.amattioli.workstate.info.Visitor;

public abstract class MetaPseudoState extends MetaState {

	public String toString() {
		return this.getClass().getName();
	}

	/**
	 * Two pseudostates are equals if they have the same class.
	 */
	public boolean equals(Object o) {
		return this.getClass().equals(o.getClass());
	}

	@Override
	public void receive(Visitor visitor) {
		// Nothing to do!
	}
}
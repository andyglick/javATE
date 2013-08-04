package it.amattioli.workstate.core;

public class MetaInitialState extends MetaPseudoState {

	public State newState(CompositeState parent) {
		checkParentState(parent);
		return new InitialState(this, parent);
	}

	public String toString() {
		String result = "InitialState";
		if (getParent() != null) {
			result += " of " + getParent().toString();
		}
		return result;
	}

}
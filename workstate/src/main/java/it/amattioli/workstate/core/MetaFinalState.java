package it.amattioli.workstate.core;

/**
 * 
 * @author a.mattioli
 */
public class MetaFinalState extends MetaPseudoState {

	public State newState(CompositeState parent) {
		checkParentState(parent);
		return new FinalState(this, parent);
	}

	public String toString() {
		String result = "FinalState";
		if (getParent() != null) {
			result += " of " + getParent().toString();
		}
		return result;
	}

}

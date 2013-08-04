package it.amattioli.workstate.core;

public class MetaJunctionPoint extends MetaPseudoState {

	public State newState(CompositeState parent) {
		checkParentState(parent);
		return new JunctionPoint(this, parent);
	}

}
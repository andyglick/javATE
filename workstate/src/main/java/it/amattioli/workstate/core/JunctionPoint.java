package it.amattioli.workstate.core;

import it.amattioli.workstate.exceptions.*;

public class JunctionPoint extends PseudoState {
	private MetaJunctionPoint metaState;

	public JunctionPoint(MetaJunctionPoint metaState, CompositeState parent) {
		super(metaState, parent);
		this.metaState = metaState;
	}

	public void enter() throws WorkflowException {
		super.enter();
		Transition triggeredTransition = metaState.findTriggeredTransition(Event.NULL, this);
		if (triggeredTransition == null) {
			throw ErrorMessages.newIllegalStateException(ErrorMessage.NO_TRANSITION_ACTIVATED, this.toString());
		}
		triggeredTransition.perform(Event.NULL, this);
	}

	public String toString() {
		return "JunctionPoint";
	}

}

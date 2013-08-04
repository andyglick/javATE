package it.amattioli.workstate.core;

import it.amattioli.workstate.exceptions.*;

/**
 * An initial state is a pseudo-state that represents the starting point of a
 * sequential state. Every sequential state must have only one initial state.
 * This initial state can be the source state of one or more transitions
 * associated with no events. If a transition enters a sequential state without
 * specifying a substate, its initial state will be activated and all the
 * transitions starting from it will be evaluated to find the one to be fired.
 * 
 */
public class InitialState extends PseudoState {
	private MetaInitialState metaState;

	public InitialState(MetaInitialState metaState, CompositeState parent) {
		super(metaState, parent);
		this.metaState = metaState;
	}

	/**
	 * Enters the initial state. When the initial state is entered all the
	 * transitions starting from it will be evaluated to find the one to follow.
	 * Once the right transition is found it will be fired. If all the
	 * transition guards return false an {@link IllegalStateException} will be
	 * thrown.
	 * 
	 */
	public void enter() throws WorkflowException {
		super.enter();
		Transition triggeredTransition = metaState.findTriggeredTransition(Event.NULL, this);
		if (triggeredTransition == null) {
			throw ErrorMessages.newIllegalStateException(ErrorMessage.NO_TRANSITION_ACTIVATED, this.toString());
		}
		triggeredTransition.perform(Event.NULL, this);
	}

	public String toString() {
		return metaState.toString();
	}

}

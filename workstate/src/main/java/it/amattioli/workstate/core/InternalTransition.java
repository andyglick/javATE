package it.amattioli.workstate.core;

import it.amattioli.workstate.actions.*;
import it.amattioli.workstate.exceptions.*;

/**
 * Am InternalTransition connects a state with itself and during its execution
 * the state is not exited. So the attributes of the associated state are
 * available during the action execution and they are not re-initialized as of
 * an {@link ExternalTransition}.
 * 
 * @see ExternalTransition
 */
public class InternalTransition extends Transition {

	public InternalTransition(MetaEvent event, MetaState state, TransitionAction action, Guard guard) {
		super(event, state, state, action, guard);
		if (!(state instanceof MetaRealState)) {
			throw ErrorMessages.newIllegalArgumentException(ErrorMessage.ILLEGAL_INTERNAL_TRANSITION_STATE, state.toString());
		}
	}

	public void perform(Event event, State currentState) throws WorkflowException {
		doAction(event, (RealState) currentState);
	}

}

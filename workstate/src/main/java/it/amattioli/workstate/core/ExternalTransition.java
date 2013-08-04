package it.amattioli.workstate.core;

import it.amattioli.workstate.actions.*;
import it.amattioli.workstate.exceptions.*;

import java.util.*;

/**
 * An external transition exits from its source state and enters its destination
 * state even if the two are the same state. So in every case the transition:
 * <ul>
 * <li>will execute the source state exit action
 * <li>will destroy the source state with all its attributes
 * <li>will execute its transition action
 * <li>will create a new instance of the destination state and will initialize
 * its attributes
 * <li>will execute the destination state entry action
 * </ul>
 * 
 * @see InternalTransition
 */
public class ExternalTransition extends Transition {

	public ExternalTransition(MetaEvent event, MetaState start, MetaState end, TransitionAction action, Guard guard) {
		super(event, start, end, action, guard);
	}

	public void perform(Event event, State currentState) throws WorkflowException {
		Stack<MetaState> entryStack = new Stack<MetaState>();
		entryStack.push(getEndState());
		MetaCompositeState breakMetaState = getEndState().getParent();
		while (!getStartState().equals(breakMetaState) && !getStartState().descendFrom(breakMetaState)) {
			entryStack.push(breakMetaState);
			breakMetaState = breakMetaState.getParent();
			if (breakMetaState == null) {
				throw ErrorMessages.newIllegalArgumentException(ErrorMessage.NO_BREAK_STATE, 
						                                        currentState.toString(), 
						                                        getEndState().toString());
			}
		}
		CompositeState breakState = null;
		if (currentState.hasMetaState(breakMetaState)) {
			// TODO: alza eccezione se non è un CompositeState
			breakState = (CompositeState) currentState;
		} else {
			breakState = currentState.findAncestor(breakMetaState);
		}
		if (!(breakState instanceof SequentialState)) {
			// TODO: alza eccezione perche' stai attraversando una regione
		}
		SequentialState sequentialBreak = (SequentialState) breakState;
		sequentialBreak.exitCurrent();
		try {
			doAction(event, sequentialBreak);
			try {
				sequentialBreak.enterCurrent(entryStack);
			} catch (WorkflowException e) {
				undoAction(event, sequentialBreak);
				throw e;
			}
		} catch (WorkflowException e) {
			sequentialBreak.reEnterCurrent();
			throw e;
		}
	}

	public boolean equals(Object o) {
		boolean result = false;
		if (o instanceof ExternalTransition) {
			result = super.equals(o);
		}
		return result;
	}

}

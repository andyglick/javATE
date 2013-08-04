package it.amattioli.workstate.core;

import it.amattioli.workstate.actions.*;
import it.amattioli.workstate.exceptions.*;

/**
 * 
 */
public class MetaConcurrentState extends MetaCompositeState {

	/**
	 * Construct a new MetaConcurrentState given its identifier (tag) and its
	 * entry and exit action.
	 * 
	 * @param tag this state identifier
	 * @param entry this state entry action. Passing null means no action should
	 *        be performed
	 * @param exit this state exit action. Passing null means no action should be
	 *        performed
	 * @throws NullPointerException if the tag is null
	 */
	public MetaConcurrentState(String tag, StateAction entry, StateAction exit) {
		super(tag, entry, exit);
	}

	/**
	 * Verifies that a sub-state of this state is a {@link MetaSequentialState}.
	 * 
	 * @throws IllegalArgumentException if the parameter cannot be a sub-state of 
	 *         this state
	 */
	protected void checkAllowedSubstate(MetaState newMetaState) {
		super.checkAllowedSubstate(newMetaState);
		if (!(newMetaState instanceof MetaSequentialState)) {
			throw ErrorMessages
					.newIllegalArgumentException(ErrorMessage.NON_SEQ_CONCURRENT_SUBSTATE);
		}
	}

	public State newState(CompositeState parent) {
		checkParentState(parent);
		return new ConcurrentState(this, parent);
	}

}
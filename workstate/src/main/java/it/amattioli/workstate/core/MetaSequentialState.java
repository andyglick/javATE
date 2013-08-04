package it.amattioli.workstate.core;

import it.amattioli.workstate.actions.*;
import it.amattioli.workstate.exceptions.*;

public class MetaSequentialState extends MetaCompositeState {

	/**
	 * The no-arguments constructor is used only for
	 * serialization/deserialization purposes. It should not be used for other
	 * purposes.
	 * 
	 */
	protected MetaSequentialState() {
		super("dummy", null, null);
	}

	/**
	 * Build a new {@link MetaSequentialState} given its tag and its entry and
	 * exit actions.
	 * 
	 * @param tag
	 *            this state tag
	 * @param entry
	 *            the state entry action or null if no action must be executed
	 *            when the state is entered
	 * @param exit
	 *            the state exit action or null if no action must be executed
	 *            when the state is exited
	 * @throws NullPointerException
	 *             if the tag is null
	 */
	public MetaSequentialState(String tag, StateAction entry, StateAction exit) {
		super(tag, entry, exit);
	}

	protected void checkAllowedSubstate(MetaState newMetaState) {
		super.checkAllowedSubstate(newMetaState);
		Class<? extends MetaState> newClass = newMetaState.getClass();
		for (MetaState curr: getSubstates()) {
			Class<? extends MetaState> currClass = curr.getClass();
			if (currClass.equals(newClass) && currClass.equals(MetaInitialState.class)) {
				throw ErrorMessages.newIllegalArgumentException(ErrorMessage.UNIQUE_META_TYPE, currClass.toString(), this.getTag());
			}
		}
	}

	/**
	 * Return the {@link MetaState} that represents the initial state of this
	 * sequential state.
	 * 
	 * @return the {@link MetaState} that represents the initial state of this
	 *         sequential state
	 * @throws IllegalStateException
	 *             if no initial state has been defined for this sequential
	 *             state
	 */
	public MetaInitialState getInitialState() {
		for (MetaState curr: getSubstates()) {
			if (curr instanceof MetaInitialState) {
				return (MetaInitialState) curr;
			}
		}
		throw ErrorMessages.newIllegalStateException(ErrorMessage.NO_INITIAL_STATE, this.getTag());
	}

	public State newState(CompositeState parent) {
		checkParentState(parent);
		return new SequentialState(this, parent);
	}

}
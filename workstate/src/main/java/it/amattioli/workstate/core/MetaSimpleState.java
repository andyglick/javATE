package it.amattioli.workstate.core;

import it.amattioli.workstate.actions.*;
import it.amattioli.workstate.info.Visitor;

/**
 * A {@link MetaSimpleState} represents the configuration of a simple state, a
 * state without sub-states.
 * 
 */
public class MetaSimpleState extends MetaRealState {

	/**
	 * Build a new {@link MetaSimpleState} given its tag and its entry and exit
	 * actions.
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
	public MetaSimpleState(String tag, StateAction entry, StateAction exit) {
		super(tag, entry, exit);
	}

	public State newState(CompositeState parent) {
		checkParentState(parent);
		return new SimpleState(this, parent);
	}

	@Override
	public void receive(Visitor visitor) {
		// Nothing to do!
	}

}
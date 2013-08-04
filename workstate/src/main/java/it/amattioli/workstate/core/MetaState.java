package it.amattioli.workstate.core;

import it.amattioli.workstate.config.Configuration;
import it.amattioli.workstate.exceptions.*;
import it.amattioli.workstate.info.Receiver;

import java.util.*;

/**
 * A {@link MetaState} represents the configuration of a possible state of a
 * {@link Machine}. Every instance of the machine will use the same
 * {@link MetaState} to retrieve configuration informations like:
 * <ul>
 * <li>Substates
 * <li>Entry and exit actions
 * <li>Transactions
 * <li>Etc.
 * </ul>
 * This class is the root of the inheritance tree for meta-states and implements
 * basic functionalities.
 */
public abstract class MetaState implements Receiver {
	private List<Transition> outgoingTransitions = Collections.emptyList();
	private MetaCompositeState parent;

	/**
	 * Create a new {@link State} based on this meta-state. Implementations of
	 * this method must call {@link #checkParentState(CompositeState)} to check
	 * that the passed state is admittable as parent state for the state that
	 * will be created.
	 * 
	 * @param parent
	 *            the parent state for the state that will be created
	 * @return the new state
	 * @throws IllegalArgumentException
	 *             if the parameter is not admittable as parent state for the
	 *             state that will be created
	 */
	public abstract State newState(CompositeState parent);

	/**
	 * Set the parent of this meta-state. When a new {@link MetaState} is
	 * created the parent is set to null. This means that the meta-state is a
	 * root one but can be later changed using this method. This method doesn't
	 * check that the parent state has this as one of its sub-states. It is a
	 * caller responsibility to assure this.
	 * 
	 * @param parent
	 *            the new parent meta-state
	 */
	public void setParent(MetaCompositeState parent) {
		this.parent = parent;
	}

	/**
	 * Return the parent meta-state
	 * 
	 * @return the parent meta-state
	 */
	public MetaCompositeState getParent() {
		return this.parent;
	}

	/**
	 * Check if the parameter could be the parent state of a {@link State} based
	 * on this. If it is not so an exception will be raised.
	 * 
	 * @param parent
	 *            the state to be checked
	 * @throws IllegalArgumentException
	 *             if the parameter cannot be the parent state of a
	 *             {@link State} based on this
	 */
	protected void checkParentState(CompositeState parent) {
		if (parent == null) {
			if (this.getParent() != null) {
				throw ErrorMessages.newIllegalArgumentException(ErrorMessage.NO_NULL_PARENT_META_STATE, this.toString());
			}
		} else {
			if (!parent.hasMetaState(this.getParent())) {
				throw ErrorMessages.newIllegalArgumentException(ErrorMessage.WRONG_PARENT_META_STATE, parent.toString(), this.toString());
			}
		}
	}

	/**
	 * Check if the parameter is an ancestor of this meta-state.
	 * 
	 * @param metaState
	 * @return true if the parameter is an ancestor of this meta-state, false
	 *         otherwise
	 * @throws NullPointerException
	 *             if the parameter is null
	 */
	public boolean descendFrom(MetaCompositeState metaState) {
		if (metaState == null) {
			throw ErrorMessages.newNullPointerException(ErrorMessage.NULL_STATE_DESCEND);
		}
		boolean retVal = false;
		if (metaState.equals(this.parent)) {
			retVal = true;
		} else {
			if (parent != null) {
				retVal = parent.descendFrom(metaState);
			} else {
				retVal = false;
			}
		}
		return retVal;
	}

	private void checkOverlappingTransition(Transition transition) {
		for (Transition curr: outgoingTransitions) {
			if (transition.hasSameTrigger(curr)) {
				throw ErrorMessages.newIllegalArgumentException(ErrorMessage.OVERLAPPING_TRANSITION, transition.toString(), curr.toString());
			}
		}
	}

	/**
	 * Add a {@link Transition} exiting from this meta-state.
	 * 
	 * @param transition
	 *            the new transition that will exit this meta-state
	 * @throws NullPointerException
	 *             if the parameter is null
	 * @throws IllegalArgumentException
	 *             if already exists a transition exiting from this meta-state
	 *             triggered by the same event and with an identical guard
	 * 
	 */
	public void addTransition(Transition transition) {
		if (transition == null) {
			throw ErrorMessages.newNullPointerException(ErrorMessage.NULL_TRANSITION);
		}
		if (outgoingTransitions.isEmpty()) {
			outgoingTransitions = new ArrayList<Transition>(1);
		} else {
			checkOverlappingTransition(transition);
		}
		outgoingTransitions.add(transition);
		Collections.sort(outgoingTransitions, Transition.getGuardPriorityComparator());
	}

	/**
	 * Find a transition triggered by a given {@link Event} when the machine is
	 * in a {@link State} based on this. Only the transitions directly exiting
	 * from this meta-state will be searched.
	 * 
	 * @param event
	 *            the received event
	 * @param state
	 *            the current state
	 * @return the found transition or null
	 */
	public Transition findTriggeredTransition(Event event, State state) {
		for (Transition curr: outgoingTransitions) {
			if (curr.isTriggeredBy(event, state)) {
				return curr;
			}
		}
		return null;
	}

	public Configuration getConfig() {
		return getParent().getConfig();
	}

	/**
	 * Add to the passed collection all the {@link MetaEvent}s receivable by the
	 * machine when it is in a state based on this.
	 * 
	 * @param coll the collection to which the {@link MetaEvent}s will be added
	 */
	public void addAvailableEvents(Collection<MetaEvent> coll) {
		for (Transition curr: outgoingTransitions) {
			coll.add(curr.getEvent());
		}
	}

	/**
	 * Return all the {@link MetaEvent}s receivable by the machine when it is in
	 * a state based on this.
	 * 
	 * @return all the {@link MetaEvent}s receivable by the machine when it is
	 *         in a state based on this
	 */
	public Collection<MetaEvent> getAvailableEvents() {
		Collection<MetaEvent> result = new ArrayList<MetaEvent>();
		addAvailableEvents(result);
		return result;
	}

}
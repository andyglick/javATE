package it.amattioli.workstate.core;

import it.amattioli.workstate.actions.*;
import it.amattioli.workstate.exceptions.*;

import java.util.*;

/**
 * Represent a generic state. Each state is associated to a {@link MetaState}
 * that represents its configuration. After creation a state is not active and
 * only the {@link #enter()} method can be called on it. This method activate
 * the state and initialize it. Calling {@link #exit()} the state will be exited
 * and it will be no more active. At creation time a unuique id will be created
 * for the state.
 * 
 */
public abstract class State implements AttributeReader {
	private MetaState metastate;
	private CompositeState parent;
	private boolean active = false;
	private Long id = new Long((long) (Math.random() * Long.MAX_VALUE));

	public State(MetaState metastate, CompositeState parent) {
		if (metastate == null) {
			throw ErrorMessages.newNullPointerException(ErrorMessage.NULL_META_STATE);
		}
		this.metastate = metastate;
		this.parent = parent;
	}

	/**
	 * Enter this state and activate it.
	 * 
	 * @throws IllegalStateException
	 *             if this state is already
	 */
	public void enter() throws WorkflowException {
		if (isActive()) {
			throw ErrorMessages.newIllegalStateException(ErrorMessage.STATE_ALREADY_ACTIVE, this.toString());
		}
		active = true;
	}

	/**
	 * Re-enter a state that was exited for exception handling purposes.
	 * 
	 */
	protected void reEnter() {
		active = true;
	}

	/**
	 * Exit a state and de-activate it.
	 * 
	 * @throws IllegalStateException
	 *             if the state is not active
	 */
	public void exit() throws WorkflowException {
		checkActive();
		active = false;
	}

	/**
	 * Re-exit a state that was entered for exception handling purposes.
	 * 
	 */
	protected void reExit() {
		active = false;
	}

	/**
	 * Return the composite state that is parent of this state.
	 * 
	 * @return the composite state that is parent of this state or null if this
	 *         is root
	 */
	public CompositeState getParent() {
		return parent;
	}

	/**
	 * Search for an ancestor of this state whose {@link MetaState} is given.
	 * 
	 * @throws IllegalArgumentException
	 *             if no ancestor of this has the given {@link MetaState}
	 */
	public CompositeState findAncestor(MetaState ancestorMetaState) {
		CompositeState parent = getParent();
		if (parent == null) {
			throw ErrorMessages.newIllegalArgumentException(ErrorMessage.NON_EXISTING_ANCESTOR, ancestorMetaState.toString());
		}
		if (parent.hasMetaState(ancestorMetaState)) {
			return parent;
		} else {
			return parent.findAncestor(ancestorMetaState);
		}
	}

	/**
	 * Verifies if this state is active.
	 * 
	 * @return true if this state is active, false otherwise
	 */
	public boolean isActive() {
		return active;
	}

	/**
	 * Verifies if this state is active. If this state is not active an
	 * exception will be raised.
	 * 
	 * @throws IllegalStateException
	 *             if this state is not active
	 */
	protected void checkActive() {
		if (!isActive()) {
			throw ErrorMessages.newIllegalStateException(ErrorMessage.NON_ACTIVE_STATE, this.toString());
		}
	}

	/**
	 * Check if the given {@link MetaState} is this state meta-state.
	 * 
	 * @param metastate
	 *            the {@link MetaState} to be checked
	 * @return true if the given {@link MetaState} is this state meta-state,
	 *         false otherwise
	 */
	public boolean hasMetaState(MetaState metastate) {
		return this.metastate.equals(metastate);
	}

	public Machine getRootMachine() {
		if (parent != null) {
			return parent.getRootMachine();
		} else {
			return null;
		}
	}

	public abstract void receiveEvent(Event event) throws WorkflowException;

	public abstract boolean admitEvent(Event event);

	/**
	 * Return the value of an attribute of this state. A generic state has no
	 * attributes. Sub-class of this must implement this method so to handle
	 * attributes.
	 * 
	 * @param tag
	 *            the attribute tag
	 * @return the attribute value
	 * @throws IllegalStateException
	 *             if the state is not active
	 * @throws IllegalArgumentException
	 *             if there is no attribute with the given name
	 */
	public Object getAttribute(String tag) {
		throw new IllegalArgumentException();
	}

	/**
	 * Return all the attributes of this state. A generic state has no
	 * attributes. Sub-class of this must implement this method so to handle
	 * attributes.
	 */
	public Map<String, Object> getAllAttributes() {
		return Collections.emptyMap();
	}

	/**
	 * Return the identifier of this state. The identifier is a random string
	 * generated when the state is created.
	 * 
	 * @return this state identifier
	 */
	public String getId() {
		return id.toString();
	}

	protected abstract StateMemento getMemento(StateMemento parent);

	public StateMemento getMemento() {
		return getMemento(null);
	}

	public abstract void restore(StateMemento memento) throws WorkflowException;

	public boolean equals(Object o) {
		boolean result = false;
		if (this.getClass().equals(o.getClass())) {
			State other = (State) o;
			result = this.isActive() == other.isActive() && this.metastate.equals(other.metastate);
		}
		return result;
	}

	/**
	 * Check if this state is a region of a {@link ConcurrentState}. Only
	 * {@link SequentialState}s can be regions so the default implementation of
	 * this method return false.
	 * 
	 * @return true if this state is a region of a {@link ConcurrentState},
	 *         false otherwise
	 */
	public boolean isRegion() {
		return false;
	}

	public Event buildEvent(String name, Map<String, Object> stringParameters) throws WorkflowException {
		throw ErrorMessages.newIllegalArgumentException(ErrorMessage.UNKNOWN_EVENT, name);
	}

	public void addAvailableEvents(Collection<MetaEvent> coll) {
		this.metastate.addAvailableEvents(coll);
	}

	public Collection<MetaEvent> getAvailableEvents() {
		Collection<MetaEvent> result = new ArrayList<MetaEvent>();
		addAvailableEvents(result);
		return result;
	}

}
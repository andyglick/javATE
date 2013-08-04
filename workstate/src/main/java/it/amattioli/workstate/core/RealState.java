package it.amattioli.workstate.core;

import it.amattioli.workstate.actions.*;
import it.amattioli.workstate.exceptions.*;
import it.amattioli.workstate.info.*;

import java.util.*;

/**
 * Represents a stable state in which a {@link Machine} can be after an event
 * has been received. A real state can have attributes and can execute actions
 * when entering and exiting it.
 * 
 */
public abstract class RealState extends State implements AttributeHandler, Receiver {
	private MetaRealState metaState;
	private Map<String, Object> attributes;

	public RealState(MetaRealState metaState, CompositeState parent) {
		super(metaState, parent);
		this.metaState = metaState;
	}

	/**
	 * Return the tag of this state.
	 * 
	 * @return the tag of this state
	 */
	public String getTag() {
		return metaState.getTag();
	}

	/**
	 * Check if the passed string is the tag of an attribute that can be
	 * accessed by this state. An attribute is accessible if it belongs to this
	 * state or to one of its ancestors.
	 * 
	 * @param tag
	 *            the attribute tag
	 * @return true if the attribute is accessible, false otherwise
	 */
	public boolean hasAllowedAttribute(String tag) {
		return this.hasOwnAttribute(tag) || getParent().hasAllowedAttribute(tag);
	}

	/**
	 * Check if the passed string is the tag of an attribute that belongs to
	 * this state. If the attribute is accessible but belongs to an ancestor
	 * this method will return false.
	 * 
	 * @param the
	 *            attribute tag
	 * @return true if this state has an attribute with the given tag, false
	 *         otherwise
	 */
	public boolean hasOwnAttribute(String tag) {
		return this.metaState.isAllowedAttribute(tag);
	}

	/**
	 * Get the value of an attribute accessible to this state. An attribute is
	 * accessible if it belongs to this state or to one of its ancestors.
	 * 
	 * @param tag
	 *            the attribute tag
	 * @return the attribute value
	 * @throws IllegalStateException
	 *             if this state is not active
	 */
	public Object getAttribute(String tag) {
		checkActive();
		if (metaState.isAllowedAttribute(tag)) {
			Object result = attributes.get(tag);
			return result;
		} else {
			try {
				return getParent().getAttribute(tag);
			} catch (IllegalArgumentException e) {
				throw ErrorMessages.newIllegalArgumentException(ErrorMessage.NON_EXISTING_ATTR_TAG, tag, this.toString());
			}
		}
	}

	/**
	 * Return a map containing all the attributes of this state. The map will
	 * contain only the attributes that belong to this state and not all the
	 * accessible attributes.
	 * 
	 * @return a map containing all the attributes of this state
	 */
	public Map<String, Object> getLocalAttributes() {
		return attributes;
	}

	/**
	 * Add to the passed map all the attributes that are accessible from this
	 * state. If the map contains a key identical to an attribute tag the old
	 * entry will be retained.
	 */
	protected Map<String, Object> addAllAttributes(Map<String, Object> attributes) {
		// attributes.putAll(this.attributes);
		for (Map.Entry<String, Object> curr: this.attributes.entrySet()) {
			if (!attributes.containsKey(curr.getKey())) {
				attributes.put(curr.getKey(), curr.getValue());
			}
		}
		if (getParent() != null) {
			return getParent().addAllAttributes(attributes);
		} else {
			return attributes;
		}
	}

	/**
	 * Return a map containing all the attributes accessible from this state. An
	 * attribute is accessible if it belongs to this state or to one of its
	 * ancestors.
	 * 
	 * @return a map containing all the attributes accessible from this state
	 */
	public Map<String, Object> getAllAttributes() {
		return addAllAttributes(new HashMap<String, Object>());
	}

	/**
	 * Set the value of an attribute accessible from this state. An attribute is
	 * accessible if it belongs to this state or to one of its ancestors.
	 * 
	 * @param tag
	 *            the attribute tag
	 * @param value
	 *            the new attribute value
	 * @throws IllegalStateException
	 *             if the state is not active
	 * @throws IllegalArgumentException
	 *             if the passed string is not the tag of an accessible
	 *             attribute
	 */
	public void setAttribute(String tag, Object value) throws WorkflowException {
		checkActive();
		if (metaState.isAllowedAttribute(tag)) {
			metaState.checkAttribute(tag, value);
			attributes.put(tag, value);
		} else {
			getParent().setAttribute(tag, value);
		}
	}

	private void doEntryAction() throws WorkflowException {
		try {
			metaState.getEntryAction().doAction(this);
		} catch (WorkflowException wfe) {
			throw wfe;
		} catch (Exception e) {
			throw new WorkflowException("SYS_EXCEPTION", e);
		}
	}

	private void doExitAction() throws WorkflowException {
		try {
			metaState.getExitAction().doAction(this);
		} catch (WorkflowException wfe) {
			throw wfe;
		} catch (Exception e) {
			throw new WorkflowException("SYS_EXCEPTION", e);
		}
	}

	public void enter() throws WorkflowException {
		super.enter();
		attributes = metaState.initialAttributesValues();
		try {
			doEntryAction();
		} catch (WorkflowException e) {
			reExit();
			throw e;
		}
	}

	protected void reEnter() {
		super.reEnter();
		metaState.getExitAction().undoAction(this);
	}

	public void exit() throws WorkflowException {
		try {
			doExitAction();
			super.exit();
		} catch (WorkflowException e) {
			reEnter();
			throw e;
		}
	}

	protected void reExit() {
		metaState.getEntryAction().undoAction(this);
		super.reExit();
	}

	public String toString() {
		return metaState.getTag();
	}

	public boolean equals(Object o) {
		boolean result = super.equals(o);
		if (result && this.isActive()) {
			RealState other = (RealState) o;
			result = this.attributes.equals(other.attributes);
		}
		return result;
	}

	public void restore(StateMemento memento) throws WorkflowException {
		super.enter();
		attributes = metaState.initialAttributesValues();
		for (Map.Entry<String,Object> curr: memento.getAttributes().entrySet()) {
			this.setAttribute((String) curr.getKey(), curr.getValue());
		}
	}

}

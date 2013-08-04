package it.amattioli.workstate.core;

import java.util.*;

import it.amattioli.workstate.actions.*;
import it.amattioli.workstate.exceptions.*;

/**
 * A MetaRealState is the configuration of a {@link RealState}, a state in which
 * a {@link Machine} can remain indefinitely. A real state must have an
 * identifier (called tag) and may have:
 * <ul>
 * <li>An entry action that will be executed when the state is entered
 * <li>An exit action that will be executed when the state is exited
 * <li>A set of attributes
 * </ul>
 * 
 */
public abstract class MetaRealState extends MetaState {
	private String tag;
	private StateAction entry;
	private StateAction exit;
	private Map<String, MetaAttribute> attributes = Collections.emptyMap();

	/**
	 * Build a new {@link MetaRealState} given its tag and its entry and exit
	 * action. The tag cannot be null or an empty string.
	 * 
	 * @param tag
	 *            this state identifier
	 * @param entry
	 *            the state entry action or null if no action must be executed
	 *            when the state is entered
	 * @param exit
	 *            the state exit action or null if no action must be executed
	 *            when the state is exited
	 * @throws NullPointerException
	 *             if the tag is null
	 * @throws IllegalArgumentException
	 *             if the tag is an empty string
	 */
	public MetaRealState(String tag, StateAction entry, StateAction exit) {
		setTag(tag);
		setEntryAction(entry);
		setExitAction(exit);
	}

	private void setTag(String tag) {
		if (tag == null) {
			throw ErrorMessages.newNullPointerException(ErrorMessage.NULL_META_STATE_TAG);
		} else if (tag.equals("")) {
			throw ErrorMessages.newIllegalArgumentException(ErrorMessage.EMPTY_META_STATE_TAG);
		}
		this.tag = tag.trim();
	}

	private void setEntryAction(StateAction entry) {
		if (entry == null) {
			this.entry = NullStateAction.getInstance();
		} else {
			this.entry = entry;
		}
	}

	private void setExitAction(StateAction exit) {
		if (exit == null) {
			this.exit = NullStateAction.getInstance();
		} else {
			this.exit = exit;
		}
	}

	public String getTag() {
		return this.tag;
	}

	public StateAction getEntryAction() {
		return this.entry;
	}

	public StateAction getExitAction() {
		return this.exit;
	}

	/**
	 * Add an attribute definition to this {@link MetaState}.
	 * 
	 * @param newAttribute
	 *            the new attribute definition
	 * @throws NullPointerException
	 *             if the newAttribute parameter is null
	 * @throws IllegalArgumentException
	 *             if an attribute with the same tag already exists
	 */
	public void addAttribute(MetaAttribute newAttribute) {
		if (newAttribute == null) {
			throw ErrorMessages.newNullPointerException(ErrorMessage.NULL_META_ATTRIBUTE);
		}
		if (isAllowedAttribute(newAttribute.getTag())) {
			throw ErrorMessages.newIllegalArgumentException(ErrorMessage.EXISTING_ATTR_TAG, newAttribute.getTag(), this.getTag());
		}
		if (attributes.isEmpty()) {
			attributes = new HashMap<String, MetaAttribute>();
		}
		attributes.put(newAttribute.getTag(), newAttribute);
	}

	/**
	 * Check if this {@link MetaState} has a parameter.
	 * 
	 * @param attributeName
	 *            the parameter tag
	 * @return true if this {@link MetaState} has a parameter with the given tag
	 */
	public boolean isAllowedAttribute(String attributeName) {
		return attributes.containsKey(attributeName);
	}

	public void checkAttribute(String attributeName, Object attributeValue) throws WorkflowException {
		MetaAttribute metaAttribute = (MetaAttribute) attributes.get(attributeName);
		if (metaAttribute == null) {
			throw ErrorMessages.newIllegalArgumentException(ErrorMessage.NON_EXISTING_ATTR_TAG, attributeName, this.getTag());
		}
		metaAttribute.checkValidAttribute(attributeValue);
	}

	public Map<String, Object> initialAttributesValues() {
		Map<String, Object> result = new HashMap<String, Object>();
		for (Map.Entry<String, MetaAttribute> current: attributes.entrySet()) {
			Object initVal = current.getValue().getInitialValue();
			result.put((String) current.getKey(), initVal);
		}
		return result;
	}

	public String toString() {
		return this.getTag();
	}

	/**
	 * Two MetaStates are equals if they have the same class and the same tag.
	 * 
	 */
	public boolean equals(Object o) {
		boolean result = false;
		if (o != null && this.getClass().equals(o.getClass())) {
			MetaRealState other = (MetaRealState) o;
			result = this.getTag().equals(other.getTag());
		}
		return result;
	}

}
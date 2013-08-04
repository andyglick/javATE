package it.amattioli.workstate.core;

import java.util.*;

import it.amattioli.workstate.exceptions.*;

/**
 * A {@link MetaEvent} is the configuration of an {@link Event} that can be sent
 * to a {@link Machine}.
 * <p>
 * Each MetaEvent has a tag that identifies it and a set of
 * {@link MetaAttribute} that are its parameters.
 * <p>
 * 
 */
public class MetaEvent {
	public static final MetaEvent NULL = new MetaEvent("NULL");
	private String tag;
	private Map<String, MetaAttribute> parameters = new HashMap<String, MetaAttribute>();
	private Collection<EventValidator> validators = Collections.emptyList();

	/**
	 * Build a new {@link MetaEvent} given its tag.
	 * 
	 * @param tag
	 *            the MetaEvent tag
	 * @throws NullPointerException
	 *             if the tag is null
	 * @throws IllegalArgumentException
	 *             if the tag is an empty sting
	 */
	public MetaEvent(String tag) {
		if (tag == null) {
			throw ErrorMessages.newNullPointerException(ErrorMessage.NULL_META_EVENT_TAG);
		}
		if (tag.equals("")) {
			throw ErrorMessages.newIllegalArgumentException(ErrorMessage.EMPTY_META_EVENT_TAG);
		}
		this.tag = tag.trim();
	}

	/**
	 * Returns this MetaEvent tag
	 * 
	 * @return this MetaEvent tag.
	 */
	public String getTag() {
		return tag;
	}

	/**
	 * Add a new parameter to this MetaEvent.
	 * 
	 * @param the
	 *            parameter to be added
	 * @throws IllegalArgumentException
	 *             if a parameter with the same tag already exists
	 */
	public void addParameter(MetaAttribute param) {
		if (parameters.containsKey(param.getTag())) {
			throw ErrorMessages.newIllegalArgumentException(ErrorMessage.EXISTING_PARAMETER, param.getTag(), tag);
		}
		parameters.put(param.getTag(), param);
	}

	/**
	 * Returns the given parameter definition.
	 * 
	 * @param tag
	 *            the desired parameter tag
	 * @return the given parameter definition or null if no parameter has been
	 *         set that has the given tag
	 */
	public MetaAttribute getParameter(String tag) {
		return (MetaAttribute) parameters.get(tag);
	}

	public void addValidator(EventValidator validator) {
		if (validators.isEmpty()) {
			validators = new ArrayList<EventValidator>();
		}
		validators.add(validator);
	}

	public void checkValidParameter(String tag, Object value)
			throws WorkflowException {
		MetaAttribute metaAttr = (MetaAttribute) parameters.get(tag);
		if (metaAttr != null) {
			metaAttr.checkValidAttribute(value);
		} else {
			throw ErrorMessages.newIllegalArgumentException(ErrorMessage.ILLEGAL_PARAMETER, tag, this.tag);
		}
	}

	public void checkValidEvent(Event event) throws WorkflowException {
		if (!event.hasMetaEvent(this)) {
			throw ErrorMessages.newIllegalArgumentException(ErrorMessage.WRONG_META_EVENT);
		}
		for (EventValidator validator: validators) {
			validator.validate(event);
		}
	}

	/**
	 * Returns the actual parameters of an event given a map of the parameters
	 * passed by the user.
	 * <p>
	 * For parameters that have been defined but for which there is no parameter
	 * in the given Map, the initial value will be used.
	 * 
	 * @param params
	 *            a map containing the parameters passed by the user. The keys
	 *            are the parameter tag while the values are the parameter
	 *            values. If null is passed it will be considered as an empty
	 *            map.
	 * @throws IllegalArgumentException
	 *             if the map contains at least a key that is not the tag of a
	 *             parameter of this event
	 * @throws ClassCastException
	 *             if the class of at least one of the passed parameter is not
	 *             compatible with the corresponding parameter definition
	 */
	public Map<String, Object> initParameters(Map<String, Object> params) throws WorkflowException {
		Map<String, Object> parameters = params;
		if (parameters == null) {
			parameters = Collections.emptyMap();
		}
		Map<String, Object> actualParams = new HashMap<String, Object>();
		for (Map.Entry<String, MetaAttribute> curr: this.parameters.entrySet()) {
			if (parameters.get(curr.getKey()) == null) {
				actualParams.put(curr.getKey(), ((MetaAttribute) curr.getValue()).getInitialValue());
			} else {
				checkValidParameter((String) curr.getKey(), parameters.get(curr.getKey()));
				actualParams.put(curr.getKey(), parameters.get(curr.getKey()));
			}
		}
		return actualParams;
	}

	/**
	 * Build a new {@link Event} using this MetaEvent as its definition.
	 * 
	 * @param params
	 *            a map containing the parameters passed by the user. The keys
	 *            are the parameter tag while the values are the parameter
	 *            values. If null is passed it will be considered as an empty
	 *            map.
	 * @throws IllegalArgumentException
	 *             if the map contains at least a key that is not the tag of a
	 *             parameter of this event
	 * @throws ClassCastException
	 *             if the class of at least one of the passed parameter is not
	 *             compatible with the corresponding parameter definition
	 */
	public Event newEvent(Map<String, Object> parameters) throws WorkflowException {
		Event result = new Event(this, initParameters(parameters));
		checkValidEvent(result);
		return result;
	}

	/**
	 * Check if a parameter exists.
	 * 
	 * @param tag
	 *            the parameter tag
	 * @return true if this MetaEvent has a parameter with the given tag, false
	 *         otherwise
	 */
	public boolean isAllowedParameter(String tag) {
		return parameters.containsKey(tag);
	}

	/**
	 * Return a string representation of this MetaEvent. The string will consist
	 * of the MetaEvent tag followed by the parameters between parentheses.
	 * 
	 * @return the string representation of this MetaEvent
	 */
	public String toString() {
		StringBuffer buffer = new StringBuffer(getTag());
		buffer.append("(");
		boolean first = true;
		for (MetaAttribute curr: parameters.values()) {
			if (!first) {
				buffer.append(",");
			}
			buffer.append(curr.toString());
			first = false;
		}
		buffer.append(")");
		return buffer.toString();
	}

	/**
	 * Two MetaEvents are considered equals if they have the same tag.
	 */
	public boolean equals(Object obj) {
		boolean result = false;
		if (obj instanceof MetaEvent) {
			MetaEvent me = (MetaEvent) obj;
			result = me.getTag().equals(this.getTag());
		}
		return result;
	}

}
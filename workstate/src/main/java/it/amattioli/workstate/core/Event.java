package it.amattioli.workstate.core;

import java.util.*;

import it.amattioli.workstate.actions.*;
import it.amattioli.workstate.exceptions.*;

/**
 * Represents an event that can be received by a state machine. An event is
 * identified by its {@link MetaEvent} and by its actual parameters. The actual
 * parameters values must be compatible with the formal parameters defined in
 * the {@link MetaEvent}.
 */
public class Event implements AttributeReader {
	/**
	 * This constant is a null event. It can be used as a Null Object.
	 * 
	 */
	public static final Event NULL = new Event(MetaEvent.NULL, Collections.EMPTY_MAP);

	private MetaEvent metaEvent;
	private Map<String,Object> parameters;

	/**
	 * Construct a new event given its {@link MetaEvent} and its actual
	 * parameters. In the parameters map the keys are the parameter names as
	 * defined in the {@link MetaEvent} while the values are the parameters
	 * values.
	 * <p>
	 * No check on parameters is performed by this constructor. The caller must
	 * ensure that the parameters are valid and compatible with the formal
	 * parameters defined in the {@link MetaEvent}.
	 * 
	 */
	public Event(MetaEvent metaEvent, Map<String,Object> parameters) {
		if (metaEvent == null) {
			throw ErrorMessages.newNullPointerException(ErrorMessage.NULL_META_EVENT);
		}
		this.metaEvent = metaEvent;
		this.parameters = parameters;
	}

	/**
	 * Verifies if this event is associated with a given {@link MetaEvent}.
	 * 
	 * @param metaEvent the {@link MetaEvent}
	 * @return true if the parameter is the {@link MetaEvent} associted to this
	 *         event, false otherwise
	 */
	public boolean hasMetaEvent(MetaEvent metaEvent) {
		return this.metaEvent.equals(metaEvent);
	}

	public boolean isAllowedParameter(String tag) {
		return metaEvent.isAllowedParameter(tag);
	}

	/**
	 * Return the event parameter value whose tag is the passed string.
	 * 
	 * @param tag
	 *            the tag of the parameter
	 * @return the parameter value
	 * @throws IllegalArgumentException
	 *             if this event has no parameter with the given tag
	 */
	public Object getParameter(String tag) {
		if (!metaEvent.isAllowedParameter(tag)) {
			throw ErrorMessages.newIllegalArgumentException(ErrorMessage.NON_EXISTING_PARAM_TAG);
		}
		return parameters.get(tag);
	}

	/**
	 * Return the event parameter value whose tag is the passed string.
	 * 
	 */
	public Object getAttribute(String tag) {
		return getParameter(tag);
	}

	public Map<String,Object> getAllAttributes() {
		return parameters;
	}

	/**
	 * Return the string representation of this event. This representation will
	 * be the event tag followed by its parameters. Every parameter will be
	 * "tag = value".
	 * 
	 * @return the string representation of this event.
	 */
	public String toString() {
		StringBuffer buffer = new StringBuffer(metaEvent.getTag());
		buffer.append("(");
		boolean first = true;
		for (Map.Entry<String,Object> curr: parameters.entrySet()) {
			if (!first) {
				buffer.append(",");
			}
			buffer.append((String) curr.getKey());
			buffer.append("=");
			buffer.append(curr.getValue());
			first = false;
		}
		buffer.append(")");
		return buffer.toString();
	}

}
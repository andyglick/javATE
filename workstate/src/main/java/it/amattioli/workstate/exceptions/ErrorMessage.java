package it.amattioli.workstate.exceptions;

/**
 * An enumeration containing all the possible error messages that the
 * framwork could throw.
 * 
 */
public class ErrorMessage {
	private String name;

	private ErrorMessage(String name) {
		this.name = name;
	}

	public String name() {
		return name;
	}

	public String toString() {
		return name;
	}

	public static final ErrorMessage NULL_STATE_DESCEND = new ErrorMessage(
			"NULL_STATE_DESCEND");
	public static final ErrorMessage NULL_TRANSITION = new ErrorMessage(
			"NULL_TRANSITION");
	public static final ErrorMessage NULL_META_STATE_TAG = new ErrorMessage(
			"NULL_META_STATE_TAG");
	public static final ErrorMessage NULL_META_ATTRIBUTE = new ErrorMessage(
			"NULL_META_ATTRIBUTE");
	public static final ErrorMessage NULL_NEGATING_GUARD = new ErrorMessage(
			"NULL_NEGATING_GUARD");
	public static final ErrorMessage NULL_SUB_META_STATE = new ErrorMessage(
			"NULL_SUB_META_STATE");
	public static final ErrorMessage NULL_STATE_ANCESTOR = new ErrorMessage(
			"NULL_STATE_ANCESTOR");
	public static final ErrorMessage NULL_ATTRIBUTE_CLASS = new ErrorMessage(
			"NULL_ATTRIBUTE_CLASS");
	public static final ErrorMessage NULL_META_EVENT_TAG = new ErrorMessage(
			"NULL_META_EVENT_TAG");
	public static final ErrorMessage NULL_SOURCE_STATE = new ErrorMessage(
			"NULL_SOURCE_STATE");
	public static final ErrorMessage NULL_TARGET_STATE = new ErrorMessage(
			"NULL_TARGET_STATE");
	public static final ErrorMessage EMPTY_META_EVENT_TAG = new ErrorMessage(
			"EMPTY_META_EVENT_TAG");
	public static final ErrorMessage INVALID_ATTRIBUTE_CLASS = new ErrorMessage(
			"INVALID_ATTRIBUTE_CLASS");
	public static final ErrorMessage INVALID_ATTRIBUTE_VALUE_CLASS = new ErrorMessage(
			"INVALID_ATTRIBUTE_VALUE_CLASS");
	public static final ErrorMessage INVALID_ATTRIBUTE_INIT_VALUE = new ErrorMessage(
			"INVALID_ATTRIBUTE_INIT_VALUE");
	public static final ErrorMessage EMPTY_META_STATE_TAG = new ErrorMessage(
			"EMPTY_META_STATE_TAG");
	public static final ErrorMessage NON_SEQ_CONCURRENT_SUBSTATE = new ErrorMessage(
			"NON_SEQ_CONCURRENT_SUBSTATE");
	public static final ErrorMessage EXISTING_META_TAG = new ErrorMessage(
			"EXISTING_META_TAG");
	public static final ErrorMessage NON_EXISTING_META_TAG = new ErrorMessage(
			"NON_EXISTING_META_TAG");
	public static final ErrorMessage EXISTING_ATTR_TAG = new ErrorMessage(
			"EXISTING_ATTR_TAG");
	public static final ErrorMessage NON_EXISTING_ATTR_TAG = new ErrorMessage(
			"NON_EXISTING_ATTR_TAG");
	public static final ErrorMessage UNIQUE_META_TYPE = new ErrorMessage(
			"UNIQUE_META_TYPE");
	public static final ErrorMessage NO_INITIAL_STATE = new ErrorMessage(
			"NO_INITIAL_STATE");
	public static final ErrorMessage WRONG_PARENT_META_STATE = new ErrorMessage(
			"WRONG_PARENT_META_STATE");
	public static final ErrorMessage OVERLAPPING_TRANSITION = new ErrorMessage(
			"OVERLAPPING_TRANSITION");
	public static final ErrorMessage NULL_META_EVENT = new ErrorMessage(
			"NULL_META_EVENT");
	public static final ErrorMessage INVALID_ATTRIBUTE = new ErrorMessage(
			"INVALID_ATTRIBUTE");
	public static final ErrorMessage NULL_ENTRY_STACK = new ErrorMessage(
			"NULL_ENTRY_STACK");
	public static final ErrorMessage EMPTY_ENTRY_STACK = new ErrorMessage(
			"EMPTY_ENTRY_STACK");
	public static final ErrorMessage ACTIVE_CURRENT_STATE = new ErrorMessage(
			"ACTIVE_CURRENT_STATE");
	public static final ErrorMessage NON_SEQUENTIAL_STATE_ENTRY = new ErrorMessage(
			"NON_SEQUENTIAL_STATE_ENTRY");
	public static final ErrorMessage NULL_META_STATE = new ErrorMessage(
			"NULL_META_STATE");
	public static final ErrorMessage STATE_ALREADY_ACTIVE = new ErrorMessage(
			"STATE_ALREADY_ACTIVE");
	public static final ErrorMessage NON_EXISTING_ANCESTOR = new ErrorMessage(
			"NON_EXISTING_ANCESTOR");
	public static final ErrorMessage NON_ACTIVE_STATE = new ErrorMessage(
			"NON_ACTIVE_STATE");
	public static final ErrorMessage NO_NULL_PARENT_META_STATE = new ErrorMessage(
			"NO_NULL_PARENT_META_STATE");
	public static final ErrorMessage NO_TRANSITION_ACTIVATED = new ErrorMessage(
			"NO_TRANSITION_ACTIVATED");
	public static final ErrorMessage EVENT_TO_PSEUDOSTATE = new ErrorMessage(
			"EVENT_TO_PSEUDOSTATE");
	public static final ErrorMessage ILLEGAL_PARAMETER = new ErrorMessage(
			"ILLEGAL_PARAMETER");
	public static final ErrorMessage EXISTING_PARAMETER = new ErrorMessage(
			"EXISTING_PARAMETER");
	public static final ErrorMessage NON_EXISTING_PARAM_TAG = new ErrorMessage(
			"NON_EXISTING_PARAM_TAG");
	public static final ErrorMessage UNKNOWN_EVENT = new ErrorMessage(
			"UNKNOWN_EVENT");
	public static final ErrorMessage WRONG_META_EVENT = new ErrorMessage(
			"WRONG_META_EVENT");
	public static final ErrorMessage SYNTAX_ERROR = new ErrorMessage(
			"SYNTAX_ERROR");
	public static final ErrorMessage ILLEGAL_INTERNAL_TRANSITION_STATE = new ErrorMessage(
			"ILLEGAL_INTERNAL_TRANSITION_STATE");
	public static final ErrorMessage WRONG_CLASS = new ErrorMessage(
			"WRONG_CLASS");
	public static final ErrorMessage OBJECT_ALREADY_BUILT = new ErrorMessage(
			"OBJECT_ALREADY_BUILT");
	public static final ErrorMessage ONE_ROOT_STATE_ONLY = new ErrorMessage(
			"ONE_ROOT_STATE_ONLY");
	public static final ErrorMessage UNKNOWN_STATE_TYPE = new ErrorMessage(
			"UNKNOWN_STATE_TYPE");
	public static final ErrorMessage SUBSTATE_NOT_ALLOWED = new ErrorMessage(
			"SUBSTATE_NOT_ALLOWED");
	public static final ErrorMessage UNMATCHED_END_STATE = new ErrorMessage(
			"UNMATCHED_END_STATE");
	public static final ErrorMessage NO_BREAK_STATE = new ErrorMessage(
			"NO_BREAK_STATE");
	public static final ErrorMessage WRONG_OWNER_CLASS = new ErrorMessage(
			"WRONG_OWNER_CLASS");
	public static final ErrorMessage SUB_MACHINE_NOT_ALLOWED = new ErrorMessage(
			"SUB_MACHINE_NOT_ALLOWED");
	public static final ErrorMessage STATEACTION_OUT_OF_STATE = new ErrorMessage(
			"STATEACTION_OUT_OF_STATE");
	public static final ErrorMessage ILLEGAL_ATTR_OWNER = new ErrorMessage(
			"ILLEGAL_ATTR_OWNER");
	public static final ErrorMessage NESTED_ATTRIBUTE = new ErrorMessage(
			"NESTED_ATTRIBUTE");
	public static final ErrorMessage UNMATCHED_END_ATTR = new ErrorMessage(
			"UNMATCHED_END_ATTR");
	public static final ErrorMessage NESTED_EVENT = new ErrorMessage(
			"NESTED_EVENT");
	public static final ErrorMessage UNMATCHED_END_EVENT = new ErrorMessage(
			"UNMATCHED_END_EVENT");
	public static final ErrorMessage NESTED_TRANSITION = new ErrorMessage(
			"NESTED_TRANSITION");
	public static final ErrorMessage UNMATCHED_END_TRANSITION = new ErrorMessage(
			"UNMATCHED_END_TRANSITION");
	public static final ErrorMessage ACTION_OUT_OF_TRANSITION = new ErrorMessage(
			"ACTION_OUT_OF_TRANSITION");
	public static final ErrorMessage GUARD_OUT_OF_TRANSITION = new ErrorMessage(
			"GUARD_OUT_OF_TRANSITION");

	public static final ErrorMessage UNPARSABLE_OBJECT = new ErrorMessage(
			"UNPARSABLE_OBJECT");
	public static final ErrorMessage UNFORMATTABLE_OBJECT = new ErrorMessage(
			"UNFORMATTABLE_OBJECT");

	public static final ErrorMessage TEST_READ_EXCEPTION = new ErrorMessage(
			"TEST_READ_EXCEPTION");
	public static final ErrorMessage NO_MORE_STEPS = new ErrorMessage(
			"NO_MORE_STEPS");
}

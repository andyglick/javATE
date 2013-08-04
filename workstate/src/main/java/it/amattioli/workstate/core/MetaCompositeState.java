package it.amattioli.workstate.core;

import java.util.*;

import it.amattioli.workstate.actions.*;
import it.amattioli.workstate.exceptions.*;
import it.amattioli.workstate.info.Visitor;

/**
 * A MetaCompositeState is the configuration of a state that can have
 * sub-states. This abstract class has facility methods to manipulate these
 * sub-states: adding a sub-state, checking a sub-state existence, etc.
 * 
 */
public abstract class MetaCompositeState extends MetaRealState {
	private Collection<MetaState> substates = Collections.emptyList();

	/**
	 * Construct a new MetaCompositeState given its identifier (tag) and its
	 * entry and exit action.
	 * 
	 * @param tag this state identifier
	 * @param entry this state entry action. Passing null means no action should
	 *        be performed
	 * @param exit this state exit action. Passing null means no action should be
	 *        performed
	 * @throws NullPointerException if the tag is null
	 */
	public MetaCompositeState(String tag, StateAction entry, StateAction exit) {
		super(tag, entry, exit);
	}

	/**
	 * Verifies that a {@link MetaState} can be a sub-state of this. This method
	 * should be called before an attempt to add a new substate to this and
	 * verifies that this action is allowed or not. The implementation in this
	 * class verifies that there is not another sub-state with the same tag but
	 * subclasses are free to redefine this method as long as they call
	 * super.checkAllowedSubstate. if the passed {@link MetaState} can be a
	 * sub-state of this the method will end normally, otherwise an
	 * IllegalArgumentException is thrown.
	 * 
	 * @param newMetaState the {@link MetaState} that shoud be checked
	 * @throws IllegalArgumentException if the argument cannot be a sub-state 
	 *         of this state
	 */
	protected void checkAllowedSubstate(MetaState newMetaState) {
		if (newMetaState instanceof MetaRealState) {
			MetaRealState newMetaRealState = (MetaRealState) newMetaState;
			if (isSubstate(newMetaRealState.getTag())) {
				throw ErrorMessages.newIllegalArgumentException(
						ErrorMessage.EXISTING_META_TAG, newMetaRealState
								.getTag(), this.getTag());
			}
		}
	}

	/**
	 * Add a sub-state to this state. Before adding the sub-state this method
	 * check that the passed meta-state can be a sub-state of this state calling
	 * {@link #checkAllowedSubstate(MetaState)}.
	 * 
	 * @param newMetaState the new sub-state
	 * @throws NullPointerException if the parameter is null
	 * @throws IllegalArgumentException if the parameter cannot be a sub-state 
	 *         of this state
	 */
	public void addMetaState(MetaState newMetaState) {
		if (newMetaState == null) {
			throw ErrorMessages.newNullPointerException(ErrorMessage.NULL_SUB_META_STATE);
		}
		checkAllowedSubstate(newMetaState);
		if (substates.isEmpty()) {
			substates = new ArrayList<MetaState>();
		}
		substates.add(newMetaState);
		newMetaState.setParent(this);
	}

	/**
	 * Check if this meta-state is a super-state of the passed one.
	 * 
	 * @param metaState
	 * @throws NullPointerException if the parameter is null
	 */
	public boolean isAncestorOf(MetaState metaState) {
		if (metaState == null) {
			throw ErrorMessages
					.newNullPointerException(ErrorMessage.NULL_STATE_ANCESTOR);
		}
		return metaState.descendFrom(this);
	}

	/**
	 * Returns an (unmodifiable) collection containing the sub-state of this
	 * state.
	 * 
	 * @return the sub-states of this state
	 */
	public Collection<MetaState> getSubstates() {
		return Collections.unmodifiableCollection(substates);
	}

	/**
	 * Returns, if exists, the sub-state of this state with a given tag. If such
	 * a state does not exists, null is returned.
	 * 
	 */
	private MetaState findSubstate(String tag) {
		MetaState result = null;
		for (MetaState current: getSubstates()) {
			if (current instanceof MetaRealState
					&& ((MetaRealState) current).getTag().equals(tag)) {
				result = current;
				break;
			}
		}
		return result;
	}

	/**
	 * Verifies if this state has a sub-state whose tag is passed as a
	 * parameter.
	 * 
	 * @param tag the tag of the sub-state
	 * @return true if this state has a sub-state whose tag is passed as a
	 *         parameter, false otherwise
	 */
	public boolean isSubstate(String tag) {
		MetaState result = findSubstate(tag);
		return result != null;
	}

	/**
	 * Returns, if exists, the sub-state of this state with a given tag. If such
	 * a state does not exists, an {@link IllegalArgumentException} is thrown.
	 */
	public MetaState getSubstate(String tag) {
		MetaState result = findSubstate(tag);
		if (result == null) {
			throw ErrorMessages.newIllegalArgumentException(
					ErrorMessage.NON_EXISTING_META_TAG, tag, this.getTag());
		}
		return result;
	}

	@Override
	public void receive(Visitor visitor) {
		for (MetaState curr: substates) {
			if (curr instanceof MetaRealState) {
				visitor.visit((MetaRealState)curr);
			}
		}
	}

}
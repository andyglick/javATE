package it.amattioli.workstate.core;

import it.amattioli.workstate.exceptions.*;

import java.util.*;

public class SequentialState extends CompositeState {
	public static final String CURRENT_STATE_TAG = "CURRENT_STATE";
	private MetaSequentialState metaState;

	public SequentialState(MetaSequentialState metaState, CompositeState parent) {
		super(metaState, parent);
		this.metaState = metaState;
	}

	/**
	 * Return the current sub-state of this state. At each time a sequential
	 * state is in one and only one sub-state. This method allows to know which
	 * is this sub-state. The current sub-state makes sense only for active
	 * states so, before entering and after entering this state the current
	 * sub-state will be null.
	 * 
	 * @return the current sub-state of this state
	 * 
	 */
	public State getCurrentState() {
		return (State) substates.get(CURRENT_STATE_TAG);
	}

	private void setCurrentState(State currentState) {
		substates.put(CURRENT_STATE_TAG, currentState);
	}

	/**
	 * Enter this state and execute the transition associated to the initial
	 * state.
	 * 
	 */
	public void enter() throws WorkflowException {
		/*
		 * super.enter(); MetaInitialState metaInitial =
		 * metaState.getInitialState(); InitialState initial =
		 * metaInitial.newState(this); substates.put(CURRENT_STATE_TAG,initial);
		 * try { initial.enter(); } catch(WorkflowException e) {
		 * setCurrentState(null); reExit(); throw e; }
		 */
		Stack<MetaState> entryStack = new Stack<MetaState>();
		entryStack.push(metaState.getInitialState());
		enter(entryStack);
	}

	/**
	 * Enter in this state and position itself in one of its sub-states. The
	 * given stack must contain a sequence of the sub-states that must be
	 * entered, the same as for {@link #enterCurrent(Stack)}. The difference
	 * between this method and {@link #enterCurrent(Stack)} is that this method
	 * assumes that this state is not active so enters it and activate it while
	 * {@link #enterCurrent(Stack)} assumes this state is already active.
	 * 
	 * @throws WorkflowException
	 *             if an action throws an exception
	 */
	public void enter(Stack<MetaState> entryStack) throws WorkflowException {
		super.enter();
		try {
			enterCurrent(entryStack);
		} catch (WorkflowException e) {
			setCurrentState(null);
			reExit();
			throw e;
		}
	}

	public void exitCurrent() throws WorkflowException {
		State oldState = getCurrentState();
		oldState.exit();
	}

	public void reEnterCurrent() throws WorkflowException {
		State oldState = getCurrentState();
		oldState.reEnter();
	}

	/**
	 * Change the current sub-state of this state. The given stack must contain
	 * the list of the tags of {@link MetaState} to enter. This method will set
	 * as current sub-state the one whose tag is on the top of the stack and
	 * will enter it passing the same stack after removing the top element. In
	 * this way the sub-state will set as its sub-state the one whose tag was in
	 * second position, and so on. Before calling this method
	 * {@link #exitCurrent()} must be called so to exit the current sub-state.
	 * 
	 * @throws NullPointerException
	 *             if the parameter is null
	 * @throws IllegalArgumentException
	 *             if the stack is empty or contains an invalid tag
	 * @throws IllegalStateException
	 *             if the current sub-state is still active
	 * @throws WorkflowException
	 *             if an action throws an exception
	 */
	public void enterCurrent(Stack<MetaState> entryStack) throws WorkflowException {
		if (entryStack == null) {
			throw ErrorMessages.newNullPointerException(ErrorMessage.NULL_ENTRY_STACK);
		}
		if (entryStack.empty()) {
			throw ErrorMessages.newIllegalArgumentException(ErrorMessage.EMPTY_ENTRY_STACK);
		}
		State oldState = getCurrentState();
		if (oldState != null && oldState.isActive()) {
			throw ErrorMessages.newIllegalStateException(ErrorMessage.ACTIVE_CURRENT_STATE);
		}
		MetaState metaSubstate = (MetaState) entryStack.pop();
		State newState = metaSubstate.newState(this);
		setCurrentState(newState);
		try {
			if (newState instanceof SequentialState && !entryStack.empty()) {
				((SequentialState) newState).enter(entryStack);
			} else {
				if (!entryStack.empty()) {
					throw ErrorMessages.newIllegalArgumentException(ErrorMessage.NON_SEQUENTIAL_STATE_ENTRY, newState.toString());
				}
				newState.enter();
			}
		} catch (WorkflowException e) {
			setCurrentState(oldState);
			entryStack.push(metaSubstate);
			throw e;
		}
	}

	public void restore(StateMemento memento) throws WorkflowException {
		super.restore(memento);
		Map<String,StateMemento> children = memento.getChildren();
		if (children.size() == 0) {
			enter();
		} else if (children.size() == 1) {
			for (Map.Entry<String,StateMemento> curr: children.entrySet()) {
				MetaState childMetaState = metaState.getSubstate(curr.getKey());
				State childState = childMetaState.newState(this);
				State oldState = getCurrentState();
				setCurrentState(childState);
				try {
					childState.restore(curr.getValue());
				} catch (WorkflowException e) {
					setCurrentState(oldState);
					throw e;
				}
			}
		} else {
			// TODO: Eccezione!!!!!!
		}
	}

	public boolean isRegion() {
		return getParent() instanceof ConcurrentState;
	}

	public void terminate() throws WorkflowException {
		super.terminate();
		if (isRegion()) {
			getParent().terminate();
		}
	}

}

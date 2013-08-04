package it.amattioli.workstate.core;

import java.util.*;

import it.amattioli.workstate.actions.*;
import it.amattioli.workstate.exceptions.*;

/**
 * A transition allows state changes when an event is received.
 * 
 */
public abstract class Transition {
	private MetaState start;
	private MetaState end;
	private TransitionAction action;
	private MetaEvent trigger;
	private Guard guard;

	public Transition(MetaEvent event, MetaState start, MetaState end, TransitionAction action, Guard guard) {
		setEvent(event);
		setAction(action);
		setGuard(guard);
		setStartState(start);
		setEndState(end);
		start.addTransition(this);
	}

	public MetaEvent getEvent() {
		return trigger;
	}

	private void setEvent(MetaEvent event) {
		if (event == null) {
			this.trigger = MetaEvent.NULL;
		} else {
			this.trigger = event;
		}
	}

	public boolean isEvent(MetaEvent event) {
		return this.trigger.equals(event);
	}

	private void setAction(TransitionAction action) {
		if (action == null) {
			this.action = NullTransitionAction.getInstance();
		} else {
			this.action = action;
		}
	}

	public boolean isAction(TransitionAction action) {
		return this.action.equals(action);
	}

	private void setGuard(Guard guard) {
		if (guard == null) {
			this.guard = NullGuard.getInstance();
		} else {
			this.guard = guard;
		}
	}

	public boolean isGuard(Guard guard) {
		return this.guard.equals(guard);
	}

	private void setStartState(MetaState start) {
		if (start == null) {
			throw ErrorMessages.newNullPointerException(ErrorMessage.NULL_SOURCE_STATE);
		}
		this.start = start;
	}

	protected MetaState getStartState() {
		return this.start;
	}

	public boolean isStartState(MetaState state) {
		return this.start.equals(state);
	}

	private void setEndState(MetaState end) {
		if (start == null) {
			throw ErrorMessages.newNullPointerException(ErrorMessage.NULL_TARGET_STATE);
		}
		this.end = end;
	}

	protected MetaState getEndState() {
		return this.end;
	}

	public boolean isEndState(MetaState state) {
		return this.end.equals(state);
	}

	/**
	 * Check if the given transition has the same trigger of this one. To have
	 * the same trigger to transitions must be triggered by the same
	 * {@link MetaEvent} and must have the same {@link Guard}.
	 * 
	 */
	public boolean hasSameTrigger(Transition transition) {
		return ((this.trigger == null && transition.trigger == null) || (this.trigger != null && this.trigger.equals(transition.trigger)))
				&& this.guard.equals(transition.guard);
	}

	/**
	 * Check if this transition is triggered by a given {@link Event} when the
	 * machine is in a giben {@link State}. The following will be checked:
	 * <ul>
	 * <li>That this transition is triggered by the given event
	 * <li>That the given state is the start state of this transition
	 * <li>That the guard of this transition is satisfied
	 * </ul>
	 * 
	 * @param event
	 *            the received event
	 * @param state
	 *            the current state
	 * @return true if this transition is triggered, false otherwise
	 */
	public boolean isTriggeredBy(Event event, State state) {
		return event.hasMetaEvent(this.trigger) && state.hasMetaState(this.start) && guard.check(event, state);
	}

	protected void doAction(Event event, RealState currentState) throws WorkflowException {
		try {
			action.doAction(event, currentState);
		} catch (WorkflowException wfe) {
			undoAction(event, currentState);
			throw wfe;
		} catch (Exception e) {
			undoAction(event, currentState);
			throw new WorkflowException("SYS_EXCEPTION", e);
		}
	}

	protected void undoAction(Event event, RealState currentState) {
		action.undoAction(event, currentState);
	}

	/**
	 * Perform this transition.
	 */
	public abstract void perform(Event event, State currentState) throws WorkflowException;

	/**
	 * Return a {@link Comparator} instance that allows the ordering of a list
	 * of transitions based on the guard priority (high priority first).
	 * 
	 */
	public static Comparator<Transition> getGuardPriorityComparator() {
		return new Comparator<Transition>() {
			public int compare(Transition t1, Transition t2) {
				return -t1.guard.getPriority().compareTo(t2.guard.getPriority());
			}
		};
	}

	public boolean equals(Object o) {
		boolean result = false;
		if (o instanceof Transition) {
			Transition t = (Transition) o;
			result = start.equals(t.start);
			result = result && end.equals(t.end);
			result = result && action.equals(t.action);
			result = result && trigger.equals(t.trigger);
			result = result && guard.equals(t.guard);
		}
		return result;
	}

	public String toString() {
		StringBuffer buffer = new StringBuffer("from ");
		buffer.append(start == null ? "null" : start.toString());
		buffer.append(" to ");
		buffer.append(end == null ? "null" : end.toString());
		buffer.append(" when ");
		buffer.append(trigger == null ? "null" : trigger.toString());
		buffer.append("[");
		buffer.append(guard == null ? "null" : guard.toString());
		buffer.append("]");
		return buffer.toString();
	}

}
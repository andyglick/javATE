package it.amattioli.workstate.core;

import it.amattioli.workstate.exceptions.*;
import it.amattioli.workstate.info.*;

import java.util.*;

public abstract class CompositeState extends RealState {
	private MetaCompositeState metaState;
	protected Map<String, State> substates = new HashMap<String, State>();
	private boolean complete = false;

	public CompositeState(MetaCompositeState metaState, CompositeState parent) {
		super(metaState, parent);
		this.metaState = metaState;
	}

	public void exit() throws WorkflowException {
		try {
			for (State curr: substates.values()) {
				curr.exit();
			}
		} catch (WorkflowException e) {
			reEnterSubstates();
			throw e;
		}
		super.exit();
	}

	private void reEnterSubstates() {
		for (State curr: substates.values()) {
			if (!curr.isActive()) {
				curr.reEnter();
			}
		}
	}

	protected void reEnter() {
		reEnterSubstates();
		super.reEnter();
	}

	public void receiveEvent(Event event) throws WorkflowException {
		if (!isComplete()) {
			Transition transition = metaState.findTriggeredTransition(event,
					this);
			if (transition != null) {
				transition.perform(event, this);
			} else {
				for (State curr: substates.values()) {
					curr.receiveEvent(event);
				}
			}
		}
	}

	public boolean admitEvent(Event event) {
		Transition transition = metaState.findTriggeredTransition(event, this);
		if (transition != null) {
			return true;
		} else {
			for (State curr: substates.values()) {
				if (curr.admitEvent(event)) {
					return true;
				}
			}
		}
		return false;
	}

	protected StateMemento getMemento(StateMemento parent) {
		StateMemento result = new StateMemento(metaState.getTag(), parent, getLocalAttributes());
		for (State curr: substates.values()) {
			curr.getMemento(result);
		}
		return result;
	}

	public boolean equals(Object o) {
		boolean result = super.equals(o);
		if (result && this.isActive()) {
			CompositeState other = (CompositeState) o;
			result = this.substates.equals(other.substates);
		}
		return result;
	}

	public void receive(Visitor visitor) {
		for (State curr: substates.values()) {
			if (curr instanceof RealState) {
				visitor.visit((RealState)curr);
			}
		}
	}

	public void terminate() throws WorkflowException {
		Transition triggeredTransition = metaState.findTriggeredTransition(Event.NULL, this);
		if (triggeredTransition != null) {
			triggeredTransition.perform(Event.NULL, this);
		}
		complete = true;
	}

	public boolean isComplete() {
		return complete;
	}

	public Event buildEvent(String name, Map<String, Object> stringParameters) throws WorkflowException {
		for (State curr: substates.values()) {
			Event event = curr.buildEvent(name, stringParameters);
			if (event != null) {
				return event;
			}
		}
		return super.buildEvent(name, stringParameters);
	}

	public void addAvailableEvents(Collection<MetaEvent> coll) {
		super.addAvailableEvents(coll);
		for (State curr: substates.values()) {
			curr.addAvailableEvents(coll);
		}
	}

}

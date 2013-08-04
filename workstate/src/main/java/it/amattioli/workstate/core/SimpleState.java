package it.amattioli.workstate.core;

import it.amattioli.workstate.exceptions.*;
import it.amattioli.workstate.info.*;

public class SimpleState extends RealState {
	private MetaSimpleState metaState;

	public SimpleState(MetaSimpleState metaState, CompositeState parent) {
		super(metaState, parent);
		this.metaState = metaState;
	}

	public void receiveEvent(Event event) throws WorkflowException {
		Transition transition = metaState.findTriggeredTransition(event, this);
		if (transition != null) {
			transition.perform(event, this);
		}
	}

	public boolean admitEvent(Event event) {
		Transition transition = metaState.findTriggeredTransition(event, this);
		return transition != null;
	}

	protected StateMemento getMemento(StateMemento parent) {
		return new StateMemento(metaState.getTag(), parent, getLocalAttributes());
	}

	public void receive(Visitor visitor) {
		// Un SimpleState non ha altri stati subordinati, dunque quando riceve
		// un Visitor non deve fargli visitare niente altro.
	}

}

package it.amattioli.workstate.core;

import java.util.Map;

import it.amattioli.workstate.exceptions.*;

public abstract class PseudoState extends State {
	//private MetaPseudoState metaState;

	public PseudoState(MetaPseudoState metaState, CompositeState parent) {
		super(metaState, parent);
		//this.metaState = metaState;
	}

	public Map<String, Object> getAllAttributes() {
		return getParent().getAllAttributes();
	}

	public Object getAttribute(String tag) {
		return getParent().getAttribute(tag);
	}

	public boolean admitEvent(Event event) {
		throw ErrorMessages.newUnsupportedOperationException(ErrorMessage.EVENT_TO_PSEUDOSTATE);
	}

	public void receiveEvent(Event event) {
		throw ErrorMessages.newUnsupportedOperationException(ErrorMessage.EVENT_TO_PSEUDOSTATE);
	}

	protected StateMemento getMemento(StateMemento parent) {
		throw new UnsupportedOperationException();
	}

	public void restore(StateMemento memento) {
		throw new UnsupportedOperationException();
	}

}

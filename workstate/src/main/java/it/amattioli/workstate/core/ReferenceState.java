package it.amattioli.workstate.core;

import java.util.Map;

import it.amattioli.workstate.config.Configuration;
import it.amattioli.workstate.config.Registry;
import it.amattioli.workstate.exceptions.WorkflowException;
import it.amattioli.workstate.info.Visitor;

/**
 * A {@link ReferenceState} is a state that represents a reference to another
 * state machine.
 * 
 * @author a.mattioli
 */
public class ReferenceState extends RealState {
	private MetaReferenceState metaState;
	private Configuration refReader;
	private Machine referenced;
	private boolean complete = false;

	/**
	 * Build a new reference state.
	 * 
	 * @param metaState
	 *            the {@link MetaState}
	 * @param parent
	 *            the parent state
	 * @param refReader
	 *            the configuration of the referenced machine
	 */
	public ReferenceState(MetaReferenceState metaState, CompositeState parent, Configuration refReader) {
		super(metaState, parent);
		this.metaState = metaState;
		this.refReader = refReader;
	}

	private Machine getReferenced() {
		return referenced;
	}

	private void setReferenced(Machine referenced) {
		this.referenced = referenced;
	}

	private void buildReferenced() throws WorkflowException {
		setReferenced(Registry.instance().newMachine(refReader, this));
	}

	public void receiveEvent(Event event) throws WorkflowException {
		Transition transition = metaState.findTriggeredTransition(event, this);
		if (transition != null) {
			transition.perform(event, this);
		} else {
			getReferenced().receiveEvent(event);
		}
	}

	public boolean admitEvent(Event event) {
		Transition transition = metaState.findTriggeredTransition(event, this);
		if (transition != null) {
			return true;
		} else {
			return getReferenced().admitEvent(event);
		}
	}

	protected StateMemento getMemento(StateMemento parent) {
		StateMemento result = new StateMemento(metaState.getTag(), parent, getLocalAttributes());
		getReferenced().getMemento(result);
		return result;
	}

	public void receive(Visitor visitor) {
		visitor.visit(getReferenced());
	}

	public void enter() throws WorkflowException {
		super.enter();
		buildReferenced();
	}

	protected void reEnter() {
		super.reEnter();
		getReferenced().reEnter();
	}

	public void exit() throws WorkflowException {
		getReferenced().exit();
		super.exit();
	}

	protected void reExit() {
		if (getReferenced() != null) {
			getReferenced().reExit();
		}
		super.reExit();
	}

	public Event buildEvent(String name, Map<String, Object> stringParameters) throws WorkflowException {
		return getReferenced().buildEvent(name, stringParameters);
	}

	public void terminate() throws WorkflowException {
		Transition triggeredTransition = metaState.findTriggeredTransition(Event.NULL, this);
		triggeredTransition.perform(Event.NULL, this);
		complete = true;
	}

	public boolean isComplete() {
		return complete;
	}

	public void restore(StateMemento memento) throws WorkflowException {
		super.restore(memento);
		Map<String, StateMemento> children = memento.getChildren();
		if (children.size() == 0) {
			enter();
		} else if (children.size() == 1) {
			Map.Entry<String,StateMemento> curr = children.entrySet().iterator().next();
			Machine oldState = getReferenced();
			buildReferenced();
			try {
				getReferenced().restore(curr.getValue());
			} catch (WorkflowException e) {
				setReferenced(oldState);
				throw e;
			}
		} else {
			// TODO: Eccezione!!!!!!
		}
	}
}

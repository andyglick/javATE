package it.amattioli.workstate.actions;

import it.amattioli.workstate.exceptions.WorkflowException;

public interface TransitionAction {

	/**
	 * @param event
	 * @param state
	 * 
	 */
	public void doAction(AttributeReader event, AttributeHandler state)
			throws WorkflowException;

	public void undoAction(AttributeReader event, AttributeHandler state);

}
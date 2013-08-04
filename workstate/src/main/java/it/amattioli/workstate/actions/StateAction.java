package it.amattioli.workstate.actions;

import it.amattioli.workstate.exceptions.WorkflowException;

public interface StateAction {

	public void doAction(AttributeHandler state) throws WorkflowException;

	public void undoAction(AttributeHandler state);

}
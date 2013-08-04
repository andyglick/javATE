package it.amattioli.workstate.actions;

import it.amattioli.workstate.exceptions.WorkflowException;

public interface AttributeHandler extends AttributeReader {

	public void setAttribute(String tag, Object value) throws WorkflowException;

}

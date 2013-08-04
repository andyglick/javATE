package it.amattioli.workstate.core;

import it.amattioli.workstate.exceptions.*;

/**
 * This interface must be implemented by objects that are able to validate
 * events.
 * 
 */
public interface EventValidator {

	/**
	 * Validate an event. If the event is valid the method will terminate
	 * normally, otherwise it will throw a WorkflowException.
	 * 
	 * @param event
	 *            the event to be validated
	 * @throws WorkflowException
	 *             if the event is not valid
	 */
	public void validate(Event event) throws WorkflowException;

}

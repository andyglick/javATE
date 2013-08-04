package it.amattioli.workstate.core;

import it.amattioli.workstate.exceptions.*;

/**
 * This interface must be implemented by classes that validate attribute values.
 * 
 */
public interface AttributeValidator {

	/**
	 * Validate an attribute value.
	 * <p>
	 * If the value is valid the method will terminate normally, otherwise it
	 * will throw a WorkflowException.
	 * 
	 * @param attribute the value to be validated
	 * @throws ClassCastException if the parameter is not an instance of the class that this
	 *         validator expects
	 * @throws WorkflowException if the value is not valid
	 */
	public void validate(Object attribute) throws WorkflowException;

}

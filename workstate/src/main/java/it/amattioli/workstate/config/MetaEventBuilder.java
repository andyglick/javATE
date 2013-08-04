package it.amattioli.workstate.config;

import it.amattioli.workstate.core.*;
import it.amattioli.workstate.exceptions.*;
import bsh.EvalError;

/**
 * Allows the construction of a {@link MetaEvent} instance given the configuration
 * strings read from a datasource.
 * 
 */
public class MetaEventBuilder implements AttributeOwnerBuilder {
	private MetaEvent buildingObject;
	private String id;

	/**
	 * Construct a builder given the event tag and an identifier
	 * 
	 */
	public MetaEventBuilder(String tag, String id) {
		buildingObject = new MetaEvent(tag);
		this.id = id;
	}

	/**
	 * Returns the identifier passed to this builder constructor
	 * 
	 * @return the identifier passed to this builder constructor
	 */
	public String getId() {
		return id;
	}

	/**
	 * Add an attribute to the building event
	 * 
	 * @param attr the attribute to be associated to the event
	 *            
	 */
	public void addAttribute(MetaAttribute attr) {
		buildingObject.addParameter(attr);
	}

	/**
	 * Add a validator object to the event that is being built.
	 * The string can be in two distinct formats:
	 * <ul>
	 *     <li>The name of a class that implements the {@link EventValidator} interface
	 *     followed by the parameters that must be passed to the constructor</li>
	 *     
	 *     <li>A BeanShell script between  curly brackets</li>
	 * </ul>
	 * 
	 */
	public void addValidator(String validator) {
		if (validator.startsWith("{") && validator.endsWith("}")) {
			// E' uno script BeanShell
			// TODO: Implementare EventVallidator come script BeanShell
		} else {
			// E' il nome di una classe
			Object newValidator = null;
			try {
				newValidator = BeanShellHelper.evalExpr("new " + validator);
			} catch (EvalError e) {
				throw ErrorMessages.newIllegalArgumentException(
						ErrorMessage.SYNTAX_ERROR, "MetaEvent Validator",
						validator);
			}
			if (newValidator instanceof EventValidator) {
				buildingObject.addValidator((EventValidator) newValidator);
			} else {
				throw ErrorMessages.newClassCastException(
						ErrorMessage.WRONG_CLASS, validator, "EventValidator");
			}
		}
	}

	/**
	 * Returns the built {@link MetaEvent}
	 * 
	 * @return the built {@link MetaEvent}
	 */
	public MetaEvent getBuiltEvent() {
		return buildingObject;
	}

}

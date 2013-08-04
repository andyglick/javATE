package it.amattioli.workstate.config;

import it.amattioli.workstate.core.AttributeValidator;
import it.amattioli.workstate.core.MetaAttribute;
import static it.amattioli.workstate.exceptions.ErrorMessage.*;
import static it.amattioli.workstate.exceptions.ErrorMessages.*;
import bsh.EvalError;

/**
 * Build a MetaAttribute starting from the strings read from a data source.
 * 
 */
public class MetaAttributeBuilder {
	private MetaAttribute builtAttribute;

	/**
	 * Constructs a builder given the base {@link MetaAttribute} parameters:
	 * <ul>
	 * <li>The tag</li>
	 * <li>The class</li>
	 * <li>The initial value</li>
	 * </ul>
	 * 
	 * @param tag the {@link MetaAttribute} tag
	 * @param attrClass the {@link MetaAttribute} class
	 * @param initialExpr the initial value. This string will be interpreted as a
	 *        BeanShell expression whose result must be of the class given in
	 *        the attrClass parameter
	 *            
	 * @throws IllegalArgumentException if the initial value expression is incorrect
	 *
	 */
	public MetaAttributeBuilder(String tag, String attrClass, String initialExpr) {
		Object initial = null;
		if (initialExpr != null && !initialExpr.equals("")) {
			try {
				initial = BeanShellHelper.evalExpr(initialExpr);
			} catch (EvalError e) {
				throw newIllegalArgumentException(SYNTAX_ERROR, "MetaAttribute Initial Value", initialExpr);
			}
		}
		builtAttribute = new MetaAttribute(tag, attrClass, initial);
	}

	/**
	 * Add a validator object for the attribute. The string can be given in
	 * two distinct ways:
	 * <ul>
	 * <li>
	 * The name of a class that implements the AttributeValidator class followed
	 * by the constructor parameters.
	 * </li>
	 * <li>
	 * A BeanShell script between curly braces.
	 * </li>
	 * </ul>
	 * 
	 * @param validator the string that define the validator object
	 * @throws IllegalArgumentException if the passed string is incorrect
	 * @throws ClassCastException if the resulting object does not implements
	 *         the {@link AttributeValidator} interface
	 */
	public void addValidator(String validator) {
		if (validator.startsWith("{") && validator.endsWith("}")) {
			// E' uno script BeanShell
			// TODO: Implementare AttributeValidator come script BeanShell
		} else {
			// E' il nome di una classe
			Object newValidator = null;
			try {
				newValidator = BeanShellHelper.evalExpr("new " + validator);
			} catch (EvalError e) {
				throw newIllegalArgumentException(SYNTAX_ERROR, "MetaAttribute Validator", validator);
			}
			if (newValidator instanceof AttributeValidator) {
				builtAttribute.addValidator((AttributeValidator) newValidator);
			} else {
				throw newClassCastException(WRONG_CLASS, validator, "AttributeValidator");
			}
		}
	}

	/**
	 * The built {@link MetaAttribute}.
	 */
	public MetaAttribute getBuiltAttribute() {
		return builtAttribute;
	}

}

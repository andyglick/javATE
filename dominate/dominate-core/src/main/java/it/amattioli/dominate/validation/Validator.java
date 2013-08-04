package it.amattioli.dominate.validation;

import java.util.Collection;

/**
 * An object that implements this interface can be used to validate an object.
 * 
 * Implementations of the interface should provide a way to set the object to be
 * validated. Tipically the object to be validated will be passed to the validator
 * constructor.
 * 
 * The validator can also be used to retrieve validation meta-data.
 * 
 * @author andrea
 *
 */
public interface Validator {

	/**
	 * Check if an object is valid as a whole.
	 * 
	 * @return a ValidationResult object whose type is ValidationResult.ResultType.VALID
	 *         if the object is valid, otherwise a ValidationResult object whose type is 
	 *         ValidationResult.ResultType.INVALID
	 */
	public ValidationResult validateBean();
	
	/**
	 * Check if a value is valid for a property. 
	 *  
	 * @param propertyName the name of the property to be checked
	 * @param value the value to be checked 
	 * @return a ValidationResult object whose type is ValidationResult.ResultType.VALID
	 *         if the value is valid for the property, otherwise a ValidationResult 
	 *         object whose type is ValidationResult.ResultType.INVALID
	 */
	public ValidationResult validateProperty(String propertyName, Object value);

	/**
	 * Retrieves validation meta-data about a property.
	 *  
	 * @param propertyName the name of the property
	 * @return a collection of {@link Constraint} objects each containing a
	 *         constraint description 
	 */
	public Collection<Constraint> getPropertyConstraints(String propertyName);
	
	/**
	 * Retrieves validation meta-data about a property.
	 * 
	 * @param propertyName the name of the property
	 * @param constraintName the name of the constraint to be retrieved
	 * @return the constraint of the given property with the given name if it
	 *         exists, null otherwise
	 */
	public Constraint getPropertyConstraint(String propertyName, String constraintName);
	
}

package it.amattioli.dominate.validation;

/**
 * The result of a validation performed by a {@link Validator}
 * 
 * @author andrea
 *
 */
public class ValidationResult {
	public static enum ResultType {
		VALID,
		INVALID;
	};
	private ResultType type;
	private String message;
	
	public ValidationResult(ResultType type, String message) {
		this.type = type;
		this.message = message;
	}
	
	/**
	 * The type of this result. Could be VALID or INVALID
	 * 
	 * @return
	 */
	public ResultType getType() {
		return type;
	}
	
	/**
	 * The message associated with this result. Tipically makes sense only for
	 * INVALID results
	 * 
	 * @return
	 */
	public String getMessage() {
		return message;
	}
}

package it.amattioli.applicate.browsing;

import org.hibernate.validator.Length;

import it.amattioli.dominate.EntityImpl;
//import it.amattioli.dominate.hibernate.validators.NotBlank;
import it.amattioli.dominate.validation.ValidationResult;
import static it.amattioli.dominate.validation.ValidationResult.ResultType.*;

public class FakeEntity extends EntityImpl {
	private String description;
	private String constrainted;

	//@NotBlank(message="NOT NULL")
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public ValidationResult validateDescription(String description) {
		if (description.equals("wrongDescription")) {
			return new ValidationResult(INVALID, "WRONG DESCRIPTION");
		}
		return new ValidationResult(VALID, null);
	}

	@Length(max=20)
	public String getConstrainted() {
		return constrainted;
	}

	public void setConstrainted(String constrainted) {
		this.constrainted = constrainted;
	}

}

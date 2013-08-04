package it.amattioli.dominate.hibernate;

import java.util.ArrayList;
import java.util.List;

import it.amattioli.dominate.EntityImpl;
import it.amattioli.dominate.validation.ValidationResult;
import static it.amattioli.dominate.validation.ValidationResult.ResultType.*;

public class FakeEntity extends EntityImpl {
	private String description;
	private String constrainted;
	private List<AssociatedEntity> assoc = new ArrayList<AssociatedEntity>();
	private FakeEnum enumerated;

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

	public String getConstrainted() {
		return constrainted;
	}

	public void setConstrainted(String constrainted) {
		this.constrainted = constrainted;
	}
	
	public List<AssociatedEntity> getAssoc() {
		return assoc;
	}

	public void addAssoc(AssociatedEntity a) {
		assoc.add(a);
	}

	public FakeEnum getEnumerated() {
		return enumerated;
	}

	public void setEnumerated(FakeEnum enumerated) {
		this.enumerated = enumerated;
	}

	public ValidationResult validate() {
		if (!"beanIsValid".equals(description + constrainted)) {
			return new ValidationResult(INVALID, "WRONG DESCRIPTION");
		}
		return new ValidationResult(VALID, null);
	}
}

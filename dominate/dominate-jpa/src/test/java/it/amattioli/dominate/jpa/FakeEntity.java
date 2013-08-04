package it.amattioli.dominate.jpa;

import java.util.ArrayList;
import java.util.List;

import static javax.persistence.CascadeType.*;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import it.amattioli.dominate.validation.ValidationResult;
import static it.amattioli.dominate.validation.ValidationResult.ResultType.*;

@Entity
public class FakeEntity extends JpaEntity {
	@Id
	private Long id;
	private Long version;
	private String description;
	private String constrainted;
	@OneToMany(cascade=PERSIST)
	private List<AssociatedEntity> assoc = new ArrayList<AssociatedEntity>();
	private FakeEnum enumerated;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getVersion() {
		return version;
	}

	public void setVersion(Long version) {
		this.version = version;
	}

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

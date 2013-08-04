package it.amattioli.dominate.jpa;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class AssociatedEntity extends JpaEntity {
	@Id
	private Long id;
	private Long version;
	private String assocDescription;

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

	public String getAssocDescription() {
		return assocDescription;
	}

	public void setAssocDescription(String assocDescription) {
		this.assocDescription = assocDescription;
	}
}

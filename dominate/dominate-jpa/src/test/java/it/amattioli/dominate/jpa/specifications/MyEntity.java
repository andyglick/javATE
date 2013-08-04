package it.amattioli.dominate.jpa.specifications;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import it.amattioli.dominate.jpa.JpaEntity;

@Entity
public class MyEntity extends JpaEntity {
	@Id
	private Long id;
	private Long version;
	private String description;
	private MyEnum enumProperty;
	@ManyToOne
	private MyEntity entityProperty;

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

	public MyEnum getEnumProperty() {
		return enumProperty;
	}

	public void setEnumProperty(MyEnum enumProperty) {
		this.enumProperty = enumProperty;
	}

	public MyEntity getEntityProperty() {
		return entityProperty;
	}

	public void setEntityProperty(MyEntity entityProperty) {
		this.entityProperty = entityProperty;
	}
}

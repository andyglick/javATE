package it.amattioli.springate;

import it.amattioli.dominate.EntityImpl;

public class FakeEntity extends EntityImpl {
	private String description;

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}

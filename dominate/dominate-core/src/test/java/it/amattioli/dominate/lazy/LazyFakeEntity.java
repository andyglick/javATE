package it.amattioli.dominate.lazy;

import it.amattioli.dominate.EntityImpl;

public class LazyFakeEntity extends EntityImpl {
	private String description;

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}

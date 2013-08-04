package it.amattioli.dominate.hibernate.types;

import it.amattioli.dominate.Entity;

public class ExternalEntity implements Entity<String> {
	private String id;

	public ExternalEntity(String id) {
		this.id = id;
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
}

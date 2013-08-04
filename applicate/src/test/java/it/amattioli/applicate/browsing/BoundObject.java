package it.amattioli.applicate.browsing;

import org.hibernate.validator.NotNull;

public class BoundObject {
	private EntityStub boundProperty;

	@NotNull
	public EntityStub getBoundProperty() {
		return boundProperty;
	}

	public void setBoundProperty(EntityStub boundProperty) {
		this.boundProperty = boundProperty;
	}
	
}

package it.amattioli.applicate.browsing;

import it.amattioli.dominate.EntityImpl;

public class MyEntity extends EntityImpl {
	private String description;
	private MyEnum enumProperty;

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
}

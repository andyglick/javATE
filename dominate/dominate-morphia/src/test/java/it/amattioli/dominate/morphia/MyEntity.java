package it.amattioli.dominate.morphia;

public class MyEntity extends MorphiaEntity {
	private String stringProperty;
	private Long longProperty;

	public String getStringProperty() {
		return stringProperty;
	}

	public void setStringProperty(String stringProperty) {
		this.stringProperty = stringProperty;
	}

	public Long getLongProperty() {
		return longProperty;
	}

	public void setLongProperty(Long longProperty) {
		this.longProperty = longProperty;
	}
}

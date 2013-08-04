package it.amattioli.guidate.properties;

public class ImageProperty extends ChildableImage {
	private static final String PROPERTY_NAME = "propertyName";

	public String getPropertyName() {
		return (String)getAttribute(PROPERTY_NAME);
	}
	
	public void setPropertyName(String propertyName) {
		setAttribute(PROPERTY_NAME, propertyName);
	}
}

package it.amattioli.dominate.properties;

public class PropertyClass {
	private Class<?> elementClass;
	private boolean multiple;
	
	public PropertyClass(Class<?> elementClass, boolean multiple) {
		this.elementClass = elementClass;
		this.multiple = multiple;
	}
	
	public Class<?> getElementClass() {
		return elementClass;
	}

	public boolean isMultiple() {
		return multiple;
	}
}

package it.amattioli.dominate.specifications;

import java.util.ArrayList;
import java.util.List;

import it.amattioli.dominate.EntityImpl;

public class MyEntity extends EntityImpl {
	private String description;
	private MyEnum enumProperty;
	private MyEntity entityProperty;
	private List<String> collectionProperty = new ArrayList<String>();
	private String[] indexedProperty = {"One","Two","Three"};

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

	public List<String> getCollectionProperty() {
		return collectionProperty;
	}

	public void addToCollectionProperty(String value) {
		collectionProperty.add(value);
	}
	
	public String getIndexedProperty(int index) {
		return indexedProperty[index];
	}

}

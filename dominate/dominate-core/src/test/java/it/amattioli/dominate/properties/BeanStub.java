package it.amattioli.dominate.properties;

import it.amattioli.dominate.properties.Derived;
import it.amattioli.dominate.EntityImpl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.hibernate.validator.NotNull;

public class BeanStub extends EntityImpl {
	private String simpleProperty = "simplePropertyValue";
	private String[] indexedProperty = new String[] {"value0", "value1", "value2"};
	private BeanStub assoc;

	public BeanStub() {
		setId(5L);
	}
	
	public String getSimpleProperty() {
		return simpleProperty;
	}

	public void setSimpleProperty(String simpleProperty) {
		String old = getSimpleProperty();
		this.simpleProperty = simpleProperty;
		firePropertyChange("simpleProperty", old, simpleProperty);
	}

	public boolean isSimplePropertyAvailable() {
		return true;
	}
	
	@NotNull
	public String getAnnotatedProperty() {
		return "";
	}

	public Collection<Integer> getCollectionProperty() {
		return Collections.emptyList();
	}

	public String getIndexedProperty(int index) {
		return indexedProperty[index];
	}

	public void setIndexedProperty(int index, String value) {
		indexedProperty[index] = value;
		firePropertyChange("indexedProperty", null, null);
	}
	
	@Derived(dependsOn="simpleProperty")
	public String getDerivedProperty() {
		return getSimpleProperty().toUpperCase();
	}
	
	public String getValuedProperty() {
		return getValuedPropertyValues().get(0);
	}
	
	public List<String> getValuedPropertyValues() {
		return new ArrayList<String>() {{
			add("value1");
			add("value2");
			add("value3");
		}};
	}

	public BeanStub getAssoc() {
		return assoc;
	}

	public void setAssoc(BeanStub assoc) {
		this.assoc = assoc;
	}

	@Override
	public String toString() {
		return "BeanStub";
	}

}

package it.amattioli.dominate.specifications.beans;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.beanutils.DynaProperty;

import it.amattioli.dominate.Entity;
import it.amattioli.dominate.Specification;
import it.amattioli.dominate.properties.Properties;

public class PropertySpecification<T extends Entity<?>> {
	private Specification<T> specification;
	private Map<String,SpecificationProperty> properties = new HashMap<String, SpecificationProperty>();
	
	public PropertySpecification(Specification<T> specification, SpecificationProperty... property) {
		this.specification = specification;
		for (SpecificationProperty prop: property) {
			addProperty(prop);
		}
	}
	
	public Specification<T> getSpecification() {
		return specification;
	}
	
	public DynaProperty getProperty(String propertyName) {
		return properties.get(propertyName).getDynaProperty();
	}
	
	public Collection<DynaProperty> getProperties() {
		Collection<DynaProperty> result = new ArrayList<DynaProperty>();
		for (SpecificationProperty prop: properties.values()) {
			result.add(prop.getDynaProperty());
		}
		return result;
	}
	
	public Collection<String> getPropertyNames() {
		Collection<String> result = new ArrayList<String>();
		for (DynaProperty prop: getProperties()) {
			result.add(prop.getName());
		}
		return result;
	}
	
	public void addProperty(SpecificationProperty property) {
		properties.put(property.getExternalName(), property);
	}
	
	public Object get(String propertyName) {
		return Properties.get(specification, properties.get(propertyName).getInternalName());
	}
	
	public void set(String propertyName, Object specValue) {
		Properties.set(specification, properties.get(propertyName).getInternalName(), specValue);
	}

}
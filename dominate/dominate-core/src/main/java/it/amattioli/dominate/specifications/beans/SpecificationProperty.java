package it.amattioli.dominate.specifications.beans;

import org.apache.commons.beanutils.DynaProperty;

public class SpecificationProperty {
	private DynaProperty dynaProperty;
	private String internalName;
	
	public SpecificationProperty(DynaProperty dynaProperty, String internalName) {
		this.dynaProperty = dynaProperty;
		this.internalName = internalName;
	}
	
	public DynaProperty getDynaProperty() {
		return dynaProperty;
	}
	
	public String getInternalName() {
		return internalName;
	}
	
	public String getExternalName() {
		return dynaProperty.getName();
	}
}
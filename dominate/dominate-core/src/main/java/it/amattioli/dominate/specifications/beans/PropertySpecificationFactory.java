package it.amattioli.dominate.specifications.beans;

import java.beans.PropertyDescriptor;

import it.amattioli.dominate.Entity;

public interface PropertySpecificationFactory {

	public boolean canBuildSpecificationFor(PropertyDescriptor descriptor);
	
	public <T extends Entity<?>> PropertySpecification<T> newSpecification(PropertyDescriptor descriptor);
	
}

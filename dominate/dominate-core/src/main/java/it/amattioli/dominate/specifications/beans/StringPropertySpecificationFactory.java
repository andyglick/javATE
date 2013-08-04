package it.amattioli.dominate.specifications.beans;

import java.beans.PropertyDescriptor;

import org.apache.commons.beanutils.DynaProperty;

import it.amattioli.dominate.Entity;
import it.amattioli.dominate.Specification;
import it.amattioli.dominate.specifications.ComparisonType;
import it.amattioli.dominate.specifications.StringSpecification;

public class StringPropertySpecificationFactory implements PropertySpecificationFactory{

	@Override
	public boolean canBuildSpecificationFor(PropertyDescriptor descriptor) {
		return String.class.equals(descriptor.getPropertyType());
	}

	@Override
	public <T extends Entity<?>> PropertySpecification<T> newSpecification(PropertyDescriptor descriptor) {
		return new PropertySpecification<T>(
				(Specification<T>) StringSpecification.newInstance(descriptor.getName()),
				newValueProperty(descriptor),
				newComparisonTypeProperty(descriptor)
		);
	}

	private SpecificationProperty newValueProperty(PropertyDescriptor descriptor) {
		return new SpecificationProperty(
				new DynaProperty(descriptor.getName(), String.class),
				"value"
		);
	}
	
	private SpecificationProperty newComparisonTypeProperty(PropertyDescriptor descriptor) {
		return new SpecificationProperty(
				new DynaProperty(descriptor.getName()+"ComparisonType", ComparisonType.class),
				"comparisonType"
		);
	}

}

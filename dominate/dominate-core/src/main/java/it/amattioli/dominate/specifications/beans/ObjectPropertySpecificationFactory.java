package it.amattioli.dominate.specifications.beans;

import java.beans.PropertyDescriptor;

import org.apache.commons.beanutils.DynaProperty;

import it.amattioli.dominate.Entity;
import it.amattioli.dominate.Specification;
import it.amattioli.dominate.specifications.ObjectSpecification;

public class ObjectPropertySpecificationFactory implements PropertySpecificationFactory {

	@Override
	public boolean canBuildSpecificationFor(PropertyDescriptor descriptor) {
		for (PropertySpecificationFactory factory: BeanSpecification.getFactories()) {
			if (factory != this && factory.canBuildSpecificationFor(descriptor)) {
				return false;
			}
		}
		return true;
	}

	@Override
	public <T extends Entity<?>> PropertySpecification<T> newSpecification(PropertyDescriptor descriptor) {
		return new PropertySpecification<T>(
				(Specification<T>)ObjectSpecification.newInstance(descriptor.getName()),
				newSpecificationProperty(descriptor)
		);
	}

	private SpecificationProperty newSpecificationProperty(PropertyDescriptor descriptor) {
		return new SpecificationProperty(
				new DynaProperty(descriptor.getName(), descriptor.getPropertyType()),
				"value"
		);
	}

}

package it.amattioli.dominate.specifications.beans;

import java.beans.PropertyDescriptor;

import org.apache.commons.beanutils.DynaProperty;

import it.amattioli.dominate.Entity;
import it.amattioli.dominate.specifications.EntitySpecification;

public class EntityPropertySpecificationFactory implements PropertySpecificationFactory {

	@Override
	public boolean canBuildSpecificationFor(PropertyDescriptor descriptor) {
		return descriptor.getPropertyType() != null && Entity.class.isAssignableFrom(descriptor.getPropertyType());
	}

	@Override
	public <T extends Entity<?>> PropertySpecification<T> newSpecification(PropertyDescriptor descriptor) {
		return new PropertySpecification<T> (
				EntitySpecification.newInstance(descriptor.getName(), (Class<Entity>)descriptor.getPropertyType()),
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

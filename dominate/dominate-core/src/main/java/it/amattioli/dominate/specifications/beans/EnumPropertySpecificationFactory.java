package it.amattioli.dominate.specifications.beans;

import java.beans.PropertyDescriptor;

import org.apache.commons.beanutils.DynaProperty;

import it.amattioli.dominate.Entity;
import it.amattioli.dominate.specifications.EnumSpecification;

public class EnumPropertySpecificationFactory implements PropertySpecificationFactory {

	@Override
	public boolean canBuildSpecificationFor(PropertyDescriptor descriptor) {
		return descriptor.getPropertyType() != null && descriptor.getPropertyType().isEnum();
	}

	@Override
	public <T extends Entity<?>> PropertySpecification<T> newSpecification(PropertyDescriptor descriptor) {
		return new PropertySpecification<T>(
				EnumSpecification.newInstance(descriptor.getName(), (Class<Enum>)descriptor.getPropertyType()),
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

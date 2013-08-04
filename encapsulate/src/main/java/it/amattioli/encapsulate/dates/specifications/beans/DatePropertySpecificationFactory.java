package it.amattioli.encapsulate.dates.specifications.beans;

import it.amattioli.dominate.Entity;
import it.amattioli.dominate.Specification;
import it.amattioli.dominate.specifications.beans.PropertySpecification;
import it.amattioli.dominate.specifications.beans.PropertySpecificationFactory;
import it.amattioli.dominate.specifications.beans.SpecificationProperty;
import it.amattioli.encapsulate.dates.TimeInterval;
import it.amattioli.encapsulate.dates.specifications.DateSpecification;

import java.beans.PropertyDescriptor;
import java.util.Date;

import org.apache.commons.beanutils.DynaProperty;

public class DatePropertySpecificationFactory implements PropertySpecificationFactory {

	@Override
	public boolean canBuildSpecificationFor(PropertyDescriptor descriptor) {
		return Date.class.equals(descriptor.getPropertyType());
	}

	@Override
	public <T extends Entity<?>> PropertySpecification<T> newSpecification(PropertyDescriptor descriptor) {
		return new PropertySpecification<T>(
				(Specification<T>) DateSpecification.newInstance(descriptor.getName()),
				newSpecificationProperty(descriptor));
	}

	private SpecificationProperty newSpecificationProperty(PropertyDescriptor descriptor) {
		return new SpecificationProperty(
				new DynaProperty(descriptor.getName(), TimeInterval.class),
				"value"
		);
	}

}

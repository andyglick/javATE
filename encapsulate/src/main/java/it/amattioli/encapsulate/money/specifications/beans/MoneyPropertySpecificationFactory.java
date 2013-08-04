package it.amattioli.encapsulate.money.specifications.beans;

import java.beans.PropertyDescriptor;

import org.apache.commons.beanutils.DynaProperty;

import it.amattioli.dominate.Entity;
import it.amattioli.dominate.Specification;
import it.amattioli.dominate.specifications.beans.PropertySpecification;
import it.amattioli.dominate.specifications.beans.PropertySpecificationFactory;
import it.amattioli.dominate.specifications.beans.SpecificationProperty;
import it.amattioli.encapsulate.money.Money;
import it.amattioli.encapsulate.money.specifications.MoneySpecification;
import it.amattioli.encapsulate.range.Range;

public class MoneyPropertySpecificationFactory implements PropertySpecificationFactory {

	@Override
	public boolean canBuildSpecificationFor(PropertyDescriptor descriptor) {
		return Money.class.equals(descriptor.getPropertyType());
	}

	@Override
	public <T extends Entity<?>> PropertySpecification<T> newSpecification(PropertyDescriptor descriptor) {
		return new PropertySpecification<T>(
				(Specification<T>) MoneySpecification.newInstance(descriptor.getName()),
				newSpecificationProperty(descriptor));
	}

	private SpecificationProperty newSpecificationProperty(PropertyDescriptor descriptor) {
		return new SpecificationProperty(
				new DynaProperty(descriptor.getName(), Range.class),
				"value"
		);
	}

}

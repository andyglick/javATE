package it.amattioli.dominate.specifications.dflt;

import it.amattioli.dominate.Entity;
import it.amattioli.dominate.Specification;

import org.apache.commons.collections.Predicate;

public class SpecificationPredicate<T extends Entity<?>> implements Predicate {
	private Specification<T> specification;
	
	public SpecificationPredicate(Specification<T> specification) {
		this.specification = specification;
	}
	
	@Override
	public boolean evaluate(Object object) {
		return specification.isSatisfiedBy((T)object);
	}

}

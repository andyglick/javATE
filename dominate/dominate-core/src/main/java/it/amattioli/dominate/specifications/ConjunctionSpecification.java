package it.amattioli.dominate.specifications;

import it.amattioli.dominate.Entity;
import it.amattioli.dominate.Specification;

public class ConjunctionSpecification<T extends Entity<?>> extends CompositeSpecification<T> {

	public static <U extends Entity<?>> ConjunctionSpecification<U> create() {
		return new ConjunctionSpecification<U>();
	}
	
	public static <U extends Entity<?>> ConjunctionSpecification<U> create(Specification<U>... specs) {
		return new ConjunctionSpecification<U>(specs);
	}
	
	public ConjunctionSpecification() {
		super();
	}

	public ConjunctionSpecification(Specification<T>... specs) {
		super(specs);
	}

	@Override
	public void assembleQuery(Assembler assembler) {
		if (!wasSet() && !isSatisfiedIfNotSet()) {
			assembler.noResults();
		} else if (wasSet()) {
			assembler.startConjunction();
			for (Specification<T> spec : getSpecifications()) {
				spec.assembleQuery(assembler);
			}
			assembler.endConjunction();
		}
	}

	@Override
	public boolean isSatisfiedBy(T entity) {
		if (!wasSet()) {
			return isSatisfiedIfNotSet();
		}
		for (Specification<T> spec : getSpecifications()) {
			if (!spec.isSatisfiedBy(entity)) {
				return false;
			}
		}
		return true;
	}

}

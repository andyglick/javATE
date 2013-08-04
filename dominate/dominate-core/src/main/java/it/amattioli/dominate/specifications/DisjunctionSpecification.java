package it.amattioli.dominate.specifications;

import it.amattioli.dominate.Entity;
import it.amattioli.dominate.Specification;

public class DisjunctionSpecification<T extends Entity<?>> extends CompositeSpecification<T> {
	
	public static <U extends Entity<?>> DisjunctionSpecification<U> create() {
		return new DisjunctionSpecification<U>();
	}
	
	public static <U extends Entity<?>> DisjunctionSpecification<U> create(Specification<U>... specs) {
		return new DisjunctionSpecification<U>(specs);
	}

	public DisjunctionSpecification() {
		super();
	}

	public DisjunctionSpecification(Specification<T>... specs) {
		super(specs);
	}

	@Override
	public void assembleQuery(Assembler assembler) {
		if (!wasSet() && !isSatisfiedIfNotSet()) {
			assembler.noResults();
		} else if (wasSet()) {
			assembler.startDisjunction();
			for (Specification<T> spec : getSpecifications()) {
				spec.assembleQuery(assembler);
			}
			assembler.endDisjunction();
		}
	}

	@Override
	public boolean isSatisfiedBy(T entity) {
		if (!wasSet()) {
			return isSatisfiedIfNotSet();
		}
		for (Specification<T> spec : getSpecifications()) {
			if (spec.isSatisfiedBy(entity)) {
				return true;
			}
		}
		return false;
	}

}

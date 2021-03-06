package it.amattioli.encapsulate.range.specifications;

import it.amattioli.dominate.Entity;
import it.amattioli.dominate.specifications.Assembler;
import it.amattioli.dominate.specifications.dflt.PredicateAssembler;
import it.amattioli.dominate.specifications.dflt.SpecificationPredicate;

public class DefaultComparableSpecification<T extends Entity<?>, N extends Comparable<? super N>> extends ComparableSpecification<T, N> {

	public DefaultComparableSpecification() {

	}
	
	public DefaultComparableSpecification(String propertyName) {
		super(propertyName);
	}

	@Override
	public void itselfAssembleQuery(Assembler assembler) {
		((PredicateAssembler)assembler).addAssembledPredicate(new SpecificationPredicate<T>(this));
	}

	@Override
	public boolean itselfSupportsAssembler(Assembler assembler) {
		return assembler instanceof PredicateAssembler;
	}
}

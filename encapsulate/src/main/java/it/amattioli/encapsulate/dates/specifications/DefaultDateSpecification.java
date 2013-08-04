package it.amattioli.encapsulate.dates.specifications;

import it.amattioli.dominate.Entity;
import it.amattioli.dominate.specifications.Assembler;
import it.amattioli.dominate.specifications.dflt.PredicateAssembler;
import it.amattioli.dominate.specifications.dflt.SpecificationPredicate;

public class DefaultDateSpecification<T extends Entity<?>> extends DateSpecification<T> {
	
	public DefaultDateSpecification() {

	}
	
	public DefaultDateSpecification(String propertyName) {
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

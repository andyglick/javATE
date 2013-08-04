package it.amattioli.encapsulate.dates.specifications;

import it.amattioli.dominate.Entity;
import it.amattioli.dominate.specifications.Assembler;
import it.amattioli.dominate.specifications.dflt.PredicateAssembler;
import it.amattioli.dominate.specifications.dflt.SpecificationPredicate;

public class DefaultTimeIntervalSpecification<T extends Entity<?>> extends TimeIntervalSpecification<T> {
	
	public DefaultTimeIntervalSpecification() {
		
	}
	
	public DefaultTimeIntervalSpecification(TimeIntervalSpecification<T> chained) {
		super(chained);
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

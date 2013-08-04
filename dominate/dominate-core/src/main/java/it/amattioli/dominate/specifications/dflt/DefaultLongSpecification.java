package it.amattioli.dominate.specifications.dflt;

import it.amattioli.dominate.Entity;
import it.amattioli.dominate.specifications.Assembler;
import it.amattioli.dominate.specifications.LongSpecification;

public class DefaultLongSpecification<T extends Entity<?>> extends LongSpecification<T> {
	
	public DefaultLongSpecification() {

	}
	
	public DefaultLongSpecification(String propertyName) {
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

package it.amattioli.dominate.specifications.dflt;

import it.amattioli.dominate.Entity;
import it.amattioli.dominate.specifications.Assembler;
import it.amattioli.dominate.specifications.CollectionSpecification;

public class DefaultCollectionSpecification<T extends Entity<?>> extends CollectionSpecification<T> {
	
	public DefaultCollectionSpecification() {
		
	}
	
	public DefaultCollectionSpecification(String propertyName) {
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

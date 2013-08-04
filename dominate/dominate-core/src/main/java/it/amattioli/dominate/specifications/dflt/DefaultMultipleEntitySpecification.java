package it.amattioli.dominate.specifications.dflt;

import java.io.Serializable;

import it.amattioli.dominate.Entity;
import it.amattioli.dominate.specifications.Assembler;
import it.amattioli.dominate.specifications.MultipleEntitySpecification;

public class DefaultMultipleEntitySpecification<T extends Entity<?>, I extends Serializable, U extends Entity<I>> extends MultipleEntitySpecification<T, I, U> {
	
	public DefaultMultipleEntitySpecification() {

	}
	
	public DefaultMultipleEntitySpecification(String propertyName, Class<U> entityClass) {
		super(propertyName, entityClass);
	}

	@Override
	public void itselfAssembleQuery(Assembler assembler) {
		((PredicateAssembler)assembler).addAssembledPredicate(new SpecificationPredicate<T>(this));
	}

	public boolean itselfSupportsAssembler(Assembler assembler) {
		return assembler instanceof PredicateAssembler;
	}

}

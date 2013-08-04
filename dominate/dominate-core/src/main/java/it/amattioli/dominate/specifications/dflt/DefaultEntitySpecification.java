package it.amattioli.dominate.specifications.dflt;

import java.io.Serializable;

import it.amattioli.dominate.Entity;
import it.amattioli.dominate.specifications.Assembler;
import it.amattioli.dominate.specifications.EntitySpecification;

public class DefaultEntitySpecification<T extends Entity<?>, I extends Serializable, U extends Entity<I>> extends EntitySpecification<T, I, U> {
	
	public DefaultEntitySpecification() {

	}
	
	public DefaultEntitySpecification(String propertyName, Class<U> entityClass) {
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

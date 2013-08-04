package it.amattioli.dominate.specifications.dflt;

import it.amattioli.dominate.Entity;
import it.amattioli.dominate.specifications.Assembler;
import it.amattioli.dominate.specifications.EnumSpecification;

public class DefaultEnumSpecification<T extends Entity<?>, U extends Enum<U>> extends EnumSpecification<T, U> {
	
	public DefaultEnumSpecification() {
		
	}
	
	public DefaultEnumSpecification(String propertyName, Class<U> enumClass) {
		super(propertyName, enumClass);
	}

	@Override
	public void itselfAssembleQuery(Assembler assembler) {
		((PredicateAssembler)assembler).addAssembledPredicate(new SpecificationPredicate<T>(this));
	}

	public boolean itselfSupportsAssembler(Assembler assembler) {
		return assembler instanceof PredicateAssembler;
	}

}

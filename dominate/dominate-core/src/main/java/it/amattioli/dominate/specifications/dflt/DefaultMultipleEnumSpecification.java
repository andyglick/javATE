package it.amattioli.dominate.specifications.dflt;

import it.amattioli.dominate.Entity;
import it.amattioli.dominate.specifications.Assembler;
import it.amattioli.dominate.specifications.MultipleEnumSpecification;

public class DefaultMultipleEnumSpecification<T extends Entity<?>, U extends Enum<U>> extends MultipleEnumSpecification<T, U> {
	
	public DefaultMultipleEnumSpecification() {

	}
	
	public DefaultMultipleEnumSpecification(String propertyName, Class<U> enumClass) {
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

package it.amattioli.dominate.specifications.dflt;

import it.amattioli.dominate.Entity;
import it.amattioli.dominate.specifications.AbstractSpecification;
import it.amattioli.dominate.specifications.Assembler;

public class FalseSpecification<T extends Entity<?>> extends AbstractSpecification<T> {

	@Override
	public void assembleQuery(Assembler assembler) {
		((PredicateAssembler)assembler).addAssembledPredicate(new SpecificationPredicate<T>(this));
	}

	@Override
	public boolean isSatisfiedBy(T object) {
		return false;
	}

	@Override
	public boolean supportsAssembler(Assembler assembler) {
		return assembler instanceof PredicateAssembler;
	}

	@Override
	public boolean wasSet() {
		return true;
	}

}

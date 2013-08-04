package it.amattioli.dominate.specifications;

import it.amattioli.dominate.Entity;
import it.amattioli.dominate.Specification;

public class NegatedSpecification<T extends Entity<?>> extends AbstractSpecification<T> {
	private  Specification<T> spec;
	
	public NegatedSpecification() {
		
	}
	
	public NegatedSpecification(Specification<T> spec) {
		this.spec = spec;
	}
	
	public void setSpecification(Specification<T> spec) {
		this.spec = spec;
	}
	
	@Override
	public boolean supportsAssembler(Assembler assembler) {
		return spec.supportsAssembler(assembler);
	}
	
	@Override
	public boolean wasSet() {
		return spec.wasSet();
	}
	
	@Override
	public void assembleQuery(Assembler assembler) {
		if (!wasSet() && !isSatisfiedIfNotSet()) {
			assembler.noResults();
		} else {
			assembler.startNegation();
			spec.assembleQuery(assembler);
			assembler.endNegation();
		}
	}

	@Override
	public boolean isSatisfiedBy(T entity) {
		if (!wasSet()) {
			return isSatisfiedIfNotSet();
		}
		return !spec.isSatisfiedBy(entity);
	}
}

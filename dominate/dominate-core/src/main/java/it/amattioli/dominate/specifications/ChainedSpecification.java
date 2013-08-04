package it.amattioli.dominate.specifications;

import java.util.ServiceLoader;

import it.amattioli.dominate.Entity;
import it.amattioli.dominate.Specification;

public abstract class ChainedSpecification<T extends Entity<?>> extends AbstractSpecification<T> {
	private Specification<T> nextInChain;
	
	public static <T extends Entity<?>, S extends ChainedSpecification<T>> S createChain(Class<S> specClass) {
		S result = null;
		for(S spec: ServiceLoader.load(specClass)) {
			spec.setNextInChain(result);
			result = spec;
		}
		return result;
	}
	
	public ChainedSpecification() {
		setNextInChain(null);
	}
	
	public ChainedSpecification(Specification<T> nextInChain) {
		setNextInChain(nextInChain);
	}
	
	protected Specification<T> getNextInChain() {
		return nextInChain;
	}
	
	protected void setNextInChain(Specification<T> nextInChain) {
		this.nextInChain = nextInChain;
	}
	
	public boolean supportsAssembler(Assembler assembler) {
		return itselfSupportsAssembler(assembler) 
		    || (nextInChain != null && nextInChain.supportsAssembler(assembler));
	}
	
	public abstract boolean itselfSupportsAssembler(Assembler assembler);

	public void assembleQuery(Assembler assembler) {
		if (!wasSet() && !isSatisfiedIfNotSet()) {
			assembler.noResults();
		} else {
			if (itselfSupportsAssembler(assembler)) {
				itselfAssembleQuery(assembler);
			} else {
				chainAssembler(assembler);
			}
		}
	}
	
	public abstract void itselfAssembleQuery(Assembler assembler);
	
	protected void chainAssembler(Assembler assembler) {
		if (nextInChain != null) {
			nextInChain.assembleQuery(assembler);
		}
	}

}

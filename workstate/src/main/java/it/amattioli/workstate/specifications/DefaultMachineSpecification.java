package it.amattioli.workstate.specifications;

import it.amattioli.dominate.Entity;
import it.amattioli.dominate.specifications.Assembler;
import it.amattioli.dominate.specifications.dflt.PredicateAssembler;
import it.amattioli.dominate.specifications.dflt.SpecificationPredicate;

public class DefaultMachineSpecification<T extends Entity<?>> extends MachineSpecification<T> {
	
	public DefaultMachineSpecification() {
	}
	
	public DefaultMachineSpecification(String propertyName, String workflowName) {
		super(propertyName, workflowName);
	}
	
	public DefaultMachineSpecification(String propertyName, String workflowName, MachineSpecification<T> chained) {
		super(propertyName, workflowName, chained);
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

package it.amattioli.workstate.specifications;

import java.util.Collections;

import it.amattioli.dominate.specifications.dflt.PredicateAssembler;
import it.amattioli.dominate.hibernate.specifications.HqlAssembler;
import junit.framework.TestCase;

public class MachineSpecificationTest extends TestCase {

	public void testInstantiation() {
		MachineSpecification<?> spec = MachineSpecification.newInstance("property", "wfName");
		spec.supportsAssembler(new PredicateAssembler());
		spec.supportsAssembler(new HqlAssembler("", Collections.EMPTY_LIST));
	}
	
}

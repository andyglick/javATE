package it.amattioli.dominate.morphia;

import it.amattioli.dominate.specifications.StringSpecification;
import junit.framework.TestCase;

public class MorphiaStringSpecificationTest extends TestCase {

	public void testStringSpecificationConstruction() {
		StringSpecification<MyEntity> spec = StringSpecification.newInstance("stringProperty");
		assertTrue(spec.supportsAssembler(new MorphiaAssembler<MyEntity>(new MorphiaQueryStub<MyEntity>())));
	}
	
}

package it.amattioli.dominate.morphia;

import it.amattioli.dominate.specifications.ObjectSpecification;
import junit.framework.TestCase;

public class MorphiaObjectSpecificationTest extends TestCase {

	public void testObjectSpecificationConstruction() {
		ObjectSpecification<MyEntity> spec = ObjectSpecification.newInstance("stringProperty");
		assertTrue(spec.supportsAssembler(new MorphiaAssembler<MyEntity>(new MorphiaQueryStub<MyEntity>())));
	}
	
}

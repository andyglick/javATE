package it.amattioli.dominate.jcr;

import static org.mockito.Mockito.*;

import it.amattioli.dominate.specifications.StringSpecification;
import junit.framework.TestCase;

public class JcrStringSpecificationTest extends TestCase {

	public void testSpecificationFactory() {
		StringSpecification<MyEntity> spec = StringSpecification.newInstance("description");
		assertTrue(spec.supportsAssembler(mock(JcrAssembler.class)));
	}
	
}

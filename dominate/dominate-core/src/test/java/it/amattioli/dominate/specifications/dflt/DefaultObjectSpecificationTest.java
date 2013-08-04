package it.amattioli.dominate.specifications.dflt;

import it.amattioli.dominate.specifications.MyEntity;
import junit.framework.TestCase;

public class DefaultObjectSpecificationTest extends TestCase {

	public void testSatisfied() {
		MyEntity e = new MyEntity();
		e.setId(1L);
		e.setDescription("Descr");
		DefaultObjectSpecification<MyEntity> spec = new DefaultObjectSpecification<MyEntity>("id");
		spec.setValue(1L);
		assertTrue(spec.isSatisfiedBy(e));
	}
	
	public void testNotSatisfied() {
		MyEntity e = new MyEntity();
		e.setId(1L);
		e.setDescription("Descr");
		DefaultObjectSpecification<MyEntity> spec = new DefaultObjectSpecification<MyEntity>("id");
		spec.setValue(2L);
		assertFalse(spec.isSatisfiedBy(e));
	}
	
	public void testNotSet() {
		MyEntity e = new MyEntity();
		e.setId(1L);
		e.setDescription("Descr");
		DefaultObjectSpecification<MyEntity> spec = new DefaultObjectSpecification<MyEntity>("id");
		assertTrue(spec.isSatisfiedBy(e));
	}
	
	public void testAssembler() {
		MyEntity e = new MyEntity();
		e.setId(1L);
		e.setDescription("Descr");
		DefaultObjectSpecification<MyEntity> spec = new DefaultObjectSpecification<MyEntity>("id");
		PredicateAssembler a = new PredicateAssembler();
		spec.assembleQuery(a);
		assertTrue(a.assembledPredicate().evaluate(e));
	}
	
}

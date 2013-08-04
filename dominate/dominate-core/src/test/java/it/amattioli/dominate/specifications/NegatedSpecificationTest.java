package it.amattioli.dominate.specifications;

import it.amattioli.dominate.specifications.dflt.FalseSpecification;
import it.amattioli.dominate.specifications.dflt.PredicateAssembler;
import it.amattioli.dominate.specifications.dflt.TrueSpecification;
import junit.framework.TestCase;

public class NegatedSpecificationTest extends TestCase {

	public void testWasSet() {
		MyNegatedSpec spec = new MyNegatedSpec();
		spec.setDescription("d");
		assertTrue(spec.wasSet());
	}
	
	public void testWasNotSet() {
		MyNegatedSpec spec = new MyNegatedSpec();
		assertFalse(spec.wasSet());
	}
	
	public void testSatisfiedIfNotSet() {
		MyNegatedSpec spec = new MyNegatedSpec();
		assertTrue(spec.isSatisfiedBy(new MyEntity()));
	}
	
	public void testNotSatisfiedIfNotSet() {
		MyNegatedSpec spec = new MyNegatedSpec();
		spec.setSatisfiedIfNotSet(false);
		assertFalse(spec.isSatisfiedBy(new MyEntity()));
	}
	
	public void testSatisfied() {
		NegatedSpecification<MyEntity> spec = new NegatedSpecification<MyEntity>();
		spec.setSpecification(new FalseSpecification<MyEntity>());
		assertTrue(spec.isSatisfiedBy(new MyEntity()));
	}
	
	public void testNotSatisfied() {
		NegatedSpecification<MyEntity> spec = new NegatedSpecification<MyEntity>();
		spec.setSpecification(new TrueSpecification<MyEntity>());
		assertFalse(spec.isSatisfiedBy(new MyEntity()));
	}

	public void testAssembler() {
		NegatedSpecification<MyEntity> spec = new NegatedSpecification<MyEntity>();
		spec.setSpecification(new FalseSpecification<MyEntity>());
		PredicateAssembler a = new PredicateAssembler();
		spec.assembleQuery(a);
		assertTrue(a.assembledPredicate().evaluate(new MyEntity()));
	}
}

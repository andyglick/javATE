package it.amattioli.dominate.specifications.dflt;

import it.amattioli.dominate.specifications.MyEntity;
import junit.framework.TestCase;

public class DefaultExclusionListSpecificationTest extends TestCase {

	public void testSatisfied() {
		MyEntity exclude = new MyEntity();
		exclude.setId(1L);
		DefaultExclusionListSpecification<MyEntity> spec = new DefaultExclusionListSpecification<MyEntity>();
		spec.addToExclusionList(exclude);
		
		MyEntity e = new MyEntity();
		e.setId(2L);
		assertTrue(spec.isSatisfiedBy(e));
	}
	
	public void testNotSatisfied() {
		MyEntity exclude = new MyEntity();
		exclude.setId(1L);
		DefaultExclusionListSpecification<MyEntity> spec = new DefaultExclusionListSpecification<MyEntity>();
		spec.addToExclusionList(exclude);
		
		assertFalse(spec.isSatisfiedBy(exclude));
	}
	
	public void testAssembler() {
		MyEntity exclude = new MyEntity();
		exclude.setId(1L);
		DefaultExclusionListSpecification<MyEntity> spec = new DefaultExclusionListSpecification<MyEntity>();
		spec.addToExclusionList(exclude);
		
		MyEntity e = new MyEntity();
		e.setId(2L);
		PredicateAssembler a = new PredicateAssembler();
		spec.assembleQuery(a);
		assertTrue(a.assembledPredicate().evaluate(e));
	}
}

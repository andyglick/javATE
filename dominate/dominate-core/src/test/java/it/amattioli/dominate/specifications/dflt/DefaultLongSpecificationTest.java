package it.amattioli.dominate.specifications.dflt;

import it.amattioli.dominate.specifications.MyEntity;
import it.amattioli.dominate.specifications.TotalOrderComparisonType;
import junit.framework.TestCase;

public class DefaultLongSpecificationTest extends TestCase {

	public void testSatisfied() {
		MyEntity e = new MyEntity();
		e.setId(1L);
		e.setDescription("Descr");
		DefaultLongSpecification<MyEntity> spec = new DefaultLongSpecification<MyEntity>("id");
		spec.setValue(1L);
		assertTrue(spec.isSatisfiedBy(e));
	}
	
	public void testSatisfiedGreater() {
		MyEntity e = new MyEntity();
		e.setId(1L);
		e.setDescription("Descr");
		DefaultLongSpecification<MyEntity> spec = new DefaultLongSpecification<MyEntity>("id");
		spec.setComparisonType(TotalOrderComparisonType.GREATER);
		spec.setValue(0L);
		assertTrue(spec.isSatisfiedBy(e));
	}
	
	public void testSatisfiedLower() {
		MyEntity e = new MyEntity();
		e.setId(1L);
		e.setDescription("Descr");
		DefaultLongSpecification<MyEntity> spec = new DefaultLongSpecification<MyEntity>("id");
		spec.setComparisonType(TotalOrderComparisonType.LOWER);
		spec.setValue(3L);
		assertTrue(spec.isSatisfiedBy(e));
	}
	
	public void testNotSatisfied() {
		MyEntity e = new MyEntity();
		e.setId(1L);
		e.setDescription("Descr");
		DefaultLongSpecification<MyEntity> spec = new DefaultLongSpecification<MyEntity>("id");
		spec.setValue(2L);
		assertFalse(spec.isSatisfiedBy(e));
	}
	
	public void testNotSet() {
		MyEntity e = new MyEntity();
		e.setId(1L);
		e.setDescription("Descr");
		DefaultLongSpecification<MyEntity> spec = new DefaultLongSpecification<MyEntity>("id");
		assertTrue(spec.isSatisfiedBy(e));
	}

	public void testAssembler() {
		MyEntity e = new MyEntity();
		e.setId(1L);
		e.setDescription("Descr");
		DefaultLongSpecification<MyEntity> spec = new DefaultLongSpecification<MyEntity>("id");
		spec.setValue(1L);
		PredicateAssembler a = new PredicateAssembler();
		spec.assembleQuery(a);
		assertTrue(a.assembledPredicate().evaluate(e));
	}
}

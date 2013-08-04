package it.amattioli.encapsulate.range.specifications;

import it.amattioli.encapsulate.range.GenericContinousRange;
import junit.framework.TestCase;

public class DefaultComparableSpecificationTest extends TestCase {

	public void testSatisfied() {
		MyEntity e = new MyEntity();
		e.setId(1L);
		e.setLongAttribute(1L);
		DefaultComparableSpecification<MyEntity,Long> spec = new DefaultComparableSpecification<MyEntity,Long>("longAttribute");
		spec.setValue(new GenericContinousRange<Long>(0L, 2L));
		assertTrue(spec.isSatisfiedBy(e));
	}
	
	public void testLowBounded() {
		MyEntity e = new MyEntity();
		e.setId(1L);
		e.setLongAttribute(1L);
		DefaultComparableSpecification<MyEntity,Long> spec = new DefaultComparableSpecification<MyEntity,Long>("longAttribute");
		spec.setValue(new GenericContinousRange<Long>(0L, null));
		assertTrue(spec.isSatisfiedBy(e));
	}
	
	public void testHighBounded() {
		MyEntity e = new MyEntity();
		e.setId(1L);
		e.setLongAttribute(1L);
		DefaultComparableSpecification<MyEntity,Long> spec = new DefaultComparableSpecification<MyEntity,Long>("longAttribute");
		spec.setValue(new GenericContinousRange<Long>(null, 2L));
		assertTrue(spec.isSatisfiedBy(e));
	}

}

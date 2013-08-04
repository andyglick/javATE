package it.amattioli.dominate.specifications.dflt;

import it.amattioli.dominate.specifications.ComparisonType;
import it.amattioli.dominate.specifications.MyEntity;
import junit.framework.TestCase;

public class DefaultStringSpecificationTest extends TestCase {

	public void testSatisfied() {
		MyEntity e = new MyEntity();
		e.setId(1L);
		e.setDescription("Descr");
		DefaultStringSpecification<MyEntity> spec = new DefaultStringSpecification<MyEntity>("description");
		spec.setComparisonType(ComparisonType.EXACT);
		spec.setValue("Descr");
		assertTrue(spec.isSatisfiedBy(e));
	}
	
	public void testSatisfiedCaseIgnore() {
		MyEntity e = new MyEntity();
		e.setId(1L);
		e.setDescription("descr");
		DefaultStringSpecification<MyEntity> spec = new DefaultStringSpecification<MyEntity>("description");
		spec.setComparisonType(ComparisonType.EXACT);
		spec.setValue("DEscr");
		assertTrue(spec.isSatisfiedBy(e));
	}
	
	public void testNotSatisfied() {
		MyEntity e = new MyEntity();
		e.setId(1L);
		e.setDescription("Descr");
		DefaultStringSpecification<MyEntity> spec = new DefaultStringSpecification<MyEntity>("description");
		spec.setComparisonType(ComparisonType.EXACT);
		spec.setValue("My");
		assertFalse(spec.isSatisfiedBy(e));
	}
	
	public void testNotSet() {
		MyEntity e = new MyEntity();
		e.setId(1L);
		e.setDescription("Descr");
		DefaultStringSpecification<MyEntity> spec = new DefaultStringSpecification<MyEntity>("description");
		assertTrue(spec.isSatisfiedBy(e));
	}
	
	public void testNotSatisfiedIfNotSet() {
		MyEntity e = new MyEntity();
		e.setId(1L);
		e.setDescription("Descr");
		DefaultStringSpecification<MyEntity> spec = new DefaultStringSpecification<MyEntity>("description");
		spec.setSatisfiedIfNotSet(false);
		assertFalse(spec.isSatisfiedBy(e));
	}
	
	public void testAssembler() {
		MyEntity e = new MyEntity();
		e.setId(1L);
		e.setDescription("Descr");
		DefaultStringSpecification<MyEntity> spec = new DefaultStringSpecification<MyEntity>("description");
		spec.setComparisonType(ComparisonType.EXACT);
		spec.setValue("Descr");
		PredicateAssembler a = new PredicateAssembler();
		spec.assembleQuery(a);
		assertTrue(a.assembledPredicate().evaluate(e));
	}
	
}

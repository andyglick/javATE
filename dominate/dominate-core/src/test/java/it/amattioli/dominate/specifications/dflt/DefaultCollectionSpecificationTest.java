package it.amattioli.dominate.specifications.dflt;

import it.amattioli.dominate.specifications.CollectionSpecification;
import it.amattioli.dominate.specifications.MyEntity;
import junit.framework.TestCase;

public class DefaultCollectionSpecificationTest extends TestCase {

	public void testConstruction() {
		CollectionSpecification<MyEntity> spec = CollectionSpecification.newInstance("collectionProperty");
		assertTrue(spec.supportsAssembler(new PredicateAssembler()));
	}
	
	public void testSatisfied() {
		MyEntity e = new MyEntity();
		e.setId(1L);
		e.addToCollectionProperty("Descr");
		DefaultCollectionSpecification<MyEntity> spec = new DefaultCollectionSpecification<MyEntity>("collectionProperty");
		spec.setValue("Descr");
		assertTrue(spec.isSatisfiedBy(e));
	}
	
	public void testNotSatisfied() {
		MyEntity e = new MyEntity();
		e.setId(1L);
		e.addToCollectionProperty("Descr");
		DefaultCollectionSpecification<MyEntity> spec = new DefaultCollectionSpecification<MyEntity>("collectionProperty");
		spec.setValue("Other");
		assertFalse(spec.isSatisfiedBy(e));
	}
	
	public void testNotSet() {
		MyEntity e = new MyEntity();
		e.setId(1L);
		e.addToCollectionProperty("Descr");
		DefaultCollectionSpecification<MyEntity> spec = new DefaultCollectionSpecification<MyEntity>("collectionProperty");
		assertTrue(spec.isSatisfiedBy(e));
	}
	
	public void testAssembler() {
		MyEntity e = new MyEntity();
		e.setId(1L);
		e.addToCollectionProperty("Descr");
		DefaultCollectionSpecification<MyEntity> spec = new DefaultCollectionSpecification<MyEntity>("collectionProperty");
		PredicateAssembler a = new PredicateAssembler();
		spec.assembleQuery(a);
		assertTrue(a.assembledPredicate().evaluate(e));
	}

}

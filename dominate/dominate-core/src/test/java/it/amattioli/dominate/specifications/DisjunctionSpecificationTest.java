package it.amattioli.dominate.specifications;

import static org.mockito.Mockito.*;

import it.amattioli.dominate.specifications.dflt.FalseSpecification;
import it.amattioli.dominate.specifications.dflt.PredicateAssembler;
import it.amattioli.dominate.specifications.dflt.TrueSpecification;
import junit.framework.TestCase;

public class DisjunctionSpecificationTest extends TestCase {

	public void testWasSet() {
		MyDisjunctionSpec spec = new MyDisjunctionSpec();
		spec.setDescription("d");
		assertTrue(spec.wasSet());
	}
	
	public void testWasNotSet() {
		MyDisjunctionSpec spec = new MyDisjunctionSpec();
		assertFalse(spec.wasSet());
	}
	
	public void testSatisfiedIfNotSet() {
		MyDisjunctionSpec spec = new MyDisjunctionSpec();
		assertTrue(spec.isSatisfiedBy(new MyEntity()));
	}
	
	public void testNotSatisfiedIfNotSet() {
		MyDisjunctionSpec spec = new MyDisjunctionSpec();
		spec.setSatisfiedIfNotSet(false);
		assertFalse(spec.isSatisfiedBy(new MyEntity()));
	}

	public void testSatisfied() {
		DisjunctionSpecification<MyEntity> spec = new DisjunctionSpecification<MyEntity>();
		spec.addSpecification(new TrueSpecification<MyEntity>());
		spec.addSpecification(new FalseSpecification<MyEntity>());
		assertTrue(spec.isSatisfiedBy(new MyEntity()));
	}
	
	public void testNotSatisfied() {
		DisjunctionSpecification<MyEntity> spec = new DisjunctionSpecification<MyEntity>();
		spec.addSpecification(new FalseSpecification<MyEntity>());
		spec.addSpecification(new FalseSpecification<MyEntity>());
		assertFalse(spec.isSatisfiedBy(new MyEntity()));
	}
	
	public void testAssembler() {
		DisjunctionSpecification<MyEntity> spec = new DisjunctionSpecification<MyEntity>();
		spec.addSpecification(new TrueSpecification<MyEntity>());
		spec.addSpecification(new FalseSpecification<MyEntity>());
		PredicateAssembler a = new PredicateAssembler();
		spec.assembleQuery(a);
		assertTrue(a.assembledPredicate().evaluate(new MyEntity()));
	}
	
	public void testAutofilteringEnabled() {
		DisjunctionSpecification<MyEntity> spec = DisjunctionSpecification.create();
		spec.setAutoFilteringEnabled(true);
		StringSpecification<MyEntity> subSpecification = StringSpecification.newInstance("description");
		spec.addSpecification(subSpecification);
		SpecificationChangeListener listener = mock(SpecificationChangeListener.class);
		spec.addSpecificationChangeListener(listener);
		subSpecification.setValue("a_value");
		verify(listener).specificationChange(any(SpecificationChangeEvent.class));
	}
	
	public void testAutofilteringDisabled() {
		DisjunctionSpecification<MyEntity> spec = DisjunctionSpecification.create();
		spec.setAutoFilteringEnabled(false);
		StringSpecification<MyEntity> subSpecification = StringSpecification.newInstance("description");
		spec.addSpecification(subSpecification);
		SpecificationChangeListener listener = mock(SpecificationChangeListener.class);
		spec.addSpecificationChangeListener(listener);
		subSpecification.setValue("a_value");
		verify(listener, never()).specificationChange(any(SpecificationChangeEvent.class));
	}
}

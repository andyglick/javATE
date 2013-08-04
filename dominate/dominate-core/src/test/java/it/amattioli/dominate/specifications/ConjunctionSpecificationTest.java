package it.amattioli.dominate.specifications;

import static org.mockito.Mockito.*;

import it.amattioli.dominate.specifications.dflt.FalseSpecification;
import it.amattioli.dominate.specifications.dflt.PredicateAssembler;
import it.amattioli.dominate.specifications.dflt.TrueSpecification;
import junit.framework.TestCase;

public class ConjunctionSpecificationTest extends TestCase {
	
	public void testWasNotSet() {
		MyConjunctionSpec spec = new MyConjunctionSpec();
		assertFalse(spec.wasSet());
	}
	
	public void testSatisfiedIfNotSet() {
		MyConjunctionSpec spec = new MyConjunctionSpec();
		assertTrue(spec.isSatisfiedBy(new MyEntity()));
	}
	
	public void testNotSatisfiedIfNotSet() {
		MyConjunctionSpec spec = new MyConjunctionSpec();
		spec.setSatisfiedIfNotSet(false);
		assertFalse(spec.isSatisfiedBy(new MyEntity()));
	}
	
	public void testSatisfied() {
		ConjunctionSpecification<MyEntity> spec = ConjunctionSpecification.create();
		spec.addSpecification(new TrueSpecification<MyEntity>());
		spec.addSpecification(new TrueSpecification<MyEntity>());
		assertTrue(spec.isSatisfiedBy(new MyEntity()));
	}
	
	public void testNotSatisfied() {
		ConjunctionSpecification<MyEntity> spec = ConjunctionSpecification.create();
		spec.addSpecification(new TrueSpecification<MyEntity>());
		spec.addSpecification(new FalseSpecification<MyEntity>());
		assertFalse(spec.isSatisfiedBy(new MyEntity()));
	}

	public void testAssembler() {
		ConjunctionSpecification<MyEntity> spec = ConjunctionSpecification.create();
		spec.addSpecification(new TrueSpecification<MyEntity>());
		spec.addSpecification(new TrueSpecification<MyEntity>());
		PredicateAssembler a = new PredicateAssembler();
		spec.assembleQuery(a);
		assertTrue(a.assembledPredicate().evaluate(new MyEntity()));
	}
	
	public void testAutofilteringEnabled() {
		ConjunctionSpecification<MyEntity> spec = ConjunctionSpecification.create();
		spec.setAutoFilteringEnabled(true);
		StringSpecification<MyEntity> subSpecification = StringSpecification.newInstance("description");
		spec.addSpecification(subSpecification);
		SpecificationChangeListener listener = mock(SpecificationChangeListener.class);
		spec.addSpecificationChangeListener(listener);
		subSpecification.setValue("a_value");
		verify(listener).specificationChange(any(SpecificationChangeEvent.class));
	}
	
	public void testAutofilteringDisabled() {
		ConjunctionSpecification<MyEntity> spec = ConjunctionSpecification.create();
		spec.setAutoFilteringEnabled(false);
		StringSpecification<MyEntity> subSpecification = StringSpecification.newInstance("description");
		spec.addSpecification(subSpecification);
		SpecificationChangeListener listener = mock(SpecificationChangeListener.class);
		spec.addSpecificationChangeListener(listener);
		subSpecification.setValue("a_value");
		verify(listener, never()).specificationChange(any(SpecificationChangeEvent.class));
	}
}

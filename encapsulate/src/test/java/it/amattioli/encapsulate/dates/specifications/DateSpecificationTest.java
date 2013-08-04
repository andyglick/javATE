package it.amattioli.encapsulate.dates.specifications;

import static org.mockito.Mockito.*;

import it.amattioli.dominate.specifications.SpecificationChangeEvent;
import it.amattioli.dominate.specifications.SpecificationChangeListener;
import it.amattioli.encapsulate.dates.Day;
import it.amattioli.encapsulate.dates.Month;
import junit.framework.TestCase;

public class DateSpecificationTest extends TestCase {

	public void testSatisfied() {
		DateSpecification<MyEntity> spec = DateSpecification.newInstance("myDate");
		MyEntity e = new MyEntity();
		e.setMyDate(Day.today().getInitTime());
		spec.setValue(Month.thisMonth());
		assertTrue(spec.isSatisfiedBy(e));
	}
	
	public void testSpecificationChange() {
		DateSpecification<MyEntity> spec = DateSpecification.newInstance("myDate");
		SpecificationChangeListener listener = mock(SpecificationChangeListener.class);
		spec.addSpecificationChangeListener(listener);
		spec.setValue(Month.thisMonth());
		verify(listener).specificationChange(any(SpecificationChangeEvent.class));
	}
}

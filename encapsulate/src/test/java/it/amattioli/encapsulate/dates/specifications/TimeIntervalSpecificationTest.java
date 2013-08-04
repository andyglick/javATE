package it.amattioli.encapsulate.dates.specifications;

import static org.mockito.Mockito.*;

import it.amattioli.dominate.specifications.SpecificationChangeEvent;
import it.amattioli.dominate.specifications.SpecificationChangeListener;
import it.amattioli.encapsulate.dates.Day;
import it.amattioli.encapsulate.dates.Month;
import junit.framework.TestCase;

public class TimeIntervalSpecificationTest extends TestCase {

	public void testSatisfied() {
		TimeIntervalSpecification<MyEntity> spec = TimeIntervalSpecification.newInstance("myTimeInterval");
		MyEntity e = new MyEntity();
		e.setMyTimeInterval(Month.thisMonth());
		spec.setIncludedValue(Day.today().getInitTime());
		assertTrue(spec.isSatisfiedBy(e));
	}
	
	public void testSpecificationChange() {
		TimeIntervalSpecification<MyEntity> spec = TimeIntervalSpecification.newInstance("myTimeInterval");
		SpecificationChangeListener listener = mock(SpecificationChangeListener.class);
		spec.addSpecificationChangeListener(listener);
		spec.setIncludedValue(Day.today().getInitTime());
		verify(listener).specificationChange(any(SpecificationChangeEvent.class));
	}
}

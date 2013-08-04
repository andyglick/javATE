package it.amattioli.dominate.specifications;

import static org.mockito.Mockito.*;

import it.amattioli.dominate.Specification;
import junit.framework.TestCase;

public class SpecificationChangeSupportTest extends TestCase {

	public void testFireSpecificationChange() {
		Specification<MyEntity> spec = mock(Specification.class);
		SpecificationChangeSupport support = new SpecificationChangeSupport();
		SpecificationChangeListener listener = mock(SpecificationChangeListener.class);
		support.addListener(listener);
		SpecificationChangeEvent evt = new SpecificationChangeEvent(spec);
		listener.specificationChange(evt);
		verify(listener).specificationChange(evt);
	}
	
}

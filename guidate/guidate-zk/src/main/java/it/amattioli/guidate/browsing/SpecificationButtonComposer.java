package it.amattioli.guidate.browsing;

import it.amattioli.dominate.Specification;
import it.amattioli.guidate.containers.BackBeans;

import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.util.GenericComposer;

public class SpecificationButtonComposer extends GenericComposer {

	public void onClick(Event evt) throws Exception {
    	Specification<?> spec = (Specification<?>)BackBeans.findTargetBackBean(evt);
    	spec.fireSpecificationChange();
	}
	
}

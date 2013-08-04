package it.amattioli.guidate.browsing;

import it.amattioli.dominate.Specification;
import it.amattioli.guidate.containers.BackBeans;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.KeyEvent;
import static org.zkoss.zk.ui.event.Events.*;
import org.zkoss.zk.ui.util.GenericComposer;

public class SpecificationComposer extends GenericComposer {

	public void onOK(Event evt) throws Exception {
    	if (evt instanceof KeyEvent) {
	    	Component reference = ((KeyEvent)evt).getReference();
	    	sendEvent(ON_BLUR, reference, null);
    	}
    	Specification<?> spec = (Specification<?>)BackBeans.findTargetBackBean(evt);
    	spec.fireSpecificationChange();
	}
	
}

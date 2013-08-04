package it.amattioli.guidate.collections;

import it.amattioli.dominate.specifications.dflt.BeanShellSpecification;
import it.amattioli.dominate.specifications.dflt.TrueSpecification;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;

public class PrototypeComposer extends AbstractPrototypeComposer {
	
	@Override
	public void onCreate(Event evt) throws Exception {
		Component comp = evt.getTarget();
		Object spec = comp.getAttribute(SPECIFICATION_ATTRIBUTE);
		if (spec == null) {
			spec = new TrueSpecification();
		} else if (spec instanceof String) {
			try {
				spec = Class.forName((String)spec).newInstance();
			} catch (ClassNotFoundException e) {
				spec = new BeanShellSpecification((String)spec);
			}
		}
		comp.setAttribute(SPECIFICATION_ATTRIBUTE, spec);
	}
}

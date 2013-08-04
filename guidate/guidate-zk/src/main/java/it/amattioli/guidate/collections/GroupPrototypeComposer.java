package it.amattioli.guidate.collections;

import it.amattioli.guidate.properties.GroupSpecification;

import org.zkoss.zk.ui.event.Event;

public class GroupPrototypeComposer extends AbstractPrototypeComposer {

	@Override
	public void onCreate(Event evt) throws Exception {
		evt.getTarget().setAttribute(SPECIFICATION_ATTRIBUTE, new GroupSpecification());
	}

}

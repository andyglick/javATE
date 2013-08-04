package it.amattioli.guidate.collections;

import it.amattioli.guidate.containers.BackBeans;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.util.Composer;

public abstract class AbstractPrototypeComposer implements Composer {

	public static final String SPECIFICATION_ATTRIBUTE = PrototypeComposer.class.getCanonicalName() + ".SpecificationAttribute";

	public AbstractPrototypeComposer() {
		super();
	}

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		comp.setAttribute(BackBeans.STOP_FINDING, true);
		comp.addEventListener("onCreate", new EventListener() {
			
			@Override
			public void onEvent(Event event) throws Exception {
				onCreate(event);
			}
			
		});
	}

	public abstract void onCreate(Event evt) throws Exception;

}
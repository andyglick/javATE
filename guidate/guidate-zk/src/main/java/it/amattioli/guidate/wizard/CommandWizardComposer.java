package it.amattioli.guidate.wizard;

import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.Events;

public class CommandWizardComposer extends AbstractWizardComposer {

	public CommandWizardComposer(WizardState state) {
		super(state);
	}

	@Override
	public void onFinish(Event evt) {
		super.onFinish(evt);
		Events.sendEvent(new Event("onDoCommand",evt.getTarget()));
	}

}

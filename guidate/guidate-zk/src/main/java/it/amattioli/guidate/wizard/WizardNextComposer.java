package it.amattioli.guidate.wizard;

import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.util.GenericComposer;
import org.zkoss.zul.Button;

public class WizardNextComposer extends GenericComposer {

	public void onCreate(Event createEvt) {
		final Button btn = (Button)createEvt.getTarget(); 
		AbstractWizardComposer.find(createEvt.getTarget()).addEventListener(WizardNavigationEvent.NAME,
                                                                            new EventListener() {

			@Override
			public void onEvent(Event evt) throws Exception {
				btn.setDisabled(!((WizardNavigationEvent)evt).hasNext());
			}
		
		});
	}
}

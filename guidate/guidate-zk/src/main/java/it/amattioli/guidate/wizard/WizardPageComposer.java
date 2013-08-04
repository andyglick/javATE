package it.amattioli.guidate.wizard;

import it.amattioli.guidate.containers.BackBeans;

import java.util.HashMap;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.util.GenericComposer;

public class WizardPageComposer extends GenericComposer {

	public void onCreate(Event createEvt) {
		final Component wizardPage = (Component)createEvt.getTarget(); 
		AbstractWizardComposer.find(createEvt.getTarget()).addEventListener(WizardNavigationEvent.NAME,
                                                                            new EventListener() {

			@Override
			public void onEvent(Event evt) throws Exception {
				WizardNavigationEvent navEvent = (WizardNavigationEvent)evt;
				wizardPage.getChildren().clear();
				HashMap<String, Object> arg = new HashMap<String, Object>();
				arg.put("wizardBean", BackBeans.findBackBean(wizardPage));
				Executions.createComponents(navEvent.getCurrentPage(), wizardPage, arg);
			}
		
		});
	}
}

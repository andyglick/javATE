package it.amattioli.guidate.properties;

import org.apache.commons.beanutils.PropertyUtils;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.Events;

public class TogglePropertyComposer extends ImagePropertyComposer {

	public void onClick(Event evt) throws Exception {
		Component comp = evt.getTarget();
		Boolean val = (Boolean)PropertyUtils.getProperty(getBackBean(comp), getPropertyName());
		Boolean newVal = val == null ? true : !val;
		PropertyUtils.setProperty(getBackBean(comp), getPropertyName(), newVal);
		Events.sendEvent(new Event(BindingUpdater.ON_BINDING_UPDATE, comp));
	}
	
}

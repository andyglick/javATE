package it.amattioli.guidate.smarttab;

import java.util.Collection;

import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.util.GenericComposer;
import org.zkoss.zk.ui.event.EventQueues;
import org.zkoss.zul.Tab;
import org.zkoss.zul.Tabbox;
import org.zkoss.zul.Tabpanel;

public class SmartTabComposer extends GenericComposer {

	public static final String SMART_TAB_IDENTIFIER = "smartTabIdentifier";

	public void onSmartTabOpen(SmartTabOpenEvent evt) {
		SmartTabDescriptor descriptor = evt.getDescriptor();
		Tabbox box = (Tabbox)evt.getTarget();
		Tab newTab = findTab(box, descriptor);
		if (newTab == null) {
    		newTab = createTab(box, descriptor);
		} 
		newTab.setSelected(true);
	}

	private Tab createTab(final Tabbox box, final SmartTabDescriptor descriptor) {
		Tab newTab;
		newTab = new Tab();
		newTab.setClosable(true);
		newTab.setLabel(descriptor.getLabel());
		newTab.setImage(descriptor.getImage());
		newTab.setAttribute(SMART_TAB_IDENTIFIER, descriptor.getIdentifier());
		newTab.addEventListener("onClose", new EventListener() {

			@Override
			public void onEvent(Event evt) throws Exception {
				EventQueues.lookup(descriptor.getIdentifier()).publish(evt);
			}
			
		});
		box.getTabs().appendChild(newTab);
		Tabpanel tabpanel = new Tabpanel();
		tabpanel.appendChild(descriptor.getPanelContent());
		box.getTabpanels().appendChild(tabpanel);
		return newTab;
	}
	
	private Tab findTab(Tabbox box, SmartTabDescriptor descriptor) {
		String identifier = descriptor.getIdentifier();
		for (Tab tab : (Collection<Tab>)box.getTabs().getChildren()) {
			if (identifier.equals(tab.getAttribute(SMART_TAB_IDENTIFIER))) {
				return tab;
			}
		}
		return null;
	}
	
}

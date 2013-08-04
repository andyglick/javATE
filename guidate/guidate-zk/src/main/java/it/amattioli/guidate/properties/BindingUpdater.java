package it.amattioli.guidate.properties;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.Events;

public class BindingUpdater implements PropertyChangeListener {
	public static final String ON_BINDING_UPDATE = "onBindingUpdate";
	private String propertyName;
	private Component component;
	
	public BindingUpdater(String propertyName, Component component) {
		this.propertyName = propertyName;
		this.component = component;
	}
	
	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		if (evt.getPropertyName() == null
			|| propertyName.equals(evt.getPropertyName())
			|| propertyName.startsWith(evt.getPropertyName()+".")) {
			Events.postEvent(new Event(ON_BINDING_UPDATE, component));
		}
	}

}

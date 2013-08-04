package it.amattioli.guidate.util;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.Events;

public class EventPoster extends ComponentVisitor {
	private String eventName;
	
	public EventPoster(String eventName) {
		this.eventName = eventName;
	}
	
	@Override
	protected void preVisit(Component comp) {
		// Do nothing
	}
	
	@Override
	protected void postVisit(Component comp) {
		Events.postEvent(new Event(eventName, comp));
	}

}

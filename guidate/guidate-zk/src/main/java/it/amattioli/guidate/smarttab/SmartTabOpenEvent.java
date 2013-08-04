package it.amattioli.guidate.smarttab;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;

public class SmartTabOpenEvent extends Event {

	public SmartTabOpenEvent(Component target, SmartTabDescriptor data) {
		super("onSmartTabOpen", target, data);
	}
	
	public SmartTabDescriptor getDescriptor() {
		return (SmartTabDescriptor)getData();
	}
	
}

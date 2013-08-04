package it.amattioli.guidate.browsing;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zul.Listheader;

public class SortEvent extends Event {
	private Listheader column;
	private boolean reset;

	public SortEvent(String name, Component target, Listheader column, boolean reset) {
		super(name, target, null);
		this.column = column;
		this.reset = reset;
	}

	public Listheader getColumn() {
		return column;
	}

	public void setColumn(Listheader column) {
		this.column = column;
	}

	public boolean isReset() {
		return reset;
	}

	public void setReset(boolean reset) {
		this.reset = reset;
	}
	
}

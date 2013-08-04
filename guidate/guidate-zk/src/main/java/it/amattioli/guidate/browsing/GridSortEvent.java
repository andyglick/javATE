package it.amattioli.guidate.browsing;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zul.Column;

public class GridSortEvent extends Event {
	private Column column;
	private boolean reset;

	public GridSortEvent(String name, Component target, Column column, boolean reset) {
		super(name, target, null);
		this.column = column;
		this.reset = reset;
	}

	public Column getColumn() {
		return column;
	}

	public void setColumn(Column column) {
		this.column = column;
	}

	public boolean isReset() {
		return reset;
	}

	public void setReset(boolean reset) {
		this.reset = reset;
	}
	
}

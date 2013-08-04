package it.amattioli.guidate.browsing;

import it.amattioli.guidate.properties.PropertyNameRetriever;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.event.MouseEvent;
import org.zkoss.zk.ui.util.GenericAutowireComposer;
import org.zkoss.zul.Column;
import org.zkoss.zul.Row;

public class BrowserColumnComposer extends GenericAutowireComposer {
	public static final String ORDER_COLUMN_ATTRIBUTE = "orderColumn";

	private Column getSelf() {
		return (Column)self;
	}
	
	public String getOrderColumn() {
		return (String)self.getAttribute(ORDER_COLUMN_ATTRIBUTE);
	}

	public void setOrderColumn(String orderColumn) {
		self.setAttribute(ORDER_COLUMN_ATTRIBUTE, orderColumn);
	}

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		getSelf().setSort("auto");
	}
	
	private int getColumnIndex(Column col) {
		return col.getParent().getChildren().indexOf(col);
	}
	
	private String retrievePropertyName(Component component) {
		PropertyNameRetriever retriever = new PropertyNameRetriever(component);
		return retriever.retrieve();
	}

	public void onCreate() {
		if (getOrderColumn() == null) {
			int idx = getColumnIndex(getSelf());
			Row prototype =(Row)getSelf().getGrid().getRows().getChildren().get(0);
			Component rowcell = (Component)prototype.getChildren().get(idx);
			setOrderColumn(retrievePropertyName(rowcell));
		}
	}
	
	public void onClick(MouseEvent event) {
		Events.sendEvent(new GridSortEvent("onSort",getSelf().getGrid(),getSelf(),!(event.getKeys() == MouseEvent.CTRL_KEY)));
	}

	public void onSort(Event event) {
		event.stopPropagation();
	}
}

package it.amattioli.guidate.browsing;

import it.amattioli.guidate.properties.PropertyNameRetriever;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.event.MouseEvent;
import org.zkoss.zk.ui.util.GenericAutowireComposer;
import org.zkoss.zul.Listheader;
import org.zkoss.zul.Listitem;

public class BrowserListheaderComposer extends GenericAutowireComposer {
	public static final String ORDER_COLUMN_ATTRIBUTE = "orderColumn";

	private Listheader getSelf() {
		return (Listheader)self;
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

	public void onCreate() {
		if (getOrderColumn() == null) {
			int idx = getSelf().getColumnIndex();
			Listitem prototype =(Listitem)getSelf().getListbox().getItems().get(0);
			Component listcell = (Component)prototype.getChildren().get(idx);
			PropertyNameRetriever retriever = new PropertyNameRetriever(listcell);
			setOrderColumn(retriever.retrieve());
		}
	}
	
	public void onClick(MouseEvent event) {
		Events.sendEvent(new SortEvent("onSort",getSelf().getListbox(),getSelf(),!(event.getKeys() == MouseEvent.CTRL_KEY)));
	}

	public void onSort(Event event) {
		event.stopPropagation();
	}
}

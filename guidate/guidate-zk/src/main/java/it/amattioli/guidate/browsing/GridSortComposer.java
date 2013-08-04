package it.amattioli.guidate.browsing;

import it.amattioli.applicate.browsing.ListBrowser;
import it.amattioli.guidate.containers.BackBeans;

import java.util.Collection;
import java.util.HashSet;

import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.util.GenericComposer;
import org.zkoss.zul.Column;

public class GridSortComposer extends GenericComposer {
	private Collection<Column> lastSortedColumns = new HashSet<Column>();
	
	public ListBrowser<?, ?> getBrowser(Event evt) {
		return (ListBrowser<?, ?>)BackBeans.findTargetBackBean(evt);
	}
	
	public void onSort(GridSortEvent evt) {
    	Column column = evt.getColumn();
    	if (evt.isReset()) {
	    	for (Column curr: lastSortedColumns) {
	    		curr.setSortDirection("natural");
	    	}
	    	lastSortedColumns.clear();
	    	getBrowser(evt).setOrder((String)column.getAttribute(BrowserColumnComposer.ORDER_COLUMN_ATTRIBUTE));
    	} else {
    		getBrowser(evt).addOrder((String)column.getAttribute(BrowserColumnComposer.ORDER_COLUMN_ATTRIBUTE));
    	}
        column.setSortDirection(getBrowser(evt).getReverseOrder() ? "descending" : "ascending");
        lastSortedColumns.add(column);
    }

}

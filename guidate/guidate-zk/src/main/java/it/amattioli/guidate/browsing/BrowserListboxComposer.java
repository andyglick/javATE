package it.amattioli.guidate.browsing;

import java.util.Collection;
import java.util.HashSet;

import it.amattioli.applicate.browsing.ListBrowser;
import it.amattioli.applicate.selection.SelectionEvent;
import it.amattioli.applicate.selection.SelectionListener;
import it.amattioli.guidate.containers.BackBeans;

import org.zkoss.zk.ui.event.SelectEvent;
import org.zkoss.zk.ui.util.GenericAutowireComposer;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listheader;
import org.zkoss.zul.Listitem;

public class BrowserListboxComposer extends GenericAutowireComposer {
    private ListBrowser<?, ?> browser;
    private Collection<Listheader> lastSortedColumns = new HashSet<Listheader>();
	private SelectionListener selectionListener;
    
    public void onCreate() {
    	this.browser = (ListBrowser<?, ?>)BackBeans.findBackBean(self);
    	if (browser == null) {
    		//throw new BackBeanNotFoundException();
    		return;
    	}
    	final Listbox listbox = (Listbox)self;
    	setRenderer(listbox);
        setModel(listbox);
        setBrowserSelectionListener(listbox);
    }

    private void setRenderer(final Listbox listbox) {
		listbox.setItemRenderer(new it.amattioli.guidate.collections.PrototypeListItemRenderer(listbox));
	}
    
    private void setModel(final Listbox listbox) {
		listbox.setModel(new ListBrowserModel(browser));
	}
    
	private void setBrowserSelectionListener(final Listbox listbox) {
		selectionListener = new SelectionListener() {

			@Override
			public void objectSelected(SelectionEvent event) {
				if (browser.isMultipleSelection()) {
					listbox.clearSelection();
					for (Integer idx: browser.getSelectedIndexes()) {
						listbox.getItemAtIndex(idx).setSelected(true);
					}
				} else {
					if (browser.getSelectedIndex() != null && browser.getSelectedIndex() < listbox.getItemCount()) {
						listbox.setSelectedIndex(browser.getSelectedIndex());
					} else {
						listbox.setSelectedIndex(-1);
					}
				}
			}
        	
        };
		browser.addSelectionListener(selectionListener);
	}

    public void onSelect(SelectEvent evt) {
        browser.deselect();
        for (Object curr:evt.getSelectedItems()) {
            browser.select(((Listitem)curr).getIndex());
        }
    }
    
    public void onSort(SortEvent evt) {
    	Listheader column = evt.getColumn();
        if (evt.isReset()) {
        	for (Listheader curr: lastSortedColumns) {
        		curr.setSortDirection("natural");
        	}
        	lastSortedColumns.clear();
            browser.setOrder((String)column.getAttribute(BrowserListheaderComposer.ORDER_COLUMN_ATTRIBUTE));
        } else {
        	browser.addOrder((String)column.getAttribute(BrowserListheaderComposer.ORDER_COLUMN_ATTRIBUTE));
        }
        column.setSortDirection(browser.getReverseOrder() ? "descending" : "ascending");
        lastSortedColumns.add(column);
    }

}

package it.amattioli.guidate.browsing;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import it.amattioli.applicate.browsing.GroupBrowser;
import it.amattioli.applicate.browsing.GroupBrowser.Selection;
import it.amattioli.applicate.selection.SelectionEvent;
import it.amattioli.applicate.selection.SelectionListener;
import it.amattioli.dominate.Entity;
import it.amattioli.dominate.groups.EntityGroup;
import it.amattioli.guidate.containers.BackBeans;

import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.SelectEvent;
import org.zkoss.zk.ui.util.GenericAutowireComposer;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;

public class GroupBrowserListboxComposer extends GenericAutowireComposer {
    private GroupBrowser browser;
    //private Listheader lastSortedColumn;
	private SelectionListener selectionListener;
    
    public void onCreate() {
    	this.browser = (GroupBrowser<?,?>)BackBeans.findBackBean(self);
    	if (browser == null) {
    		//throw new BackBeanNotFoundException();
    		return;
    	}
    	final Listbox listbox = (Listbox)self;
    	listbox.setMultiple(browser.isMultiple());
    	setRenderer(listbox);
        setModel(listbox);
        setBrowserSelectionListener(listbox);
    }

    private void setRenderer(final Listbox listbox) {
        listbox.setItemRenderer(new it.amattioli.guidate.collections.PrototypeListItemRenderer(listbox));
	}
    
    private void setModel(final Listbox listbox) {
		listbox.setModel(new GroupBrowserModel(browser));
	}
    
	private void setBrowserSelectionListener(final Listbox listbox) {
		selectionListener = new SelectionListener() {

			@Override
			public void objectSelected(SelectionEvent event) {
				
				if (browser.isMultiple()) {
					listbox.clearSelection();
					for (Selection sel: (List<Selection>)browser.getSelections()) {
						listbox.getItemAtIndex(calcListboxIndex(sel)).setSelected(true);
					}
					for (Integer idx: (List<Integer>)browser.getSelectedGroupsIndex()) {
						listbox.getItemAtIndex(calcGroupListboxIndex(idx)).setSelected(true);
					}
				} else {
					if (browser.getSelection() != null) {
						listbox.setSelectedIndex(calcListboxIndex(browser.getSelection()));
					} else {
						listbox.setSelectedIndex(-1);
					}
				}
			}
        	
        };
		browser.addSelectionListener(selectionListener);
	}

    protected int calcListboxIndex(Selection sel) {
    	return calcGroupListboxIndex(sel.getGroupIndex()) + sel.getMemberIndex() + 1;
    }
    
    protected int calcGroupListboxIndex(int groupIndex) {
    	int result = 0;
		for (int i = 0; i < groupIndex; i++) {
			result += ((EntityGroup)browser.getGroups().get(i)).size() + 1;
		}
		return result;
    }

    private Set<Listitem> lastSelection = new HashSet<Listitem>();
    
	public void onSelect(SelectEvent evt) {
		/*
        browser.deselect();
        for (Object curr: evt.getSelectedItems()) {
            Object itemValue = ((Listitem)curr).getValue();
            if (itemValue instanceof Entity && !isSelectedGroupMember((Entity)itemValue)) {
            	browser.select((Entity<?>)itemValue);
            } else if (itemValue instanceof EntityGroup) {
            	browser.selectGroup((EntityGroup<?,?>)itemValue);
            }
        }
        */
		Set<Listitem> currSelection = new HashSet<Listitem>(((Listbox)evt.getTarget()).getSelectedItems());
		if (currSelection.size() > lastSelection.size()) {
			currSelection.removeAll(lastSelection);
		} else {
			lastSelection.removeAll(currSelection);
			currSelection = lastSelection;
		}
		for (Object curr: currSelection) {
            Object itemValue = ((Listitem)curr).getValue();
            if (itemValue instanceof Entity) {
            	browser.select((Entity<?>)itemValue);
            } else if (itemValue instanceof EntityGroup) {
            	browser.selectGroup((EntityGroup<?,?>)itemValue);
            }
        }
		lastSelection = new HashSet<Listitem>(((Listbox)evt.getTarget()).getSelectedItems());
    }
	
	private boolean isSelectedGroupMember(Entity e) {
		for (Integer idx: (List<Integer>)browser.getSelectedGroupsIndex()) {
			EntityGroup group = browser.getGroup(idx);
			if (group.contains(e)) {
				return true;
			}
		}
		return false;
	}
    
    public void onSort(Event evt) {
    	/*
        if (lastSortedColumn != null) {
            lastSortedColumn.setSortDirection("natural");
        }
        Listheader column = (Listheader)evt.getData();
        browser.setOrder((String)column.getAttribute(BrowserListheaderComposer.ORDER_COLUMN_ATTRIBUTE));
        column.setSortDirection(browser.getReverseOrder() ? "descending" : "ascending");
        lastSortedColumn = column;
        */
    }

}

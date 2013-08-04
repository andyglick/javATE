package it.amattioli.guidate.collections;

import java.util.HashSet;
import java.util.Set;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.event.SelectEvent;
import org.zkoss.zk.ui.util.GenericComposer;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Row;

public class ItemSelectionComposer extends GenericComposer {
	private String id;
	
	public ItemSelectionComposer(String id) {
		this.id = id;
	}
	
	private Component findItem(Component comp) {
		if (comp.getUuid().equals(id)) {
			return comp;
		}
		if (comp.getParent() != null) {
			return findItem(comp.getParent());
		}
		return null;
	}
	
	private void selectItem(Component comp) {
		Component itemComp = findItem(comp);
		if (itemComp instanceof Listitem) {
			Listitem item = (Listitem)itemComp;
			if (item != null && item.getListbox() != null) {
				Set<Listitem> selectedItems;
				Listbox listbox = item.getListbox();
				if (listbox.isMultiple()) {
					selectedItems = new HashSet<Listitem>(listbox.getSelectedItems());
				} else {
					selectedItems = new HashSet<Listitem>();
				}
				selectedItems.add(item);
				listbox.selectItem(item);
				Events.postEvent(new SelectEvent(Events.ON_SELECT, listbox, selectedItems));
			}
		} else if (itemComp instanceof Row) {
			final Row row = (Row)itemComp;
			if (row != null && row.getGrid() != null) {
				Set<Row> selectedRows = new HashSet<Row>() {{ add(row); }};
				Events.postEvent(new SelectEvent(Events.ON_SELECT, row.getGrid(), selectedRows));
			}
		}
	}
	
	public void onClick(Event evt) {
		selectItem(evt.getTarget());
	}
	
	public void onFocus(Event evt) {
		selectItem(evt.getTarget());
	}
	
}

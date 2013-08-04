package it.amattioli.guidate.browsing.tree;

import java.util.HashSet;
import java.util.Set;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.event.SelectEvent;
import org.zkoss.zk.ui.util.GenericComposer;
import org.zkoss.zul.Tree;
import org.zkoss.zul.Treeitem;

public class TreeItemSelectionComposer extends GenericComposer {
	private String id;
	
	public TreeItemSelectionComposer(String id) {
		this.id = id;
	}
	
	private Component findItem(Component comp) {
		if (comp.getId().equals(id)) {
			return comp;
		}
		if (comp.getParent() != null) {
			return findItem(comp.getParent());
		}
		return null;
	}
	
	private void selectItem(Component comp) {
		Component itemComp = findItem(comp);
		if (itemComp instanceof Treeitem) {
			Treeitem item = (Treeitem)itemComp;
			if (item != null && item.getTree() != null) {
				Tree tree = item.getTree();
				tree.selectItem(item);
				Set<Treeitem> selectedItems = new HashSet<Treeitem>();
				selectedItems.add(item);
				Events.postEvent(new SelectEvent(Events.ON_SELECT, tree, selectedItems));
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

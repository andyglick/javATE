package it.amattioli.guidate.browsing.tree;

import java.util.ArrayList;

import it.amattioli.applicate.browsing.ContentChangeEvent;
import it.amattioli.applicate.browsing.ContentChangeListener;
import it.amattioli.applicate.browsing.TreeBrowser;
import it.amattioli.applicate.browsing.TreePath;
import it.amattioli.applicate.selection.SelectionEvent;
import it.amattioli.applicate.selection.SelectionListener;
import it.amattioli.guidate.containers.BackBeans;

import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.event.SelectEvent;
import org.zkoss.zk.ui.util.GenericComposer;
import org.zkoss.zul.Tree;
import org.zkoss.zul.Treechildren;
import org.zkoss.zul.Treeitem;

public class BrowserTreeComposer extends GenericComposer {

	public void onCreate(Event evt) {
		final TreeBrowser<?,?> browser = (TreeBrowser<?,?>)BackBeans.findTargetBackBean(evt);
		if (browser == null) {
			return;
		}
		final Tree tree = (Tree)evt.getTarget();
		Treeitem prototype = (Treeitem)tree.getItems().iterator().next();
		prototype.detach();
		tree.setTreeitemRenderer(new PrototypeTreeItemRenderer(prototype));
		tree.setModel(new TreeBrowserModel(browser));
		initContentChangeListener(browser, tree);
		initSelectionListener(browser, tree);
		if (browser.getSelectedPath() != null) {
			selectItem(tree, browser.getSelectedPath());
		}
	}

	private void initSelectionListener(final TreeBrowser<?, ?> browser, final Tree tree) {
		SelectionListener listener = new SelectionListener() {

			@Override
			public void objectSelected(SelectionEvent event) {
				TreePath path = ((TreeBrowser<?, ?>)event.getSource()).getSelectedPath();
				selectItem(tree, path);
			}
			
		};
		browser.addSelectionListener(listener);
		tree.setAttribute("BrowserTreeComposer.selectionListener", listener);
	}

	private void initContentChangeListener(final TreeBrowser<?, ?> browser, final Tree tree) {
		ContentChangeListener browserListener = new ContentChangeListener() {
			
			@Override
			public void contentChanged(ContentChangeEvent event) {
				tree.setModel(new TreeBrowserModel(browser));
			}
		};
		browser.addContentChangeListener(browserListener);
		tree.setAttribute("BrowserTreeComposer.contentChangeListener", browserListener);
	}
	
	public void onOpenForSelection(Event evt) {
		selectItem((Tree)evt.getTarget(), new TreePath((String)evt.getData()));
	}
	
	private void selectItem(Tree tree, TreePath path) {
		Treechildren treechildren = tree.getTreechildren();
		Treeitem item = null;
		for (int i = 0; i < path.depth(); i++) {
			if (treechildren.getChildren().size() == 0) {
				item.setOpen(true);
				Events.echoEvent("onOpenForSelection", tree, path.toString());
			}
			item = ((Treeitem)treechildren.getChildren().get(path.elementAt(i)));
			treechildren = item.getTreechildren();
		}
		item.setSelected(true);
	}

	public void onSelect(SelectEvent evt) {
		Treeitem item = (Treeitem)evt.getSelectedItems().iterator().next();
		ArrayList<Integer> path = findItemPath(item);
		TreeBrowser<?,?> browser = (TreeBrowser<?,?>)BackBeans.findTargetBackBean(evt);
		browser.select(new TreePath(path));
	}
	
	private ArrayList<Integer> findItemPath(Treeitem item) {
		ArrayList<Integer> path = new ArrayList<Integer>();
		Treeitem curr = item;
		do {
			path.add(0, curr.getParent().getChildren().indexOf(curr));
			curr = curr.getParentItem();
		} while (curr != null);
		return path;
	}
}

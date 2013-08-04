package it.amattioli.guidate.editing;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;

import it.amattioli.applicate.browsing.TreePath;
import it.amattioli.applicate.commands.tree.TreeEditor;
import it.amattioli.applicate.selection.SelectionEvent;
import it.amattioli.applicate.selection.SelectionListener;
import it.amattioli.dominate.properties.PropertyChangeEmitter;
import it.amattioli.guidate.browsing.tree.PrototypeTreeItemRenderer;
import it.amattioli.guidate.containers.BackBeans;

import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.event.SelectEvent;
import org.zkoss.zk.ui.util.GenericComposer;
import org.zkoss.zul.Tree;
import org.zkoss.zul.Treechildren;
import org.zkoss.zul.Treeitem;

public class EditorTreeComposer extends GenericComposer {

	public void onCreate(Event evt) {
		final TreeEditor<?,?> editor = (TreeEditor<?,?>)BackBeans.findTargetBackBean(evt);
		if (editor == null) {
			return;
		}
		final Tree tree = (Tree)evt.getTarget();
		configureRenderer(tree);
		configureModel(editor, tree);
		listenToSelection(editor, tree);
		listenToPropertyChange(editor, tree);
		
		if (editor.getSelectedPath() != null) {
			selectItem(tree, editor.getSelectedPath());
		}
	}

	private void configureModel(final TreeEditor<?, ?> editor, final Tree tree) {
		tree.setModel(new TreeEditorModel(editor));
	}

	private void configureRenderer(final Tree tree) {
		Treeitem prototype = (Treeitem)tree.getItems().iterator().next();
		prototype.detach();
		tree.setTreeitemRenderer(new PrototypeTreeItemRenderer(prototype));
	}

	private void listenToPropertyChange(final TreeEditor<?, ?> editor, final Tree tree) {
		if (editor instanceof PropertyChangeEmitter) {
			PropertyChangeListener pChangeListener = new PropertyChangeListener() {
				
				@Override
				public void propertyChange(PropertyChangeEvent evt) {
					if ("root".equals(evt.getPropertyName())) {
						configureModel(editor, tree);
					}
				}
			};
			((PropertyChangeEmitter)editor).addPropertyChangeListener(pChangeListener);
			tree.setAttribute("BrowserTreeComposer.pChangeListener", pChangeListener);
		}
	}

	private void listenToSelection(final TreeEditor<?, ?> editor, final Tree tree) {
		SelectionListener listener = new SelectionListener() {

			@Override
			public void objectSelected(SelectionEvent event) {
				TreePath path = ((TreeEditor<?, ?>)event.getSource()).getSelectedPath();
				selectItem(tree, path);
			}
			
		};
		editor.addSelectionListener(listener);
		tree.setAttribute("BrowserTreeComposer.selectionListener", listener);
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
		int[] path = findItemPath(item);
		TreeEditor<?,?> browser = (TreeEditor<?,?>)BackBeans.findTargetBackBean(evt);
		browser.select(new TreePath(path));
	}
	
	private int[] findItemPath(Treeitem item) {
		ArrayList<Integer> path = new ArrayList<Integer>();
		Treeitem curr = item;
		do {
			path.add(0, curr.getParent().getChildren().indexOf(curr));
			curr = curr.getParentItem();
		} while (curr != null);
		int[] result = new int[path.size()];
		for (int idx = 0; idx < path.size(); idx++ ) {
			result[idx] = path.get(idx);
		}
		return result;
	}
	
	public void onAddTreeChild(Event evt) {
		Tree tree = (Tree)evt.getTarget();
		TreeEditor<?,?> editor = (TreeEditor<?,?>)BackBeans.findBackBean(tree);
		editor.addChild();
		Events.postEvent(new Event("onNewItemSelect", tree));
	}
	
	public void onAddTreeSibling(Event evt) {
		Tree tree = (Tree)evt.getTarget();
		TreeEditor<?,?> editor = (TreeEditor<?,?>)BackBeans.findBackBean(tree);
		editor.addSibling();
		Events.postEvent(new Event("onNewItemSelect", tree));
	}
	
	public void onRemoveTreeNode(Event evt) {
		TreeEditor<?,?> editor = (TreeEditor<?,?>)BackBeans.findBackBean(evt.getTarget());
		editor.remove();
	}
}

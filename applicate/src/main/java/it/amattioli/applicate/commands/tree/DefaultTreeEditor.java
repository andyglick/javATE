package it.amattioli.applicate.commands.tree;

import it.amattioli.applicate.browsing.TreePath;
import it.amattioli.applicate.editors.BeanEditor;
import it.amattioli.applicate.selection.SelectionEvent;
import it.amattioli.applicate.selection.SelectionListener;
import it.amattioli.applicate.selection.SelectionSupport;
import it.amattioli.dominate.properties.PropertyChangeSupport;
import it.amattioli.dominate.Entity;
import it.amattioli.dominate.properties.PropertyChangeEmitter;

import java.beans.PropertyChangeListener;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.ObjectUtils;

public class DefaultTreeEditor<I extends Serializable, T extends Entity<I>> implements TreeEditor<I,T>, PropertyChangeEmitter {
	private TreeManager<T> treeManager;
	private TreePath selectedPath;
	private Map<String, BeanEditor<T>> nodeEditors = new HashMap<String, BeanEditor<T>>();
	private PropertyChangeSupport pChange = new PropertyChangeSupport(this);
	private TreeEventSupport treeEventSupport = new TreeEventSupport();
	
	private SelectionSupport selectionSupport = new SelectionSupport();

	public TreeManager<T> getTreeManager() {
		return treeManager;
	}

	public void setTreeManager(TreeManager<T> treeManager) {
		this.treeManager = treeManager;
		initEditors();
		firePropertyChange("treeManager", null, null);
		firePropertyChange("root", null, null);
	}
	
	protected void initEditors() {
		nodeEditors.clear();
	}
	
	public T getRoot() {
		return getTreeManager().getRoot();
	}
	
	public void setRoot(T root) {
		if (getTreeManager() == null) {
			setTreeManager(new DefaultTreeManager<T>(root));
		} else {
			getTreeManager().setRoot(root);
			initEditors();
			firePropertyChange("root", null, null);
		}
	}
	
	public List<T> getChildrenOf(T target) {
		return getTreeManager().getChildrenOf(target);
	}
	
	public T getParentOf(T target) {
		return getTreeManager().getParentOf(target);
	}
	
	public TreePath getPathOf(T target) {
		return getTreeManager().getPathOf(target);
	}
	
	public T getTargetOf(TreePath path) {
		return getTreeManager().getTargetOf(path);
	}

	public void select(TreePath path) {
		if (!ObjectUtils.equals(this.selectedPath,path)) {
			this.selectedPath = path;
			notifySelectionListeners();
		}
	}

	public void select(T selected) {
		select(getPathOf(selected));
	}
	
	public void deselect() {
		select((TreePath)null);
	}

	public T getSelectedObject() {
		if (selectedPath == null) {
			return null;
		}
		return getTargetOf(selectedPath);
	}
	
	public TreePath getSelectedPath() {
		return selectedPath;
	}
	
	public T addChild() {
		T parent = getSelectedObject();
		if (parent == null) {
			parent = getRoot();
		}
		BeanEditor<T> editor = getTreeManager().addChild(parent);
		TreePath addedPath = getPathOf(editor.getEditingBean());
		nodeEditors.put(addedPath.toString(), editor);
		fireTreeEvent(TreeEvent.Type.CHILD_ADDED, addedPath);
		select(addedPath);
		return editor.getEditingBean();
	}
	
	public T addSibling() {
		T parent = getSelectedObject();
		if (parent != null) {
			parent = getParentOf(parent);
		}
		BeanEditor<T> editor = getTreeManager().addChild(parent);
		TreePath addedPath = getPathOf(editor.getEditingBean());
		nodeEditors.put(addedPath.toString(), editor);
		fireTreeEvent(TreeEvent.Type.CHILD_ADDED, addedPath);
		select(addedPath);
		return editor.getEditingBean();
	}
	
	public T remove() {
		T toBeRemoved = getSelectedObject();
		TreePath path = getPathOf(toBeRemoved);
		getTreeManager().remove(toBeRemoved);
		nodeEditors.remove(path.toString());
		fireTreeEvent(TreeEvent.Type.CHILD_REMOVED, path);
		return toBeRemoved;
	}
	
	public void moveTo(T newParent) {
		T toBeMoved = getSelectedObject();
		TreePath path = getPathOf(toBeMoved);
		getTreeManager().remove(toBeMoved);
		BeanEditor<T> editor = nodeEditors.remove(path.toString());
		fireTreeEvent(TreeEvent.Type.CHILD_REMOVED, path);
		getTreeManager().addChild(newParent, toBeMoved);
		TreePath newPath = getPathOf(toBeMoved);
		nodeEditors.put(newPath.toString(), editor);
		fireTreeEvent(TreeEvent.Type.CHILD_ADDED, newPath);
	}
	
	@Override
	public void moveDown() {
		int[] path = getSelectedPath().asIntArray().clone();
		path[path.length - 1]--;
		T newParent = getTargetOf(new TreePath(path));
		moveTo(newParent);
	}

	@Override
	public void moveUp() {
		T toBeMoved = getSelectedObject();
		T parent = getParentOf(toBeMoved);
		T newParent = getParentOf(parent);
		moveTo(newParent);
	}

	@Override
	public BeanEditor<T> getNodeEditor(TreePath path) {
		if (!nodeEditors.containsKey(path.toString())) {
			nodeEditors.put(path.toString(), getTreeManager().createNodeEditor(getTargetOf(path)));
		}
		return nodeEditors.get(path.toString());
	}

	@Override
	public BeanEditor<T> getNodeEditor(T node) {
		return getNodeEditor(getPathOf(node));
	}

	public void addTreeListener(TreeEventListener listener) {
		treeEventSupport.addTreeListener(listener);
	}
	
	public void removeTreeListener(TreeEventListener listener) {
		treeEventSupport.removeTreeListener(listener);
	}
	
	protected void fireTreeEvent(TreeEvent.Type type, TreePath path) {
		treeEventSupport.notifyTreeListeners(new TreeEvent(type, path));
	}
	
	@Override
    public void addSelectionListener(SelectionListener listener) {
    	selectionSupport.addSelectionListener(listener);
    }

    @Override
    public void removeSelectionListener(SelectionListener listener) {
    	selectionSupport.removeSelectionListener(listener);
    }

    private void notifySelectionListeners() {
    	selectionSupport.notifySelectionListeners(new SelectionEvent(this));
    }
    
    protected void firePropertyChange(String propertyName, Object oldValue, Object newValue) {
    	pChange.firePropertyChange(propertyName, oldValue, newValue);
    }

	@Override
	public void addPropertyChangeListener(PropertyChangeListener listener) {
		pChange.addPropertyChangeListener(listener);
	}

	@Override
	public void removePropertyChangeListener(PropertyChangeListener listener) {
		pChange.removePropertyChangeListener(listener);
	}
}

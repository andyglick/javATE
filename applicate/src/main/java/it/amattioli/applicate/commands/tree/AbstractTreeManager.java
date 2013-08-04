package it.amattioli.applicate.commands.tree;

import it.amattioli.applicate.browsing.TreePath;
import it.amattioli.dominate.properties.PropertyChangeSupport;

import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractTreeManager<T> implements TreeManager<T> {
	protected T root;
	private PropertyChangeSupport pChange = new PropertyChangeSupport(this);

	public AbstractTreeManager(T root) {
		this.root = root;
	}
	
	public T getRoot() {
		return root;
	}
	
	public void setRoot(T root) {
		this.root = root;
	}

	@Override
	public TreePath getPathOf(T target) {
		List<Integer> result = new ArrayList<Integer>();
		T curr = target;
		while (!curr.equals(getRoot())) {
			T parent = getParentOf(curr);
			result.add(0, getChildrenOf(parent).indexOf(curr));
			curr = parent;
		}
		return new TreePath(result);
	}

	@Override
	public T getTargetOf(TreePath path) {
		T result = getRoot();
		for (int i: path.asIntArray()) {
			result = getChildrenOf(result).get(i);
		}
		return result;
	}

	@Override
	public void addPropertyChangeListener(PropertyChangeListener listener) {
	    pChange.addPropertyChangeListener(listener);
	}

	@Override
	public void removePropertyChangeListener(PropertyChangeListener listener) {
	    pChange.removePropertyChangeListener(listener);
	}

	protected void firePropertyChange(String propertyName, Object oldValue, Object newValue) {
		pChange.firePropertyChange(propertyName, oldValue, newValue);
	}

}
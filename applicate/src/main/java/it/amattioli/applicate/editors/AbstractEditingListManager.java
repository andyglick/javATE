package it.amattioli.applicate.editors;

import it.amattioli.dominate.properties.PropertyChangeSupport;

import java.beans.PropertyChangeListener;
import java.util.List;

public abstract class AbstractEditingListManager<T> implements EditingListManager<T> {
	private PropertyChangeSupport pChange = new PropertyChangeSupport(this);
	private List<T> editingList;

	protected void firePropertyChange(String propertyName, Object oldValue, Object newValue) {
		pChange.firePropertyChange(propertyName, oldValue, newValue);
	}

	public void addPropertyChangeListener(PropertyChangeListener listener) {
	    pChange.addPropertyChangeListener(listener);
	}

	public void removePropertyChangeListener(PropertyChangeListener listener) {
	    pChange.removePropertyChangeListener(listener);
	}

	public List<T> getEditingList() {
		return editingList;
	}

	public void setEditingList(List<T> editingList) {
		List<T> old = this.editingList;
		this.editingList = editingList;
		firePropertyChange("editingList", old, editingList);
	}

}

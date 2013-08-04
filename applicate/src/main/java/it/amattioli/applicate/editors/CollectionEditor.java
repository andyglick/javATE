package it.amattioli.applicate.editors;

import it.amattioli.dominate.properties.PropertyChangeEmitter;

import java.beans.PropertyChangeListener;
import it.amattioli.dominate.properties.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.List;

public abstract class CollectionEditor<T> implements PropertyChangeEmitter {
	private static final String EDITING_LIST = "editingList";
	private List<T> editingList;
	private int selectedIndex;
	private boolean emptyRow = true;
	private PropertyChangeSupport pChange = new PropertyChangeSupport(this);

	public CollectionEditor(List<T> editingList) {
		setEditingList(editingList);
	}

	public CollectionEditor(List<T> editingList, boolean emptyRow) {
		this.emptyRow = emptyRow;
		setEditingList(editingList);
	}

	public boolean isEmptyRow() {
		return emptyRow;
	}

	public void setEmptyRow(boolean emptyRow) {
		this.emptyRow = emptyRow;
	}

	protected void addRowIfEmpty() {
		if (emptyRow && this.editingList.isEmpty()) {
			addRow();
		}
	}

	public void setEditingList(List<T> editingList) {
		this.editingList = editingList == null ? new ArrayList<T>() : editingList;
		addRowIfEmpty();
		firePropertyChange(EDITING_LIST, null, getEditingList());
	}

	public List<T> getEditingList() {
		return editingList;
	}

	public void select(int idx) {
		this.selectedIndex = idx;
	}

	public int getSelectedIndex() {
		return selectedIndex;
	}

	public T getSelectedObject() {
		return getEditingList().get(getSelectedIndex());
	}

	public void addRow() {
		getEditingList().add(createObject());
		firePropertyChange(EDITING_LIST, null, getEditingList());
	}

	public void deleteRow() {
		if (getSelectedIndex() >= 0 && getSelectedIndex() < getEditingList().size()) {
			getEditingList().remove(getSelectedIndex());
			addRowIfEmpty();
			firePropertyChange(EDITING_LIST, null, getEditingList());
		}
	}

	protected abstract T createObject();

	protected void firePropertyChange(String propertyName, Object oldValue, Object newValue) {
		pChange.firePropertyChange(propertyName, oldValue, newValue);
	}

	public void addPropertyChangeListener(PropertyChangeListener listener) {
	    pChange.addPropertyChangeListener(listener);
	}

	public void removePropertyChangeListener(PropertyChangeListener listener) {
	    pChange.removePropertyChangeListener(listener);
	}
}

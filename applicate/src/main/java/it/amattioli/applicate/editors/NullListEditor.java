package it.amattioli.applicate.editors;

import it.amattioli.applicate.selection.SelectionListener;

import java.beans.PropertyChangeListener;
import java.util.Collections;
import java.util.List;

/**
 * For testing purpose you can decide to use the NullListEditor implementation of the
 * ListEditor interface.
 *
 * This class {@link #getEditingList()} method always returns an empty list while the {@link #getElementEditor(int)},
 * consistently, always returns null.
 *
 * If you try to use the {@link #addRow(Object...)} and {@link #deleteRow()} methods an {@link UnsupportedOperationException}
 * will be raised.
 *
 * @author andrea
 *
 * @param <T> the class of the objects contained in the list that is being edited
 */
public class NullListEditor<T> implements ListEditor<T> {
	
	@Override
	public T addRow(Object... param) {
		throw new UnsupportedOperationException();
	}

	@Override
	public T deleteRow() {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean canDeleteRow() {
		return false;
	}

	@Override
	public List<T> getEditingList() {
		return Collections.emptyList();
	}

	@Override
	public int getSize() {
		return 0;
	}

	@Override
	public BeanEditor<T> getElementEditor(int index) {
		return null;
	}

	@Override
	public Integer getSelectedIndex() {
		return null;
	}

	@Override
	public T getSelectedObject() {
		return null;
	}

	@Override
	public boolean isEmptyRow() {
		return false;
	}

	@Override
	public void select(int idx) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setEditingList(List<T> editingList) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setEmptyRow(boolean emptyRow) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void addPropertyChangeListener(PropertyChangeListener listener) {
		// Does nothing because the content never changes
	}

	@Override
	public void removePropertyChangeListener(PropertyChangeListener listener) {
		// Does nothing because the content never changes
	}

	@Override
	public void addSelectionListener(SelectionListener listener) {
		// Does nothing because there's nothing to select
	}

	@Override
	public void removeSelectionListener(SelectionListener listener) {
		// Does nothing because there's nothing to select
	}

}

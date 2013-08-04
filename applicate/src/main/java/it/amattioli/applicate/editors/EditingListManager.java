package it.amattioli.applicate.editors;

import it.amattioli.dominate.properties.PropertyChangeEmitter;

import java.util.List;

public interface EditingListManager<T> extends PropertyChangeEmitter {

	public abstract List<T> getEditingList();

	public abstract void setEditingList(List<T> editingList);

	public abstract BeanEditor<T> createElementEditor(T curr);
	
	public abstract BeanEditor<T> addRow(Object... param);

	public abstract boolean canDeleteRow(int idx);

	public abstract T deleteRow(int idx);

}
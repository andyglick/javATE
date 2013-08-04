package it.amattioli.applicate.editors;

import it.amattioli.applicate.selection.Selector;
import it.amattioli.dominate.properties.PropertyChangeEmitter;

import java.util.List;

/**
 * An editor for a list of objects. Implementations of this interface maintain a
 * list of editors, one for each object contained in the editing list. A new object
 * can be added to the list using the {@link #addRow()} method. The {@link #deleteRow()}
 * method, instead, can be used to remove an entity from the editing list.
 * 
 * @param <T> the class of the objects contained in the list that is being edited
 */
public interface ListEditor<T> extends PropertyChangeEmitter, Selector<T> {

	/**
	 * Set the list that has to be edited by this editor.
	 * 
	 * @param editingList the list that has to be edited by this editor
	 */
	public void setEditingList(List<T> editingList);

	/**
	 * Retrieve the list that is being edited by this editor.
	 * 
	 * @return the list that is being edited by this editor
	 */
	public List<T> getEditingList();
	
	/**
	 * Returns the number of objects in the list that is being edited by this editor.
	 * 
	 * @return the number of objects in the list that is being edited by this editor
	 */
	public int getSize();
	
	/**
	 * Set the behavior of this editor when the editing list is empty.
	 * If the emptyRow property is set to true the editor will automatically
	 * create a new row so the list will never be empty. If the property is
	 * set to false the list can be empty, so no new entity is created.
	 *  
	 * @param emptyRow
	 */
	public void setEmptyRow(boolean emptyRow);

	public boolean isEmptyRow();

	/**
	 * Selected the object with the given index.
	 * 
	 * @param idx the index of the object to be selected
	 */
	public void select(int idx);

	/**
	 * Retrieves the index of the currently selected object.
	 * 
	 * @return the index of the currently selected object or null if no object
	 *         is selected
	 */
	public Integer getSelectedIndex();

	/**
	 * Retrieves the currently selected object.
	 * 
	 * @return the currently selected object or null if no object is selected 
	 */
	public T getSelectedObject();

	/**
	 * Retrieves the editor for the entity with the given index.
	 * 
	 * @param index
	 * @return
	 */
	public BeanEditor<T> getElementEditor(int index);

	/**
	 * Create a new object (and a corresponding editor) at the end of the list.
	 * 
	 * @param param a list of param that can be necessary for building a new entity instance
	 * @return the new entity
	 */
	public T addRow(Object... param);

	/**
	 * Delete from the list the object (and the corresponding editor) currently
	 * selected.
	 * 
	 * @return
	 */
	public T deleteRow();

	/**
	 * Check if the currently selected object can be deleted.
	 * 
	 * @return true if the currently selected object can be deleted,
	 *         false otherwise
	 */
	public boolean canDeleteRow();
}